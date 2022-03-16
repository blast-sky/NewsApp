package com.example.newsapp.bookmarks

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles")
    suspend fun getAllArticles(): Array<ArticleDbEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insertArticle(article: ArticleDbEntity)

    @Delete
    suspend fun deleteArticle(article: ArticleDbEntity)
}