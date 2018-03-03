package com.max.test.testkotlin.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.max.test.testkotlin.R
import com.max.test.testkotlin.entity.Contact
import java.util.*

class ContactsAdapter(contacts: ArrayList<Contact>, private var layoutId: Int):
        RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    var contacts: ArrayList<Contact> = ArrayList()

    init {
        this.contacts.addAll(contacts)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ContactsViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(layoutId, null)
        val holder = ContactsViewHolder(view)
        holder.layout!!.setOnClickListener {
            listener!!.onItemClick(view)
        }
        return holder
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contact = contacts[position]
        if (position == 0 || contacts[position-1].index != contact.index) {
            holder.tvIndex!!.visibility = View.VISIBLE
            holder.tvIndex!!.text = contact.index
        } else {
            holder.tvIndex!!.visibility = View.GONE
        }
        holder.tvName!!.text = contact.name
        holder.ivAvatar!!.setBackgroundResource(getAvatar())
    }

    private val avatarArray = arrayOf(R.drawable.ic_avatar_0, R.drawable.ic_avatar_1,R.drawable.ic_avatar_no)
    private fun getAvatar(): Int{
        val index = Random().nextInt(3)
        return avatarArray[index]
    }

    inner class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvIndex: TextView? = null
        var ivAvatar: ImageView? = null
        var tvName: TextView? = null
        var layout: ViewGroup? = null

        init {
            tvIndex = itemView.findViewById(R.id.tv_index)
            ivAvatar = itemView.findViewById(R.id.iv_avatar)
            tvName = itemView.findViewById(R.id.tv_name)
            layout = itemView.findViewById(R.id.item_layout)
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        listener = onItemClickListener
    }
    private var listener: OnItemClickListener? = null
    interface OnItemClickListener {
        fun onItemClick(view: View)
    }
}