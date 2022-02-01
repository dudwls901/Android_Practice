package fastcampus.part3.fastcampus_goodstrade.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import fastcampus.part3.fastcampus_goodstrade.R
import fastcampus.part3.fastcampus_goodstrade.databinding.FragmentHomeBinding
import fastcampus.part3.fastcampus_goodstrade.key.DBKey.Companion.DB_ARTICLES

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding : FragmentHomeBinding

    private lateinit var articleAdapter :ArticleAdapter

    private lateinit var articleDB: DatabaseReference

    //onViewCreated할 때 뷰 자체는 새로 만들어졌지만 전역변수는 mainActivity에서
    // HomeFragment인스턴스를 그대로 가지고 있고 프래그먼트 뷰만 초기화하기 때문에
    // 전역 변수들은 살아있음. -> onViewCreated에서 초기화 하지 않으면 프래그먼트 이동할 때마다 중복값 생김
    private val articleList = mutableListOf<ArticleModel>()

    private val listener = object: ChildEventListener{
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            //클래스 자체를 넘기면 프로퍼티를 매핑해서 인스턴스 자체를 받아올 수 있음
            //디비에 모델 클래스 자체를 db에 올렸댜는 가정이 있기 때문
            val articleModel = snapshot.getValue(ArticleModel::class.java)
            articleModel ?: return
            articleList.add(articleModel)
            articleAdapter.submitList(articleList)
        }
        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onChildRemoved(snapshot: DataSnapshot) {}
        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onCancelled(error: DatabaseError) {}
    }

    private val auth: FirebaseAuth by lazy{
        Firebase.auth
    }

    //activity onCreate
    //fragment onViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        articleList.clear()
        articleDB = Firebase.database.reference.child(DB_ARTICLES)

        articleAdapter = ArticleAdapter()

        //fragment는 context가 될 수 없음
        //kotlin context == java getcontext()
        binding.articleRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.articleRecyclerView.adapter = articleAdapter

        binding.addFloatingButton.setOnClickListener{
            //todo 로그인 기능 구현
            //fragment에서 context사용하기 : requireContext()
            //그냥 context사용해도 되는데 nullable이기 때문에 null처리 해주어야 함
//            if(auth.currentUser != null) {
                val intent = Intent(requireContext(), AddArticleActivity::class.java)
                startActivity(intent)
//            }
//            else{
//                Snackbar.make(view, "로그인 후 사용해주세요", Snackbar.LENGTH_LONG).show()
//            }
        }

        //onViewCreate될 때 디비값 가져와서 붙여놓고 viewCreated될 때마다 addChildeEventListener하면 중복 값이 붙을 수 있음
        //-> eventListener 전역으로 하고, onDestroy에서 리스너 제거
        articleDB.addChildEventListener(listener)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        articleAdapter.notifyDataSetChanged()
    }


    override fun onDestroy() {
        super.onDestroy()
        articleDB.removeEventListener(listener)
    }


}