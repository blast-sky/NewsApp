package com.example.newsapp.di

import android.app.Application
import androidx.room.Room
import com.example.newsapp.data.room.ArticleDao
import com.example.newsapp.data.room.ArticleDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {

    @[Provides Singleton]
    fun provideArticleDatabase(application: Application): ArticleDatabase {
        return Room.databaseBuilder(application, ArticleDatabase::class.java, "articles")
            .fallbackToDestructiveMigration()
            .build()
    }

    @[Provides Singleton]
    fun provideArticleDao(articleDatabase: ArticleDatabase): ArticleDao {
        return articleDatabase.articleDao()
    }
}