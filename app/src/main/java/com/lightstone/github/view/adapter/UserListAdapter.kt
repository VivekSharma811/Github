package com.lightstone.github.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lightstone.github.R
import com.lightstone.github.databinding.ItemUserBinding
import com.lightstone.github.model.response.UserItem

class UserListAdapter(val userList : ArrayList<UserItem>) : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    class UserViewHolder(var view : ItemUserBinding) : RecyclerView.ViewHolder(view.root)

    fun updateUsers(newUsersList : List<UserItem>) {
        userList.clear()
        userList.addAll(newUsersList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemUserBinding>(inflater, R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.view.user = userList[position]
    }

}