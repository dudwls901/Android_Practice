package fastcampus.practice.fastcampus_githubrepo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import fastcampus.practice.fastcampus_githubrepo.data.database.DataBaseProvider
import fastcampus.practice.fastcampus_githubrepo.data.entity.GithubOwner
import fastcampus.practice.fastcampus_githubrepo.data.entity.GithubRepoEntity
import fastcampus.practice.fastcampus_githubrepo.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class MainActivity: AppCompatActivity(), CoroutineScope{

    //빌더 구성
    private val job = Job()

    private val repositoryDao by lazy{
        DataBaseProvider.provideDB(applicationContext).repositoryDao()
    }

    private lateinit var adapter: RepositoryRecyclerAdapter

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        initViews()

    }

    private fun initAdapter(){
        adapter = RepositoryRecyclerAdapter()
    }

    private fun initViews() = with(binding){
        recyclerView.adapter =adapter
        searchButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, SearchActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        launch(coroutineContext) {
            loadLikedRepositoryList()
        }
    }

    private suspend fun loadLikedRepositoryList() = withContext(Dispatchers.IO){
        val repoList = repositoryDao.getHistory()
        withContext(Dispatchers.Main){
            setData(repoList)
        }
    }

    private fun setData(githubRepositoryList: List<GithubRepoEntity>) = with(binding){
        if(githubRepositoryList.isEmpty()){
            emptyResultTextView.isGone = false
            recyclerView.isGone = true
        }
        else{
            emptyResultTextView.isGone = true
            recyclerView.isGone = false
            adapter.setRepositoryList(githubRepositoryList){
                startActivity(
                    Intent(this@MainActivity, RepositoryActivity::class.java).apply{
                        putExtra(RepositoryActivity.REPOSITORY_NAME_KEY, it.name)
                        putExtra(RepositoryActivity.REPOSITORY_OWNER_KEY, it.owner.login)
                    }
                )
            }

        }
    }

}