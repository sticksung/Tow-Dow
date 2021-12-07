package com.example.towdow

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.towdow.databinding.HomeFragmentBinding

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import org.json.JSONArray
import java.text.Normalizer
import java.util.ArrayList


class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? =null
    private val binding get() =_binding!!
    private lateinit var recyclerView: RecyclerView
    val adapter = ForumAdapter()
    val myTowDows = ArrayList<TowDowData>()
    private lateinit var database: DatabaseReference
    private val user = Firebase.auth.currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

     //   (activity as MainActivity)..menu.getItem(1).isChecked = true
        _binding = HomeFragmentBinding.inflate(inflater,container, false)
        val v = binding.root

        database = Firebase.database.reference

      //  val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(com.example.towdow.R.id.bottomNavigationView)
       // bottomNavigationView.visibility = View.VISIBLE

        binding.addCategoryImage.setImageResource(R.drawable.plus)
        binding.addCategoryImage.setOnClickListener {
            v.findNavController().navigate(R.id.action_homeFragment_to_forumCreateFragment)
        }

        binding.homeSearchBotton.setOnClickListener{
            v.findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        binding.homeProfileButtom.setOnClickListener{
            v.findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }


        // Adapter stuff
        initArray(myTowDows)
        Log.d("Debug", myTowDows.toString())

        database.child("Forums").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                for (i in snapshot.children) {
                    val forum: Forum = i.getValue(Forum::class.java)!!

                    if (user != null) {
                        if (forum.users.contains(user.uid.toString())) {
                            myTowDows.add(TowDowData(forum.name!!, forum.description!!))
                        }
                    }
                }

                recyclerView = binding.myForumsList
                recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                recyclerView.adapter = adapter
                adapter.setLocations(myTowDows)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



        return v
    }

    private fun initArray(myDataset: MutableList<TowDowData>){
        myDataset.clear()

    }

    inner class ForumAdapter():
        RecyclerView.Adapter<ForumAdapter.AddressViewHolder>(){
        var locations = emptyList<TowDowData>()
        var users = ArrayList<String>()
        val forums = ArrayList<ArrayList<String>>()
       // var currentForum = String?

        override fun getItemCount(): Int {
            return locations.size
        }

        internal fun setLocations(locations: List<TowDowData>) {
            this.locations = locations
            notifyDataSetChanged()
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_view_towdow, parent, false)
            return AddressViewHolder(v)
        }

        fun isUser(): Boolean {
            for (i in users) {
                if (user != null) {
                    if (user.email == i)
                        return true
                }
            }
            return false
        }

        override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {

            holder.view.findViewById<TextView>(R.id.towdow_name).text=locations[position].forum_name
            holder.view.findViewById<TextView>(R.id.short_description).text=locations[position].short_description

            holder.itemView.setOnClickListener(){
                val bundle = Bundle()
                bundle.putString("name", locations[position].forum_name)
                // bundle.putDouble("lat", locations[position].lat)
                //  bundle.putDouble("long", locations[position].long)
                // Log.d("T05", "In home fragment Lat: ${locations[position].lat} Long: ${locations[position].lat}")

                view?.findNavController()?.navigate(R.id.action_homeFragment_to_forumHomeFragment, bundle)

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