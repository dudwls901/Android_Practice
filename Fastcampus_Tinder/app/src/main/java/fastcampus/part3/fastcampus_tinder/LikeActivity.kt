package fastcampus.part3.fastcampus_tinder

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import fastcampus.part3.fastcampus_tinder.DBKey.Companion.DIS_LIKE
import fastcampus.part3.fastcampus_tinder.DBKey.Companion.LIKE
import fastcampus.part3.fastcampus_tinder.DBKey.Companion.LIKED_BY
import fastcampus.part3.fastcampus_tinder.DBKey.Companion.MATCH
import fastcampus.part3.fastcampus_tinder.DBKey.Companion.NAME
import fastcampus.part3.fastcampus_tinder.DBKey.Companion.USERS
import fastcampus.part3.fastcampus_tinder.DBKey.Companion.USER_ID
import fastcampus.part3.fastcampus_tinder.adapter.CardItemAdapter
import fastcampus.part3.fastcampus_tinder.databinding.ActivityLikeBinding
import fastcampus.part3.fastcampus_tinder.model.CardItem

class LikeActivity : AppCompatActivity(), CardStackListener {

    private lateinit var binding: ActivityLikeBinding

    private lateinit var auth: FirebaseAuth

    private lateinit var userDB: DatabaseReference

    private val adapter = CardItemAdapter()
    private val cardItems = mutableListOf<CardItem>()

    private val manager by lazy {
        CardStackLayoutManager(this, this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLikeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        userDB = Firebase.database.reference.child(USERS)

        val currentUserDB = userDB.child(getCurrentUserID())
        //db?????? ?????? ???????????? ????????? ?????? ???????????? ??????
        currentUserDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child(NAME).value == null) {
                    showNameInputPopUp()
                    return
                }
                // ?????? ?????? ??????
                getUnSelectedUsers()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        initCardStackView()
        initSignOutButton()
        initMatchedListButton()
    }

    private fun initSignOutButton(){
        binding.signOutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun initMatchedListButton(){
        binding.matchListButton.setOnClickListener {

            startActivity(Intent(this, MatchedUserActivity::class.java))

        }
    }

    private fun initCardStackView() {
        binding.cardStackView.layoutManager = manager
        binding.cardStackView.adapter = adapter
    }

    private fun getUnSelectedUsers() {
        //?????? ???????????? ?????????
        userDB.addChildEventListener(object : ChildEventListener {
            //?????? ????????? ???????????? ??????, ?????? ?????? ????????? ????????? ????????? ???????????? ???????????? add ?????? ??????
            @SuppressLint("NotifyDataSetChanged")
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                //?????? ?????? ?????? ????????? ?????? ?????????,
                //???????????? likedBy??? like??? ?????? ??????, ???????????? likedBy??? dislike??? ?????? ?????? ??????
                if (snapshot.child(USER_ID).value != getCurrentUserID()
                    && snapshot.child(LIKED_BY).child(LIKE).hasChild(getCurrentUserID()).not()
                    && snapshot.child(LIKED_BY).child(DIS_LIKE).hasChild(getCurrentUserID()).not()
                ) {

                    val userId = snapshot.child(USER_ID).value.toString()
                    var name = "undecided"
                    if (snapshot.child(NAME).value != null) {
                        name = snapshot.child(NAME).value.toString()
                    }
                    cardItems.add(CardItem(userId, name))
                    adapter.submitList(cardItems)
                    adapter.notifyDataSetChanged()
                }
            }

            //????????? ????????? ???, ?????? ????????? ?????? ?????? ????????? ?????? ??? ??????
            @SuppressLint("NotifyDataSetChanged")
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                //????????? ??? ?????????
                cardItems.find { it.userId == snapshot.key }?.let {
                    it.name = snapshot.child("name").value.toString()
                }
                adapter.submitList(cardItems)
                adapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun showNameInputPopUp() {
        val editText = EditText(this)
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.write_name))
            .setView(editText)
            .setPositiveButton("??????") { _, _ ->
                if (editText.text.isEmpty()) {
                    showNameInputPopUp()
                } else {
                    saveUserName(editText.text.toString())
                }
            }
            .setCancelable(false)
            .show()
    }

    private fun saveUserName(name: String) {

        val userId = getCurrentUserID()
        val currentUserDB = userDB.child(userId)
        val user = mutableMapOf<String, Any>()
        user["userId"] = userId
        user["name"] = name
        currentUserDB.updateChildren(user)

        //?????? ?????? ????????????
        getUnSelectedUsers()
    }

    private fun getCurrentUserID(): String {
        if (auth.currentUser == null) {
            Toast.makeText(this, "???????????? ???????????? ????????????.", Toast.LENGTH_SHORT).show()
            finish()
        }
        return auth.currentUser?.uid.orEmpty()
    }

    private fun like() {
        val card = cardItems[manager.topPosition - 1]
        cardItems.removeFirst()

        userDB.child(card.userId)
            .child("likedBy")
            .child("like")
            .child(getCurrentUserID())
            .setValue(true)

        Toast.makeText(this, "${card.name}?????? Like ???????????????.", Toast.LENGTH_SHORT).show()
        //todo ????????? ??? ????????? ????????????.
        saveMatchIfOtherUserLikedMe(card.userId)
    }

    private fun disLike() {
        val card = cardItems[manager.topPosition - 1]
        cardItems.removeFirst()

        userDB.child(card.userId)
            .child(LIKED_BY)
            .child(DIS_LIKE)
            .child(getCurrentUserID())
            .setValue(true)

        Toast.makeText(this, "${card.name}?????? disLike ???????????????.", Toast.LENGTH_SHORT).show()
    }

    private fun saveMatchIfOtherUserLikedMe(otherUserId: String) {
        //??? db??? ????????? ???????????? ?????? like??? id
        val otherUserDB = userDB.child(getCurrentUserID()).child("likedBy").child("like").child(otherUserId)
        //??? ?????? ????????? ????????? ??? ??? ???(????????? ????????????)??? ?????????
        otherUserDB.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //snapshot.value??? true??? ?????? ???????????? like??? ???????????? ????????? otherUserDB??? ?????? like??? ??????
                if(snapshot.value ==true){
                    //?????? like??? ??????(??????)
                    userDB.child(getCurrentUserID())
                        .child(LIKED_BY)
                        .child(MATCH)
                        .child(otherUserId)
                        .setValue(true)

                    userDB.child(otherUserId)
                        .child(LIKED_BY)
                        .child(MATCH)
                        .child(getCurrentUserID())
                        .setValue(true)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {}

    override fun onCardSwiped(direction: Direction?) {
        when (direction) {
            Direction.Right -> like()
            Direction.Left -> disLike()
            else -> {

            }
        }

    }

    override fun onCardRewound() {}

    override fun onCardCanceled() {}

    override fun onCardAppeared(view: View?, position: Int) {}

    override fun onCardDisappeared(view: View?, position: Int) {}
}