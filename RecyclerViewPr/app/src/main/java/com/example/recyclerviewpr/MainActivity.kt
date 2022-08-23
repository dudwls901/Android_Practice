package com.example.recyclerviewpr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.*
import com.example.recyclerviewpr.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var concatAdapter: ConcatAdapter
    private val headerAdapter: HeaderAdapter by lazy {
        HeaderAdapter()
    }
    private val profileAdapter: ProfileAdapter by lazy {
        ProfileAdapter(){
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
        }
    }
    private val footerAdapter: FooterAdapter by lazy {
        FooterAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
    }

    private fun initRecyclerView() = with(binding) {

        val profileList = List(30) { Profile(it.toLong(), it, it, it) }
        mainRecyclerView.apply {
            headerAdapter.setData("HEADER")
            footerAdapter.setData("FOOTER")
            profileAdapter.submitList(profileList)
            val itemTouchHelperCallback = getItemTouchHelperCallback()
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    LinearLayoutManager.VERTICAL
                )
            )
            ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(this)
            concatAdapter = ConcatAdapter(headerAdapter, profileAdapter, footerAdapter)
            adapter = concatAdapter
//            adapter = profileAdapter
        }
    }

    private fun getItemTouchHelperCallback() = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {

        override fun getDragDirs(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return when(viewHolder){
                is MainViewHolders.ProfileViewHolder -> super.getDragDirs(recyclerView, viewHolder)
                else -> ItemTouchHelper.ACTION_STATE_IDLE //0, ProfileViewHolder 제외한 나머지는 trigger x
            }
        }

        override fun getSwipeDirs(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return when(viewHolder){
                is MainViewHolders.ProfileViewHolder -> super.getSwipeDirs(recyclerView, viewHolder)
                else -> ItemTouchHelper.ACTION_STATE_IDLE //0, ProfileViewHolder 제외한 나머지는 trigger x
            }
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            //header 포지션 뺀 값
            try {
                profileAdapter.moveItem(
                    viewHolder.absoluteAdapterPosition - 1,
                    target.absoluteAdapterPosition - 1
                )
            } catch (e: Exception) {
                Log.e("main", "onMove: ")
            }
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            try {
                //header 포지션 뺀 값
                profileAdapter.removeItem(viewHolder.layoutPosition - 1)
            } catch (e: Exception) {
                Log.e("main", "onSwiped: $e")
            }
        }

        override fun onSelectedChanged(
            viewHolder: RecyclerView.ViewHolder?,
            actionState: Int
        ) {
            super.onSelectedChanged(viewHolder, actionState)
            try {
                when (actionState) {
                    ItemTouchHelper.ACTION_STATE_DRAG, ItemTouchHelper.ACTION_STATE_SWIPE -> {
                        (viewHolder as MainViewHolders.ProfileViewHolder).setAlpha(0.5f)
                    }
                }
            } catch (e: Exception) {
                Log.e("main", "onSelectedChanged: $e")
            }
        }

        override fun clearView(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ) {
            super.clearView(recyclerView, viewHolder)
            try {
                (viewHolder as MainViewHolders.ProfileViewHolder).setAlpha(1.0f)
            } catch (e: Exception) {
                Log.e("main", "clearView: $e")
            }
        }
    }
}