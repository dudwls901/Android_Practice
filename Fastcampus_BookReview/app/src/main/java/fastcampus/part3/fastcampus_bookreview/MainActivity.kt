package fastcampus.part3.fastcampus_bookreview

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import fastcampus.part3.fastcampus_bookreview.adapter.BookAdapter
import fastcampus.part3.fastcampus_bookreview.adapter.HistoryAdapter
import fastcampus.part3.fastcampus_bookreview.api.BookService
import fastcampus.part3.fastcampus_bookreview.databinding.ActivityMainBinding
import fastcampus.part3.fastcampus_bookreview.model.BestSellerDTO
import fastcampus.part3.fastcampus_bookreview.model.Book
import fastcampus.part3.fastcampus_bookreview.model.History
import fastcampus.part3.fastcampus_bookreview.model.SearchBookDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: BookAdapter
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var bookService: BookService

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!

        initBookRecyclerView()
        initHistoryRecyclerView()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://book.interpark.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        bookService = retrofit.create(BookService::class.java)

        bookService.getBestSellerBooks(getString(R.string.interparkAPIKey))
            .enqueue(object : Callback<BestSellerDTO> {
                override fun onResponse(
                    call: Call<BestSellerDTO>,
                    response: Response<BestSellerDTO>
                ) {
                    if (response.isSuccessful.not()) {
                        Log.e(TAG, "NOT!! SUCCESS")
                        return
                    }
                    response.body()?.let {
                        Log.d(TAG, it.toString())

                        it.books.forEach { book ->
                            Log.d(TAG, book.toString())
                        }
                        //이렇게 어댑터에 데이터 연결하거나
                        //아니면 어댑터 생성자로 넘기거나
                        adapter.submitList(it.books)
                    }
                }

                override fun onFailure(call: Call<BestSellerDTO>, t: Throwable) {
                    Log.e(TAG, t.toString())
                }
            })

    }

    private fun search(keyword: String){
        bookService.getBooksByName(getString(R.string.interparkAPIKey),keyword)
            .enqueue(object : Callback<SearchBookDTO> {
                override fun onResponse(
                    call: Call<SearchBookDTO>,
                    response: Response<SearchBookDTO>
                ) {
                    hideHistoryView()
                    saveSearchKeyword(keyword)

                    if (response.isSuccessful.not()) {
                        Log.e(TAG, "NOT!! SUCCESS")
                        return
                    }
                    //이렇게 어댑터에 데이터 연결하거나
                    //아니면 어댑터 생성자로 넘기거나
                    adapter.submitList(response.body()?.books.orEmpty())
                }

                override fun onFailure(call: Call<SearchBookDTO>, t: Throwable) {
                    hideHistoryView()
                }
            })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initSearchEditText(){
        binding.searchEditText.setOnKeyListener { v, keyCode, event ->
            //터치이벤트엔 keydown(눌렀을 때)과 keyup(땠을때)이 있음
            //down과 up의 간격을 보고 클릭인지 롱프레스인지 결정하게됨
            //onKeyListener에는 Enter 누르면 실제로 두 개의 이벤트가 발생
            //KEYDOWN event, KEYUP event
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == MotionEvent.ACTION_DOWN){
                search(binding.searchEditText.text.toString())
                //true는 이 이벤트를 처리했음을 의미
                return@setOnKeyListener true
            }
            //false는 이 키이벤트가 처리가 안 돼서 시스템에서 정의한 다른 이벤트가 실행되어야 한다는 의미
            return@setOnKeyListener false
        }
        binding.searchEditText.setOnTouchListener { view, event ->
            if(event.action == MotionEvent.ACTION_DOWN){
                showHistoryView()
//                return@setOnTouchListener true
            }
            return@setOnTouchListener false
        }
    }

    private fun showHistoryView(){
        Thread{
            val keywords = db.historyDAO().getAll().reversed()

            runOnUiThread{
                binding.historyRecyclerView.isVisible = true
                historyAdapter.submitList(keywords.orEmpty())
            }
        }.start()
        binding.historyRecyclerView.isVisible = true
    }
    private fun hideHistoryView(){
        binding.historyRecyclerView.isVisible = false
    }

    private fun saveSearchKeyword(keyword: String){
        Thread{
            db.historyDAO().insertHistory(History(null, keyword))
        }.start()
    }

    private fun initBookRecyclerView(){
        adapter = BookAdapter(itemClickedListener = {
            val intent = Intent(this, DetailActivity::class.java)
            //Book 클래스를 직렬화하여 클래스 자체를 넘겨버리기
            intent.putExtra("bookModel",it)
            startActivity(intent)
        })

        binding.bookRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.bookRecyclerView.adapter = adapter

    }
    private fun initHistoryRecyclerView(){
        historyAdapter = HistoryAdapter(historyDeleteClickedListener = {
            deleteSearchKeyword(it)
        },historyClickedListener ={
            binding.searchEditText.setText(it)
            search(it)
        })
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.historyRecyclerView.adapter = historyAdapter
        initSearchEditText()
    }

    private fun deleteSearchKeyword(keyword: String){
        Thread{
            db.historyDAO().delete(keyword)
            showHistoryView()
        }.start()
    }


    companion object {
        private const val TAG = "MainActivity"
    }

}