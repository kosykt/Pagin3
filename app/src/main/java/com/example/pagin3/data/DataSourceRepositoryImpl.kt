package com.example.pagin3.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pagin3.data.network.GithubService
import com.example.pagin3.data.network.Repo
import com.example.pagin3.data.network.RepoSearchDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.http.Query

class DataSourceRepositoryImpl(
    private val githubService: GithubService
) : DataSourceRepository {
    override fun getPagedRepos(searchBy: String): Flow<PagingData<Repo>> {
        val loader: ReposPageLoader = { pageIndex, pageSize ->
            getUsers(pageIndex, pageSize, searchBy)
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { RepoPagingSource(loader, PAGE_SIZE) },
        ).flow
    }

    private suspend fun getUsers(pageIndex: Int, pageSize: Int, searchBy: String): List<Repo> =
        withContext(Dispatchers.IO) {

            val list = githubService.searchRepos(searchBy, pageIndex, pageSize)

            return@withContext list.items
        }

    private companion object {
        const val PAGE_SIZE = 20
    }
}