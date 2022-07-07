package com.example.pagin3.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagin3.data.network.Repo

typealias ReposPageLoader = suspend (pageIndex: Int, pageSize: Int) -> List<Repo>

@Suppress("UnnecessaryVariable")
class RepoPagingSource(
    private val loader: ReposPageLoader,
    private val pageSize: Int
) : PagingSource<Int, Repo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val pageIndex = params.key ?: 0

        return try {
            val users = loader.invoke(pageIndex, params.loadSize)
            return LoadResult.Page(
                data = users,
                prevKey = if (pageIndex == 0) null else pageIndex - 1,
                nextKey = if (users.size == params.loadSize) pageIndex + (params.loadSize / pageSize) else null
            )
        } catch (e: Exception) {
            LoadResult.Error(
                throwable = e
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

}