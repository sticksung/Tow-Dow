package com.example.towdow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.towdow.databinding.ActivityMainBinding.inflate
import com.example.towdow.databinding.ForumHomeFragmentBinding
import com.example.towdow.databinding.HomeFragmentBinding
import com.example.towdow.databinding.SignupFragmentBinding.inflate
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.ArrayList

class ForumHomeFragment : Fragment() {

    //private lateinit var binding: ForumHomeFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var plus: ImageView
    private lateinit var forumName: String

    private lateinit var database: DatabaseReference
    private val user = Firebase.auth.currentUser

    val adapter = CategoryAdapter()
    val myTowDows = ArrayList<TowDowData>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.forum_home_fragment, container, false)

        //binding = ForumHomeFragmentBinding.inflate(inflater)

        database = Firebase.database.reference

        plus = view.findViewById(R.id.add_category_image)
        plus.setImageResource(R.drawable.plus);
        plus.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_forumHomeFragment_to_createCategoryFragment)
        }

        view.findViewById<Button>(R.id.delete_forum_button).setOnClickListener {
            database.child("Forums").addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (i in snapshot.children) {
                        val forum: Forum = i.getValue(Forum::class.java)!!

                        if (user != null) {
                            if (forum.users.contains(user.uid)) {
                                if (forum.name == forumName) {
                                    forum.users.remove(user.uid)
                                    println("Removed?" +  forum.users.remove(user.uid))
                                    view?.findNavController()?.navigate(R.id.action_forumHomeFragment_to_homeFragment)
                                }
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

      //  binding.forumTitleText.text = "Science"

        // Adapter stuff
        initArray(myTowDows)
        recyclerView = view.findViewById(R.id.forum_categories_list)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        adapter.setLocations(myTowDows)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        forumName = this.arguments?.getString("name").toString()
    }

    private fun initArray(myDataset: MutableList<TowDowData>){
        myDataset.clear()

        myDataset.add(TowDowData("Science", "Science description"))
        myDataset.add(TowDowData("Math", "Math description"))
        myDataset.add(TowDowData("History", "History description"))


    }
    inner class CategoryAdapter():
        RecyclerView.Adapter<CategoryAdapter.AddressViewHolder>(){
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

                view?.findNavController()?.navigate(R.id.action_forumHomeFragment_to_categoryHomeFragment, bundle)

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