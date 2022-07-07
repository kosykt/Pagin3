package com.example.pagin3.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.pagin3.data.DataSourceRepositoryImpl
import com.example.pagin3.data.network.GithubService
import com.example.pagin3.data.network.Repo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchFragmentViewModel() : ViewModel() {

    private val dataSourceRepository = DataSourceRepositoryImpl(GithubService.create())

    private val _repos: MutableStateFlow<PagingData<Repo>> = MutableStateFlow(PagingData.empty())
    val repos: StateFlow<PagingData<Repo>> = _repos.asStateFlow()

    fun getPagedRepos(searchBy: String) {
        viewModelScope.launch {
            dataSourceRepository.getPagedRepos(searchBy)
                .collectLatest {
                    _repos.value = it
                }
        }
    }
}