package fastcampus.practice.fastcampus_githubrepo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isGone
import fastcampus.practice.fastcampus_githubrepo.data.entity.GithubRepoEntity
import fastcampus.practice.fastcampus_githubrepo.databinding.ActivitySearchBinding
import fastcampus.practice.fastcampus_githubrepo.util.RetrofitUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SearchActivity : AppCompatActivity(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var binding: ActivitySearchBinding

    private lateinit var adapter: RepositoryRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        initViews()
        bindViews()
    }

    private fun initAdapter(){
        adapter = RepositoryRecyclerAdapter()
    }
    private fun initViews() = with(binding){
        emptyResultTextView.isGone = true
        recyclerView.adapter = adapter
    }
    private fun bindViews() = with(binding){
        searchButton.setOnClickListener {
            searchKeyword(searchBarInputView.text.toString())
        }
    }
    private fun searchKeyword(keywordString: String) = launch{
        withContext(Dispatchers.IO) {
            val response = RetrofitUtil.githubApiService.searchRepositories(keywordString)
            if(response.isSuccessful){
                val body = response.body()
                withContext(Dispatchers.Main){
                    Log.e("response", body.toString())
                    body?.let{
                        setData(body.items)
                    }
                }
            }
        }
    }

    private fun setData(items : List<GithubRepoEntity>){
        adapter.setRepositoryList(items){
            Toast.makeText(this, "entity : ${it.toString()}", Toast.LENGTH_SHORT).show()
            startActivity(
                Intent(this, RepositoryActivity::class.java).apply{
                    putExtra(RepositoryActivity.REPOSITORY_OWNER_KEY, it.owner.login)
                    putExtra(RepositoryActivity.REPOSITORY_NAME_KEY, it.name)
                }
            )
        }
    }
}