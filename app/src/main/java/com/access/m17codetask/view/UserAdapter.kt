package com.access.m17codetask.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.access.m17codetask.GlideApp
import com.access.m17codetask.R
import com.access.m17codetask.dataClass.User
import com.access.m17codetask.util.toPx
import kotlinx.android.synthetic.main.user_item.view.*

class UserAdapter : RecyclerView.Adapter<UserViewHolder>() {
    var userList: ArrayList<User> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(vh: UserViewHolder, position: Int) {
        GlideApp.with(vh.itemView.ivUserPhoto.context)
            .load(userList[position].url)
            .miniThumb(100.toPx)
            .placeholder(R.mipmap.ic_launcher)
            .into(vh.itemView.ivUserPhoto)
        vh.itemView.tvName.text = userList.get(position).name
    }


}

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)