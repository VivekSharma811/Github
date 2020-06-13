package com.lightstone.github.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.lightstone.github.R
import com.lightstone.github.databinding.ItemUserBinding
import com.lightstone.github.model.response.UserItem
import com.lightstone.github.view.fragment.ListFragmentDirections
import com.lightstone.github.view.listener.ItemClickListener
import kotlinx.android.synthetic.main.item_user.view.*

class UserListAdapter(val userList : ArrayList<UserItem>) : RecyclerView.Adapter<UserListAdapter.UserViewHolder>(), ItemClickListener {

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
        holder.view.listener = this
    }

    override fun onItemClicked(v: View) {
        val uuid = v.userUuid.text.toString().toInt()
        val action = ListFragmentDirections.actionListUser()
        action.userUuid = uuid
        Navigation.findNavController(v).navigate(action)
    }

}