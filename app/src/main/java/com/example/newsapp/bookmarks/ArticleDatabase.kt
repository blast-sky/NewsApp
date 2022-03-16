package com.example.newsapp.bookmarks

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArticleDbEntity::class], version = 2)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}