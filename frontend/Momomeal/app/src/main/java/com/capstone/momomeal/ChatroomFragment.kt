package com.capstone.momomeal

import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.capstone.momomeal.api.MomomealService
import com.capstone.momomeal.data.ChatStatus
import com.capstone.momomeal.databinding.FragmentChatroomBinding
import com.capstone.momomeal.feature.BaseFragment
import com.capstone.momomeal.data.Chatroom
import com.capstone.momomeal.data.User_light
import com.capstone.momomeal.feature.adapter.ChatRoomViewHolder
import com.capstone.momomeal.feature.adapter.ChatroomAdapter
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.FirebaseDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChatroomFragment : BaseFragment<FragmentChatroomBinding>(FragmentChatroomBinding::inflate) {
    private val TAG = "ChatroomFragment"

    val momomeal = MomomealService.momomealAPI
    val chatroomAdapter: ChatroomAdapter by lazy {
        ChatroomAdapter(requireContext())
    }

    private val fireDatabase = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val retview = super.onCreateView(inflater, container, savedInstanceState)
        val mainactivity = requireActivity() as MainActivity
        chatroomAdapter.setItemClickListener(object : ChatroomAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                val intent = Intent(activity, ChatActivity::class.java)
                val chatroomInfo = chatroomAdapter.getData(position)

                val userArea = fireDatabase.child("chatroom")
                    .child(chatroomInfo.idChatroom.toString())
                    .child("users")
                userArea.get().addOnSuccessListener {
                    val a = it
                    if (it.hasChildren()) {
                    }
                }


                val myInfoLight = User_light((activity as MainActivity).myInfo)
                intent.putExtra("chatroominfo", chatroomInfo) // Chatroom information
                intent.putExtra("myinfo", myInfoLight)
                intent.putExtra("chatstatus", ChatStatus.ENTER)
                startActivity(intent)
            }
        })
        binding.fragmentChatroomToolbar.inflateMenu(R.menu.menu_chat_room)
        binding.fragmentChatroomRecycle.adapter = chatroomAdapter
        updateMyChatRoom()

        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback (
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val mainactivity = requireActivity() as MainActivity
                //Log.d("onswipe",viewHolder.adapterPosition.toString())

                val deleted_chat_room = chatroomAdapter.getData(viewHolder.layoutPosition)
                chatroomAdapter.removeData(viewHolder.layoutPosition)

                // ???????????? ??? ????????? ???????????????.
                momomeal.deleteChatroom(
                    mainactivity.myInfo.idUser, deleted_chat_room.idChatroom
                ).enqueue( object: Callback<HashMap<String, Int>>{
                    override fun onResponse(
                        call: Call<HashMap<String, Int>>,
                        response: Response<HashMap<String, Int>>
                    ) {
                        if(response.isSuccessful.not()){
                            return
                        }
                    }
                    override fun onFailure(call: Call<HashMap<String, Int>>, t: Throwable) {
                        Toast.makeText(requireContext(), "?????? ??????", Toast.LENGTH_SHORT).show()
                    }
                })

                // Firebase ?????? ??????????????? ??? ????????? ????????????.

                val userArea = fireDatabase.child("chatroom")
                    .child(deleted_chat_room.idChatroom.toString())
                    .child("users")
                userArea.child(mainactivity.myInfo.idUser.toString())
                    .removeValue().addOnSuccessListener {
                        userArea.get().addOnSuccessListener {
                            if (!it.hasChildren()) {
                                // ?????? ?????? ?????? ????????? ????????? ?????????, Firebase ???????????? ???????????? ????????? ???????????????.
                                fireDatabase.child("chatroom")
                                    .child(deleted_chat_room.idChatroom.toString()).removeValue()
                            }
                        }
                }
            }
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val icon: Bitmap
                // actionState??? SWIPE ????????? ??? ????????? ??????????????? ????????? ????????? ??????????????? ???
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView
                    val height = (itemView.bottom - itemView.top).toFloat()
                    val width = height / 4
                    val paint = Paint()
                    if (dX < 0) {  // ???????????? ????????????????????? ??????
                        // ViewHolder??? ?????????????????? ????????? ???????????? ????????? ????????? ??????
                        paint.color = Color.parseColor("#ff0000")
                        val background = RectF(itemView.right.toFloat() + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
                        c.drawRect(background, paint)

                        // ????????? ???????????? ????????? ????????? ???????????? ???????????? ?????????
                        // ????????? ???????????? Image Asset ???????????? ???????????? drawable ????????? ??????????????? ???
                        icon = BitmapFactory.decodeResource(activity?.applicationContext?.resources, R.drawable.ic_menu_delete)
                        val iconDst = RectF(itemView.right.toFloat() - 3 * width, itemView.top.toFloat() + width, itemView.right.toFloat() - width, itemView.bottom.toFloat() - width)
                        c.drawBitmap(icon, null, iconDst, null)

                    }
                }

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(binding.fragmentChatroomRecycle)

        return retview
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden){
            updateMyChatRoom()
        }
    }

    fun updateMyChatRoom(){
        val mainActivity = requireActivity() as MainActivity

        momomeal.getEnteredChatroom(mainActivity.myInfo.idUser).enqueue(object: Callback<List<Chatroom>>{
            override fun onResponse(call: Call<List<Chatroom>>, response: Response<List<Chatroom>>) {
                //Log.d("retrofit", response?.body().toString())
                if(response.isSuccessful.not()){
                    return
                }
                response.body()?.let{
                    chatroomAdapter.replaceData(ArrayList<Chatroom>(it))
                }
            }
            override fun onFailure(call: Call<List<Chatroom>>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }
        })
    }
}
