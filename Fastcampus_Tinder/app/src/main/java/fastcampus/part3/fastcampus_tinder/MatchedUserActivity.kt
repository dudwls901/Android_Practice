package fastcampus.part3.fastcampus_tinder

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import fastcampus.part3.fastcampus_tinder.adapter.MatchedUserAdapter
import fastcampus.part3.fastcampus_tinder.databinding.ActivityMatchBinding
import fastcampus.part3.fastcampus_tinder.model.CardItem

class MatchedUserActivity: AppCompatActivity() {

    private lateinit var binding : ActivityMatchBinding

    private lateinit var auth: FirebaseAuth

    private lateinit var userDB: DatabaseReference

    private val adapter = MatchedUserAdapter()
    //모델 새로 만드는 게 좋음 일단 그냥 재활용
    private val cardItems = mutableListOf<CardItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        userDB = Firebase.database.reference.child("Users")

        initMatchedUserRecyclerView()
        getMatchUsers()
    }

    private fun getCurrentUserID(): String {
        if (auth.currentUser == null) {
            Toast.makeText(this, "로그인이 되어있지 않습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
        return auth.currentUser?.uid.orEmpty()
    }

    private fun initMatchedUserRecyclerView(){
        binding.matchedUserRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.matchedUserRecyclerView.adapter = adapter
    }

    private fun getMatchUsers(){
        val matchedDB = userDB.child(getCurrentUserID()).child("likedBy").child("match")
        matchedDB.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if(snapshot.key?.isNotEmpty() == true){
                    getUserByKey(snapshot.key.orEmpty())
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun getUserByKey(userId: String){
            userDB.child(userId).addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    cardItems.add(CardItem(userId,snapshot.child("name").value.toString()))
                    adapter.submitList(cardItems)
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }
}