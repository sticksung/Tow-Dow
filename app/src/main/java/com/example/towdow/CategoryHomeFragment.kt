package com.example.towdow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.towdow.databinding.CategoryHomeFragmentBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.ArrayList


class CategoryHomeFragment : Fragment() {

    private var _binding: CategoryHomeFragmentBinding? =null
    private val binding get() =_binding!!
    private lateinit var createPosts: ImageView
    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var home: ImageButton

    val adapter = TowListAdapter()
    val myTowDows = ArrayList<TowDowData>()
    private val user = Firebase.auth.currentUser
    private lateinit var postName: String
    private lateinit var categoryName: String
    private lateinit var forumName: String
    private lateinit var description: String

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

        categoryName = this.arguments?.getString("category").toString()
        forumName = this.arguments?.getString("forum").toString()
        postName = this.arguments?.getString("post").toString()
        description = this.arguments?.getString("description").toString()
        view.findViewById<TextView>(R.id.category_name_text).text = categoryName

        createPosts = view.findViewById(R.id.create_post_image)
        createPosts.setImageResource(R.drawable.plus)
        createPosts.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("forum", forumName)
            bundle.putString("category", categoryName)
            view.findNavController().navigate(R.id.action_categoryHomeFragment_to_createPostFragment, bundle)
        }

        home = view.findViewById(R.id.home_home_button3)
        home.setOnClickListener {
            view.findNavController().navigate(R.id.action_categoryHomeFragment_to_homeFragment)
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
//                    println("snap: " + post.toString())
                    myTowDows.add(TowDowData(post?.name!!, post.username!!))
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

            holder.view.findViewById<TextView>(R.id.reply_user_text).text=locations[position].name
            holder.view.findViewById<TextView>(R.id.reply_text).text=locations[position].short_description

            holder.itemView.setOnClickListener(){
                val bundle = Bundle()
                bundle.putString("description", description)
                bundle.putString("post", locations[position].name)
                bundle.putString("category", categoryName)
                bundle.putString("forum", forumName)
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