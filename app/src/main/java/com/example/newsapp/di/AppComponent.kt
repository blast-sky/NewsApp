package com.example.newsapp.di

import android.app.Application
import android.content.Context
import com.example.newsapp.ui.fragments.ArticleFragment
import com.example.newsapp.ui.fragments.BookmarksFragment
import com.example.newsapp.ui.fragments.DailyNewsFragment
import com.example.newsapp.ui.fragments.SearchFragment
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class, DatabaseModule::class])
interface AppComponent {

    fun inject(fragment: DailyNewsFragment)
    fun inject(fragment: BookmarksFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: ArticleFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun apiKey(@NewsApiQualifier key: String): Builder

        fun build(): AppComponent
    }
}

@Module
abstract class ApplicationModule {

    @Binds
    abstract fun bindContext(application: Application): Context
}
