package com.capstone.momomeal.feature.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.momomeal.databinding.ItemMyMsgBinding
import com.capstone.momomeal.databinding.ItemOtherMsgBinding
import com.capstone.momomeal.databinding.ItemOtherMsgFullBinding
import com.capstone.momomeal.feature.Chat

class ChatAdapter(
//    val context: Context,
    val chatList: ArrayList<Chat>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")

    }

    override fun getItemCount(): Int = chatList.size

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val OTHER_MSG_FULL = 0
        private const val OTHER_MSG = 1
        private const val MY_MSG = 2
    }
}


class OtherMsgFullViewHolder(
    private val binding: ItemOtherMsgFullBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Chat) {
        binding.tvOtherMsgFull.text = item.chatContent
        binding.tvOtherName
    }
}

class OtherMsgViewHolder(
    val binding: ItemOtherMsgBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Chat) {

    }
}

class MyMsgViewHolder(
    val binding: ItemMyMsgBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Chat) {

    }
}