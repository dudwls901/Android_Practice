package fastcampus.practice.fastcampus_githubrepo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import fastcampus.practice.fastcampus_githubrepo.data.dao.RepositoryDao
import fastcampus.practice.fastcampus_githubrepo.data.entity.GithubRepoEntity

@Database(entities = [GithubRepoEntity::class], version = 1)
abstract class SimpleGithubDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
}