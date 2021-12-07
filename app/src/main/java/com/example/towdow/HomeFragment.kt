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
import java.util.ArrayList


class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? =null
    private val binding get() =_binding!!
    private lateinit var recyclerView: RecyclerView
    val adapter = ForumAdapter()
    val myTowDows = ArrayList<TowDowData>()
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = HomeFragmentBinding.inflate(inflater,container, false)
        val v = binding.root

        database = Firebase.database.reference

        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(com.example.towdow.R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.VISIBLE

        binding.addCategoryImage.setImageResource(R.drawable.plus)
        binding.addCategoryImage.setOnClickListener {
            v.findNavController().navigate(R.id.action_homeFragment_to_forumCreateFragment)
        }

        binding.signoutButton.setOnClickListener {
            Firebase.auth.signOut()
            v.findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }

        // Adapter stuff
        initArray(myTowDows)
        Log.d("Debug", myTowDows.toString())

        database.child("Forums").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {
                    println("Init" + i.value.toString())
                    var str = i.value.toString()
                    str = str.substringAfter("name=")
                    val name = str.substringBefore(",")
                    str = str.substringAfter(", description=")
                    val description = str.substringBefore("}")

                    myTowDows.add(TowDowData(name, description))
                    println(myTowDows.toString())
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