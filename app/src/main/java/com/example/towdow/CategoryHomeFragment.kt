package com.example.towdow

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.towdow.databinding.CategoryHomeFragmentBinding
import com.example.towdow.databinding.HomeFragmentBinding
import com.google.firebase.database.*
import java.util.ArrayList


class CategoryHomeFragment : Fragment() {

    private var _binding: CategoryHomeFragmentBinding? =null
    private val binding get() =_binding!!
    private lateinit var createPosts: ImageView
    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    val adapter = TowListAdapter()
    val myTowDows = ArrayList<TowDowData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.category_home_fragment, container, false)

//        _binding = CategoryHomeFragmentBinding.inflate(inflater,container, false)
//        val v = binding.root
       // database = FirebaseDatabase.getInstance().getReference("0")

        createPosts = view.findViewById(R.id.create_post_image)
        createPosts.setImageResource(R.drawable.plus)
        createPosts.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_categoryHomeFragment_to_createPostFragment)
        }

      // Adapter stuff
        initArray(myTowDows)
        recyclerView = view.findViewById(R.id.forum_posts_list)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        adapter.setLocations(myTowDows)


        return view
    }
    private fun initArray(myDataset: MutableList<TowDowData>){
        myDataset.clear()

        myDataset.add(TowDowData("Post 1", "This is example 1"))
        myDataset.add(TowDowData("Post 2", "This is example 2"))
        myDataset.add(TowDowData("Post 3", "This is example 3"))


    }



    inner class TowListAdapter():
        RecyclerView.Adapter<TowListAdapter.AddressViewHolder>(){
        private var locations = emptyList<TowDowData>()

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

                view?.findNavController()?.navigate(R.id.action_categoryHomeFragment_to_forumPostFragment, bundle)

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