package fastcampus.part3.fastcampus_goodstrade.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import fastcampus.part3.fastcampus_goodstrade.R
import fastcampus.part3.fastcampus_goodstrade.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding : FragmentHomeBinding

    private lateinit var articleAdapter :ArticleAdapter

    //activity onCreate
    //fragment onViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        articleAdapter = ArticleAdapter()
        articleAdapter.submitList(mutableListOf<ArticleModel>().apply{
            add(ArticleModel("0","aaaa",1000,"5000원",""))
            add(ArticleModel("0","bbbb",2000,"23000원",""))
        })
        //fragment는 context가 될 수 없음
        //kotlin context == java getcontext()
        binding.articleRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.articleRecyclerView.adapter = articleAdapter

    }

}