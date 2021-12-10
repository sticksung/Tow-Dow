package com.example.towdow

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.towdow.databinding.SearchFragmentBinding
import com.google.firebase.auth.ktx.auth

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.ArrayList


class SearchFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: SearchFragmentBinding? =null
    private val binding get() =_binding!!
    private lateinit var recyclerView: RecyclerView
    val adapter = TowListAdapter()
    val myTowDows = ArrayList<TowDowData>()
    private lateinit var database: DatabaseReference
    val model: MyViewModel by viewModels()
    private val user = Firebase.auth.currentUser

    override fun onQueryTextChange(newText: String?): Boolean {

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        adapter.search(query)
        return true
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_bar_menu, menu)

        val item = menu.findItem(R.id.search_movie)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(item: String?): Boolean {
                adapter.search(item)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }


        })


        //   val searchView: SearchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(this)

        //  return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
//            R.id.restore_list ->{
//               //action to be performed
//            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = SearchFragmentBinding.inflate(inflater,container, false)
        val v = binding.root

        recyclerView = binding.searchForumsList
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        adapter.setLocations(myTowDows)

        model.forums.observe(
            viewLifecycleOwner,
            Observer<List<TowDowData>>{ tows ->
                tows?.let{
                    adapter.setLocations(it)
                    Log.d("T05", it.toString())
                }
            }
        )

        database = Firebase.database.reference

        setHasOptionsMenu(true)


        // Adapter stuff
        initArray(myTowDows)
        Log.d("Debug", myTowDows.toString())

        database.child("Forums").addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {
                    val forum: Forum = i.getValue(Forum::class.java)!!

                    model.forumItems?.add(TowDowData(forum.name!!, forum.description!!))
                    model.forums.postValue(model.forumItems)
                }

                //recyclerView = binding.searchForumsList
                //recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                recyclerView.adapter = adapter
                adapter.setLocations(myTowDows)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        binding.homeSearchButton.setOnClickListener{
            v.findNavController().navigate(R.id.action_searchFragment_to_homeFragment)
        }

        binding.restore.setOnClickListener{
            adapter.restore()
        }
        binding.profileSearchButton.setOnClickListener{
            v.findNavController().navigate(R.id.action_searchFragment_to_profileFragment)
        }

        return v
    }


    private fun initArray(myDataset: MutableList<TowDowData>){
        myDataset.clear()

    }

    inner class TowListAdapter():
        RecyclerView.Adapter<TowListAdapter.AddressViewHolder>(){
        var locations = emptyList<TowDowData>()
        private var locationsBackup= emptyList<TowDowData>()
        override fun getItemCount(): Int {
            return locations.size
        }

        internal fun setLocations(locations: List<TowDowData>) {
            this.locations = locations
            locationsBackup = locations
            notifyDataSetChanged()
        }
        fun restore(){
            locations = locationsBackup
            notifyDataSetChanged()
        }
        fun search(query: String?) {
            if (query != null) {
                Log.d("Search", query)
            }

            locations = locations.filter{it.name.contains(query!!, ignoreCase = true)}

            notifyDataSetChanged()

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_view_search, parent, false)
            return AddressViewHolder(v)
        }
        override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {

            val forumName = locations[position].name
            holder.view.findViewById<TextView>(R.id.reply_user_text).text=locations[position].name
            holder.view.findViewById<TextView>(R.id.reply_text).text=locations[position].short_description

            holder.view.findViewById<Button>(R.id.add_forum_button).setOnClickListener {

                database.child("Forums").addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {

                        //model.forumItems?.clear()

                        for (i in snapshot.children) {
                            val forum: Forum = i.getValue(Forum::class.java)!!

                            if (forum.name == forumName) {
                                if (user != null) {
                                    val users = ArrayList<String>()
                                    users.add(user.uid)
                                    for (b in forum.users) {
                                        if (!users.contains(b)) {
                                            users.add(b)
                                        }
                                    }
                                    database.child("Forums").child("$forumName/users").setValue(users)
                                    return
                                }
                            }
                        }
                        //   adapter.setLocations(model.forums)
                        adapter.notifyDataSetChanged()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }

            holder.itemView.setOnClickListener(){
            }


        }



        inner class AddressViewHolder(val view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
            override fun onClick(view: View?){
                if (view != null) {

                }
            }
        }
    }

}
