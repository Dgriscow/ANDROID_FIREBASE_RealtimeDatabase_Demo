package com.example.android_firebase_realtimedatabase_demo

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.util.Log
import android.view.KeyEvent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.widget.addTextChangedListener

import com.example.android_firebase_realtimedatabase_demo.databinding.FragmentPersonListItemBinding
import com.google.firebase.Firebase
import com.google.firebase.database.database

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyPersonRecyclerViewAdapter:RecyclerView.Adapter<MyPersonRecyclerViewAdapter.ListRowBinder>() {

    //List for the Adapter to use
    private var peopleList = emptyList<Person>()

    //use binding function


    //Change the view holder function to return a list row binder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRowBinder {
        //Create a binding for the cell items
        val itemBinder = FragmentPersonListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ListRowBinder(itemBinder)
//        return ViewHolder(
//            FragmentPersonListItemBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//        )

    }



    override fun onBindViewHolder(holder: ListRowBinder, position: Int) {
        Log.e("On Viewer Binding", "Binding")
        val personitem = peopleList[position]

        val personNameHolder = holder.itemBinding.NameTxtBox


        // add the functionality for users to be able to press on the text edits and type and press enter on them.
        //(Doing that will update it in firebase)
        val personBioEditText = holder.itemBinding.BioTxtBox

        personBioEditText.addTextChangedListener {
            Log.e("CTA", "done ${personBioEditText.text}")

            if (personNameHolder.text.toString() == personitem.firstName){

                //update the person objects BIO to the typed txt
                personitem.bio = personBioEditText.text.toString()

                Log.e("new User Bio", "Updating NEW USER BIO ")

                updateBio(personitem)
            }

        }

//        personBioEditText.setOnEditorActionListener(object : TextView.OnEditorActionListener {
//            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
//                //Check if the editor (keyboard) is done and the user is done tyfsdfkljdsfklfsdfdsfping,
//                //When they are send a function call to update the new values to real time
//                Log.e("CTA", "done Typing")
//                if (actionId == EditorInfo.IME_ACTION_DONE){
//
//                    Log.e("User Act", "done Typing")
//
//                    //Set New Info To Firebase for entering New User
//                    //change the value for the name entry and set it to firebase
//                    //First just double check if v is not null as well
//                    if (v != null) {
//                        personitem.bio = v.text.toString()
//                        //Once its NOT, send it off and rewrite the user to Firebase
//                        updateBio(personitem)
//                        Log.e("new User Bio", "${personitem.bio}")
//
//
//                    }
//
//
//                    //After typing, also close the keyboard
//                    holder.itemView.context.hideKeyboard(holder.itemView)
//
//                    return true
//                }
//
//                return false//No Event to handle
//            }
//
//
//        })

        holder.bind(personitem)

    }

    override fun getItemCount(): Int = peopleList.size

//    inner class ViewHolder(binding: FragmentPersonListItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        val idView: TextView = binding.itemIndexNum
//        val contentView: TextView = binding.txtContent
//
//        override fun toString(): String {
//            return super.toString() + " '" + contentView.text + "'"
//        }
//    }


    class ListRowBinder(val itemBinding:FragmentPersonListItemBinding):RecyclerView.ViewHolder(itemBinding.root){

        //This function takes a userItem, and binds values from the views/fragments to the user item
        fun bind(userItem:Person){

            itemBinding.NameTxtBox.text = userItem.firstName
            itemBinding.BioTxtBox.setText(userItem.bio)
//            itemBinding.txtContent.text = userItem.bio

            Log.e("Jujubeat", "binding" )


        }

    }

    fun setPeopleArray(usersToSet: List<Person>){
        Log.e("setting new people ","$usersToSet")

        this.peopleList = usersToSet

        notifyDataSetChanged()
    }



    //This is a seperate Function to handle Adding to and From Firebase
    private fun updateBio(newPerson:Person) {
        val database = Firebase.database

        //make a reference to a collection of items "Users"
        //And add the new User TO the collection of "Users"
        Log.e("JUMANJ", "${newPerson.bio.toString()}")


        database.reference.child("People").child(newPerson.firstName.toString()).updateChildren(
            mapOf("bio" to newPerson.bio.toString())
        )
    }

}



