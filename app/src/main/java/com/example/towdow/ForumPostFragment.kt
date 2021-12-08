package com.example.towdow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

class ForumPostFragment : Fragment() {

    private var _binding: ForumPostFragment? =null
    private val binding get() =_binding!!
    private lateinit var postName: String
    private lateinit var categoryName: String
    private lateinit var forumName: String
    private lateinit var description: String
    private lateinit var database: DatabaseReference
    private val user = Firebase.auth.currentUser
    private lateinit var recyclerView: RecyclerView
    val myTowDows = ArrayList<TowDowData>()
    val adapter = TowListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.forum_post_fragment, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        database = Firebase.database.reference

        categoryName = this.arguments?.getString("category").toString()
        forumName = this.arguments?.getString("forum").toString()
        postName = this.arguments?.getString("post").toString()
        description = this.arguments?.getString("description").toString()

        recyclerView = view.findViewById(R.id.reply_list)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        adapter.setLocations(myTowDows)


        database.child("Forums").child(forumName).child("Categories").child(categoryName).child("Posts").addListenerForSingleValueEvent(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {
                    val post: Post? = i.getValue(Post::class.java)
                    if (post != null) {
                        if (post.name.toString() == postName) {
                            view.findViewById<TextView>(R.id.username_text).text = post.username
                            view.findViewById<TextView>(R.id.post_description_text).text = post.description.toString()
                            break
                        }
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        database.child("Forums").child(forumName).child("Categories").child(categoryName).child("Posts").child(postName).child("Replies").addListenerForSingleValueEvent(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {
                    val reply: Reply? = i.getValue(Reply::class.java)
                    myTowDows.add(TowDowData(reply?.username.toString(), reply?.reply.toString()))
                }

                recyclerView.adapter = adapter
                adapter.setLocations(myTowDows)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        view.findViewById<TextView>(R.id.post_title_text).text = postName

        view.findViewById<Button>(R.id.reply_button).setOnClickListener {

            val bundle = Bundle()
            bundle.putString("description", description)
            bundle.putString("post", postName)
            bundle.putString("category", categoryName)
            bundle.putString("forum", forumName)
            view.findNavController().navigate(R.id.action_forumPostFragment_to_replyFragment, bundle)
        }

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
                .inflate(R.layout.card_view_reply, parent, false)
            return AddressViewHolder(v)
        }
        override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {

            holder.view.findViewById<TextView>(R.id.reply_user_text).text=locations[position].name
            holder.view.findViewById<TextView>(R.id.reply_text).text=locations[position].short_description

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