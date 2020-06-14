package com.lightstone.github.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.lightstone.github.R
import com.lightstone.github.databinding.ItemRepositoryBinding
import com.lightstone.github.model.response.GithubRepository
import com.lightstone.github.view.fragment.UserFragmentDirections
import com.lightstone.github.view.listener.ItemClickListener
import kotlinx.android.synthetic.main.item_repository.view.*

class RepoListAdapter(var repoList : ArrayList<GithubRepository>) : RecyclerView.Adapter<RepoListAdapter.RepoViewHolder>(), ItemClickListener {

    class RepoViewHolder(var view : ItemRepositoryBinding) : RecyclerView.ViewHolder(view.root)

    fun updateUsers(newUsersList : List<GithubRepository>) {
        repoList.clear()
        repoList.addAll(newUsersList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemRepositoryBinding>(inflater, R.layout.item_repository, parent, false)
        return RepoViewHolder(view)
    }

    override fun getItemCount() = repoList.size

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.view.repo = repoList[position]
        holder.view.listener = this
    }

    override fun onItemClicked(v: View) {
        val reponame = v.reponame.text.toString()
        val action = UserFragmentDirections.actionUserRepoDetails()
        action.reponame = reponame
        action.username = "mojombo"
        Navigation.findNavController(v).navigate(action)
    }

}