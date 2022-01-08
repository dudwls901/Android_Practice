package com.example.memoapp_room_practice

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), OnDeleteListener {

    //lateinit : 나중에 초기화
    lateinit var db : MemoDatabase
    var memoList = listOf<MemoEntity>()
    lateinit var add_btn : Button
    lateinit var add_et : EditText
    lateinit var memo_rv : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = MemoDatabase.getInstance(this)!!

        add_btn = findViewById(R.id.add_btn)
        add_et = findViewById(R.id.add_et)
        memo_rv = findViewById(R.id.memo_rv)

        add_btn.setOnClickListener{
            //id는 자동 생성해놨기 때문에 그냥 null
            //add_et.text 는 editable 타입이기 때문에 String으로 형 변환
            val memo = MemoEntity(null, add_et.text.toString())
            insertMemo(memo)
            add_et.setText("")
        }

        //recyclerview
        memo_rv.layoutManager = LinearLayoutManager(this)

        getAllMemos()
    }


    //1. Insert Data
    //2. Get Data
    //3. Delete Data

    //4. Set RecyclerView

    //안드로이드에선 Lint를 통해 성능상 문제가 있을 수 있는 코드를 관리해준다.
    //AsyncTask때문에 메모리 누수가 발생할 수 있는데 @SuppressLint를 사용하면 그 경고를 무시한다.
    @SuppressLint("StaticFieldLeak")
    fun insertMemo(memo : MemoEntity){
        //1. MainThread(UI Thread) vs WorkerThread(Background Thread)
        //모든 ui 관련 일들은 MainThread
        //모든 데이터 통신과 관련된 일들은 WorkerThread
        //coroutine 배우자!
        //일단 asynctask 사용
        //async : 비동기적인, 백그라운드에서 하는 것들을 도와주는 추상 클래스

        //백그라운드로 보내기
        val inserTask = object : AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                //workerThread에서(백그라운드) 뭘 할 건지 정의
                //db에 memo 삽입
                db.memoDAO().insert(memo)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                //insert 한 다음에 뭐 할 건지 정의
                //insert 했으니 모든 데이터를 다시 불러오기
                getAllMemos()
            }
        }
        inserTask.execute()
    }

    //모든 메모 데이터 받아와서 리사이클러뷰에 셋팅
    fun getAllMemos(){
        val getTask = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Unit,Unit,Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                memoList = db.memoDAO().getAll()
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                setRecyclerView(memoList)
            }
        }
        getTask.execute()

    }

    //longClick시 deleteMemo가 호출되어야 하는데 이를 가장 간편히 사용할 수 있는 것이 인터페이스
    //인터페이스느 액티비티와 액티비티, 액티비티와 프래그먼트 등에 통신이 가능하게 함
    fun deleteMemo(memo : MemoEntity){
        val deleteTask = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Unit,Unit,Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                db.memoDAO().delete(memo)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                getAllMemos()
            }
        }
        deleteTask.execute()
    }

    fun setRecyclerView(memoList : List<MemoEntity>){
        //리스너를 메인 엑티비티에서 지정했는데, 이걸 어댑터에서 사용하려면 그냥 파라미터로 넘기면 됨
        memo_rv.adapter = MyAdapter(this, memoList, this)
    }


    override fun onDeleteListener(memo: MemoEntity) {
        deleteMemo(memo)
    }
}