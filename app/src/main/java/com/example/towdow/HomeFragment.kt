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
import java.util.ArrayList


class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? =null
    private val binding get() =_binding!!
    private lateinit var recyclerView: RecyclerView
    val adapter = TowListAdapter()
    val myTowDows = ArrayList<TowDowData>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = HomeFragmentBinding.inflate(inflater,container, false)
        val v = binding.root


        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(com.example.towdow.R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.VISIBLE

        binding.addCategoryImage.setImageResource(R.drawable.plus)
        binding.addCategoryImage.setOnClickListener {
            v?.findNavController()?.navigate(R.id.action_homeFragment_to_forumCreateFragment)
        }


        // Adapter stuff
        initArray(myTowDows)
        Log.d("Debug", myTowDows.toString())

        recyclerView = binding.myForumsList
        recyclerView.adapter = adapter
        adapter.setLocations(myTowDows)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)


        return v
    }

    private fun initArray(myDataset: MutableList<TowDowData>){
        myDataset.clear()

        myDataset.add(TowDowData("BIO 1004", "This forum is for Dr. T's Biological Science Class."))
        myDataset.add(TowDowData("MATH 2114", "Linear Algebra at Virginia Tech."))
        myDataset.add(TowDowData("HIST 3134", "Profressor Hess's history forum!"))
    }

    inner class TowListAdapter():
        RecyclerView.Adapter<TowListAdapter.AddressViewHolder>(){
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
                // bundle.putDouble("lat", locations[position].lat)
                //  bundle.putDouble("long", locations[position].long)
                // Log.d("T05", "In home fragment Lat: ${locations[position].lat} Long: ${locations[position].lat}")

               // view?.findNavController()?.navigate(R.id.action_homeFragment_to_forumHomeFragmentomeFragment_to_forumPostFragment, bundle)

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