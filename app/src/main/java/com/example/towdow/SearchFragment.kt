package com.example.towdow

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.towdow.databinding.SearchFragmentBinding

import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.ArrayList


class SearchFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: SearchFragmentBinding? =null
    private val binding get() =_binding!!
    private lateinit var recyclerView: RecyclerView
    val adapter = TowListAdapter()
    val myTowDows = ArrayList<TowDowData>()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = SearchFragmentBinding.inflate(inflater,container, false)
        val v = binding.root

        setHasOptionsMenu(true)

        binding.searchImageButton.setImageResource(R.drawable.search1)
        // Adapter stuff
        initArray(myTowDows)
        Log.d("Debug", myTowDows.toString())

        recyclerView = binding.searchForumsList
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        adapter.setLocations(myTowDows)

        return v
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_bar_menu, menu)

        val item = menu.findItem(R.id.search_movie)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(item: String?): Boolean {
                //adapter.search(item)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }


        })

        searchView.setOnQueryTextListener(this)
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

                view?.findNavController()?.navigate(R.id.action_searchFragment_to_categoryHomeFragment, bundle)

            }

        }



        inner class AddressViewHolder(val view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
            override fun onClick(view: View?){
                if (view != null) {

                }
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
      //  adapter.search(query)
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
//            R.id.restore_list ->{
//               //action to be performed
//            }
        }
        return super.onOptionsItemSelected(item)
    }
}
