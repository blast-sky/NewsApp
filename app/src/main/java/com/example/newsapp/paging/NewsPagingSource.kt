package com.example.newsapp.paging

import androidx.paging.*
import com.example.newsapp.model.Article
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.lang.Exception

typealias ArticleLoader = suspend (page: String, pageSize: String) -> List<Article>

class NewsPagingSource(
    private val loader: ArticleLoader,
    private val pageSize: Int
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val nextPageIndex = params.key ?: 1
            val articles = loader.invoke(nextPageIndex.toString(), params.loadSize.toString())
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
//    {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }

}