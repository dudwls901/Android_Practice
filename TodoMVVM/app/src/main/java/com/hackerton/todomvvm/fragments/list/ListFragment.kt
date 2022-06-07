package com.hackerton.todomvvm.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar
import com.hackerton.todomvvm.R
import com.hackerton.todomvvm.data.model.ToDoData
import com.hackerton.todomvvm.data.viewmodel.ToDoViewModel
import com.hackerton.todomvvm.databinding.FragmentListBinding
import com.hackerton.todomvvm.fragments.SharedViewModel
import com.hackerton.todomvvm.fragments.list.adapter.ListAdapter
import com.hackerton.todomvvm.utils.observeOnce
import jp.wasabeef.recyclerview.animators.LandingAnimator
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import java.text.FieldPosition

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = requireNotNull(_binding)
//    private lateinit var binding: FragmentListBinding

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        binding.sharedVM = mSharedViewModel

        //Setup RecyclerView
        setUpRecyclerview()

        observeDatas()

        //set Menu
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun observeDatas() {
        mToDoViewModel.getAllData.observe(viewLifecycleOwner) { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        }
    }

    //디스트로이뷰에서 바인딩에 널 넣어줘야 메모리릭 방지됨!!!!!!!!!!!!!!
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRecyclerview() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.itemAnimator = SlideInRightAnimator().apply {
            addDuration = 300
        }

        swipeToDelete()
    }

    private fun swipeToDelete() {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.dataList[viewHolder.adapterPosition]
                //Delete Item
                mToDoViewModel.deleteItem(deletedItem)
                //notify안 해도 정상 동작
                //아마 ItemTouchHelper.SimpleCallback 내부에서 구현되어있지 않을까
//                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                restoreDeletedData(viewHolder.itemView, deletedItem, viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun restoreDeletedData(view: View, deletedItem: ToDoData, position: Int){
        val snackBar = Snackbar.make(
            view,
            "Deleted '${deletedItem.title}'",
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction("Undo"){
            mToDoViewModel.insertData(deletedItem)
//            adapter.notifyItemChanged(position)
        }
        snackBar.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> confirmRemoval()
            R.id.menu_priority_high -> mToDoViewModel.sortByHighPriority.observe(viewLifecycleOwner){ adapter.setData(it)}
            R.id.menu_priority_low -> mToDoViewModel.sortByLowPriority.observe(viewLifecycleOwner){ adapter.setData(it)}
        }
        return super.onOptionsItemSelected(item)
    }

    //Show AlertDialgog to Confirm Removal of All Items from Database Table
    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mToDoViewModel.deleteAll()
            Toast.makeText(
                requireContext(),
                "Successfully Removed Everything!",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete everyThing?")
        builder.setMessage("Are you sure you want to remove everyThing?")
        builder.create().show()
    }

    /*
    * 검색 기능 구현
    * 1.SearchView.OnQueryTextListener 상속
    * 2.onQueryTextSubmit, onQueryTextChange 오버라이딩
    * 3.searchQuery 실행하여 data받아오고 observe하여 리사이클러뷰 어댑터에 갱신
    * 4.searchView에 리스너 연결
    * import androidx.appcompat.widget.SearchView
    * import android.widget.SearchView가 자동 import됨에 주의
    * */

    //press Enter
    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null){
            searchThroughDatabase(query)
        }
        return true
    }

    //typing
    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
            searchThroughDatabase(query)
        }
        return true
    }

    private fun searchThroughDatabase(query: String) {
        val searchQuery = "%$query%"
        //searchQuery를 포함한 title 검색
        mToDoViewModel.searchDatabase(searchQuery).observeOnce(viewLifecycleOwner){ list->
            list?.let {
                Log.d("listfragment", "searchThroughDatabase: ")
                adapter.setData(it)
            }
        }
    }

}