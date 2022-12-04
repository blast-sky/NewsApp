package com.example.newsapp.ui.adapter.paging

import androidx.paging.*
import com.example.newsapp.domain.model.Article
import java.lang.Exception

typealias ArticleLoader = suspend (page: Int, pageSize: Int) -> List<Article>

class NewsPagingSource(
    private val loader: ArticleLoader,
    private val pageSize: Int
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val nextPageIndex = params.key ?: 1
            val articles = loader.invoke(nextPageIndex, params.loadSize)
            LoadResult.Page(
                data = articles,
                prevKey = if(nextPageIndex == 1) null else nextPageIndex - 1,
                nextKey = if(params.loadSize != articles.size) null else nextPageIndex + params.loadSize / pageSize
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? = null
}