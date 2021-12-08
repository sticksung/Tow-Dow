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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text
import java.util.ArrayList


class CategoryHomeFragment : Fragment() {

    private var _binding: CategoryHomeFragmentBinding? =null
    private val binding get() =_binding!!
    private lateinit var createPosts: ImageView
    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    val adapter = TowListAdapter()
    val myTowDows = ArrayList<TowDowData>()
    private val user = Firebase.auth.currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.category_home_fragment, container, false)

//        _binding = CategoryHomeFragmentBinding.inflate(inflater,container, false)
//        val v = binding.root
       // database = FirebaseDatabase.getInstance().getReference("0")


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        database = Firebase.database.reference

        val categoryName = this.arguments?.getString("category").toString()
        val forumName = this.arguments?.getString("forum").toString()
        val postName = this.arguments?.getString("post").toString()
        view.findViewById<TextView>(R.id.category_name_text).text = categoryName

        createPosts = view.findViewById(R.id.create_post_image)
        createPosts.setImageResource(R.drawable.plus)
        createPosts.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("forum", forumName)
            bundle.putString("category", categoryName)
            view.findNavController().navigate(R.id.action_categoryHomeFragment_to_createPostFragment, bundle)
        }

        // Adapter stuff
        initArray(myTowDows)
        recyclerView = view.findViewById(R.id.forum_posts_list)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        adapter.setLocations(myTowDows)


        database.child("Forums").child(forumName).child("Categories").child(categoryName).child("Posts").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {
                    val post: Post? = i.getValue(Post::class.java)
                    println("snap: " + post.toString())
                    myTowDows.add(TowDowData(post?.name!!, user?.email.toString()))
                }

//                recyclerView = binding.myForumsList
//                recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                recyclerView.adapter = adapter
                adapter.setLocations(myTowDows)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun initArray(myDataset: MutableList<TowDowData>){
        myDataset.clear()

//        myDataset.add(TowDowData("Post 1", "This is example 1"))
//        myDataset.add(TowDowData("Post 2", "This is example 2"))
//        myDataset.add(TowDowData("Post 3", "This is example 3"))


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

            holder.view.findViewById<TextView>(R.id.towdow_name).text=locations[position].name
            holder.view.findViewById<TextView>(R.id.short_description).text=locations[position].short_description

            holder.itemView.setOnClickListener(){
                val bundle = Bundle()
                bundle.putString("name", locations[position].name)
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