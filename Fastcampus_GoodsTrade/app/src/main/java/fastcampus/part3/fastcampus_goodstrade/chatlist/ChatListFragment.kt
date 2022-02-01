package fastcampus.part3.fastcampus_goodstrade.chatlist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import fastcampus.part3.fastcampus_goodstrade.R
import fastcampus.part3.fastcampus_goodstrade.chatdetail.ChatRoomActivity
import fastcampus.part3.fastcampus_goodstrade.databinding.FragmentChatlistBinding
import fastcampus.part3.fastcampus_goodstrade.key.DBKey
import fastcampus.part3.fastcampus_goodstrade.key.DBKey.Companion.CHILD_CHAT

class ChatListFragment : Fragment(R.layout.fragment_chatlist){

    private lateinit var binding: FragmentChatlistBinding

    private lateinit var chatListAdapter: ChatListAdapter

    private val auth: FirebaseAuth by lazy{
        Firebase.auth
    }

    private lateinit var chatDB: DatabaseReference

    private val chatRoomList = mutableListOf<ChatListItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatlistBinding.bind(view)

        chatListAdapter = ChatListAdapter(onItemClicked = {chatRoom ->
            //채팅방으로 이동하는 코드
            context?.let{
                val intent = Intent(it, ChatRoomActivity::class.java)
                intent.putExtra("chatKey",chatRoom.key)
                startActivity(intent)
            }


        })

        binding.chatListRecyclerView.adapter = chatListAdapter
        binding.chatListRecyclerView.layoutManager = LinearLayoutManager(context)

        chatRoomList.clear()

        if(auth.currentUser == null){
            return
        }

        chatDB = Firebase.database.reference.child(DBKey.DB_USERS).child(auth.currentUser!!.uid).child(CHILD_CHAT)

        chatDB.addListenerForSingleValueEvent(object: ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                //CHILD_CHAT == chat이라는 하나의 데이터를 가져오는데 그 하나의 데이터는 리스트로, 내부에 여러 값이 있음
                //-> childer, forEach
                snapshot.children.forEach{
                    val model = it.getValue(ChatListItem::class.java)
                    model?: return
                    chatRoomList.add(model)
                }
                chatListAdapter.submitList(chatRoomList)
                chatListAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })



    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        chatListAdapter.notifyDataSetChanged()
    }

}