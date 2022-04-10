package com.example.newsapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<ArticleDbEntity>>

    @Query("SELECT * FROM articles WHERE sourceName LIKE :query OR title LIKE :query")
    suspend fun searchArticlesWithTitleAndSourceName(query: String): List<ArticleDbEntity>

    @Query("SELECT * FROM articles WHERE sourceName = :sourceName AND title = :title")
    suspend fun searchArticle(title: String, sourceName: String) : ArticleDbEntity?

    @Insert(onConflict = REPLACE)
    suspend fun insertArticle(article: ArticleDbEntity)

    @Delete
    suspend fun deleteArticle(article: ArticleDbEntity)

    @Query("DELETE FROM articles WHERE url = :url")
    suspend fun deleteArticleByUrl(url: String)
}