package com.example.pagin3.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pagin3.data.network.Repo
import com.example.pagin3.databinding.ItemReposBinding

class SearchFragmentAdapter: PagingDataAdapter<Repo, SearchFragmentAdapter.ReposViewHolder>(ReposCallback) {

    inner class ReposViewHolder(private val vb: ItemReposBinding) :
        RecyclerView.ViewHolder(vb.root) {

        fun show(model: Repo?) {
            vb.itemText.text = model?.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        return ReposViewHolder(
            ItemReposBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        val repo: Repo? = getItem(position)
        holder.show(repo)
    }

    companion object ReposCallback : DiffUtil.ItemCallback<Repo>() {
        override fun areItemsTheSame(
            oldItem: Repo,
            newItem: Repo
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Repo,
            newItem: Repo
        ): Boolean {
            return oldItem == newItem
        }
    }
}