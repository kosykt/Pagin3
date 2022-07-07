package com.example.pagin3.data

import androidx.paging.PagingData
import com.example.pagin3.data.network.Repo
import kotlinx.coroutines.flow.Flow

interface DataSourceRepository {

    fun getPagedRepos(searchBy: String): Flow<PagingData<Repo>>
}