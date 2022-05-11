package com.capstone.momomeal

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.momomeal.databinding.FragmentSearchResultBinding
import com.capstone.momomeal.feature.BaseFragment
import com.capstone.momomeal.data.Category
import com.capstone.momomeal.data.Chatroom
import com.capstone.momomeal.feature.adapter.ChatroomAdapter


class SearchResultFragment : BaseFragment<FragmentSearchResultBinding>(FragmentSearchResultBinding::inflate) {

    private val TAG = "SearchResultFragment"
    val chatlist = arrayListOf<Chatroom>(
        //test
        Chatroom("Bhc 뿌링클 뿌개실분 ~ ", 123, Category.Chicken, 3, "국민대학교 정문", 3.3, 1.1, listOf(7, 49, 89)),
        Chatroom("밤 12시에 족발 먹을 사람 있니?", 128, Category.BoiledPork, 3, "서울대입구 4번출구", 3.9, 1.1,  listOf(3, 29, 69))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val retView = super.onCreateView(inflater, container, savedInstanceState)
        binding.fragmentSearchResultBack.setOnClickListener{
            val activity = requireActivity() as MainActivity
            activity.comebackHome()
        }
        val chatAdapter = ChatroomAdapter(requireContext())
        chatAdapter.setItemClickListener(object : ChatroomAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                val item = chatAdapter.getData(position)
                val intent = Intent(activity, ChatActivity::class.java)
                intent.putExtra("id", item.idChatroom)
                startActivity(intent)
            }
        })
        binding.fragmentSearchResultRecycle.adapter = chatAdapter
        chatAdapter.replaceData(chatlist)
        return retView
    }
}