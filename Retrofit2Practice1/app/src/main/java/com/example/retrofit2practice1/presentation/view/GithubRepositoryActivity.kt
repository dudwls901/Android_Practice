package com.example.retrofit2practice1.presentation.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit2practice1.presentation.view.adapter.GitHubRepositoryAdapter
import com.example.retrofit2practice1.presentation.viewmodel.GitHubViewModel
import com.example.retrofit2practice1.data.remote.GithubRepositoryData
import com.example.retrofit2practice1.data.repository.GithubServiceClient
import com.example.retrofit2practice1.databinding.ActivityGithubrepositoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubRepositoryActivity: AppCompatActivity() {

    private lateinit var binding: ActivityGithubrepositoryBinding

    private lateinit var gitHubRepositoryAdapter: GitHubRepositoryAdapter

    private val githubViewModel: GitHubViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGithubrepositoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val searchId = intent.getStringExtra("searchId")
        Log.d("main",searchId?:"empty")
        searchId?.let{
            initGithubRecyclerView(searchId)
        }

    }


    private fun initGithubRecyclerView(searchId : String){

        gitHubRepositoryAdapter = GitHubRepositoryAdapter()
        binding.githubRepositoryRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.githubRepositoryRecyclerview.adapter= gitHubRepositoryAdapter

        val call : Call<List<GithubRepositoryData>> = GithubServiceClient.service.getRepoList(searchId)
        call.enqueue(
            object : Callback<List<GithubRepositoryData>> {
                val repoList = mutableListOf<GithubRepositoryData>()
                override fun onResponse(call: Call<List<GithubRepositoryData>>, response: Response<List<GithubRepositoryData>>) {
                    val userInfo = response.body()
                    if(!userInfo.isNullOrEmpty()) {

                        Log.d("??????", "${userInfo.size}")
                        for(i in userInfo){
                            if(!i.private){
                                repoList.add(i)
                                Log.d("??????", "${i.private} , name : ${i.repoName}  id : ${i.repoId}")
                            }
                        }
                    }
                    Log.d("??????","?????? ?????? : ${response.body().toString()}")
                    gitHubRepositoryAdapter.submitList(repoList.orEmpty())
                }

                override fun onFailure(call: Call<List<GithubRepositoryData>>, t: Throwable) {
                    Log.d("??????", "?????? ?????? : $t")
                }
            }
        )
    }
}