package com.example.towdow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        forumName = this.arguments?.getString("name").toString()

        database = Firebase.database.reference

        plus = view.findViewById(R.id.add_category_image)
        plus.setImageResource(R.drawable.plus);
        plus.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("name", forumName)
            view.findNavController().navigate(R.id.action_forumHomeFragment_to_createCategoryFragment, bundle)
        }

        view.findViewById<TextView>(R.id.forum_title_text).text = forumName

        initArray(myTowDows)
        recyclerView = view.findViewById(R.id.forum_categories_list)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        adapter.setLocations(myTowDows)

        database.child("Forums").child(forumName).child("Categories").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {
                    val category: Category? = i.getValue(Category::class.java)
                    myTowDows.add(TowDowData(category?.name!!, category.description!!))
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

//        myDataset.add(TowDowData("Science", "Science description"))
//        myDataset.add(TowDowData("Math", "Math description"))
//        myDataset.add(TowDowData("History", "History description"))


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

            holder.view.findViewById<TextView>(R.id.reply_user_text).text=locations[position].name
            holder.view.findViewById<TextView>(R.id.reply_text).text=locations[position].short_description

            holder.itemView.setOnClickListener(){
                val bundle = Bundle()
                bundle.putString("category", locations[position].name)
                bundle.putString("forum", forumName)
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