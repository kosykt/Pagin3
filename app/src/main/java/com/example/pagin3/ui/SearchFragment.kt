package com.example.pagin3.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import com.example.pagin3.databinding.FragmentSearchBinding
import kotlinx.coroutines.flow.collectLatest

class SearchFragment : Fragment() {

    private val viewModel: SearchFragmentViewModel by viewModels()
    private val adapter by lazy {
        SearchFragmentAdapter()
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding ?: throw RuntimeException("FragmentSearchBinding? = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.adapter = adapter
        binding.editText.doAfterTextChanged {
            viewModel.getPagedRepos(it.toString())
        }
        lifecycleScope.launchWhenStarted {
            viewModel.repos.collectLatest { paging ->
                    adapter.submitData(paging)
                }
        }
    }
}