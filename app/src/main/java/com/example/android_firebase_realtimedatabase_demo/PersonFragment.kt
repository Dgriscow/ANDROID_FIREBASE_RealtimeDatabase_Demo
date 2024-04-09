package com.example.android_firebase_realtimedatabase_demo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_firebase_realtimedatabase_demo.databinding.FragmentPersonListBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import java.util.ArrayList


/**
 * A fragment representing a list of Items.
 */
class PersonFragment : Fragment() {

    private lateinit var  listFragBinding: FragmentPersonListBinding

    private var retrievedPeople = ArrayList<Person>()


    private var columnCount = 1

    private val peopleAdapter = MyPersonRecyclerViewAdapter()

    var initLaunch:Boolean = true



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        listFragBinding = FragmentPersonListBinding.inflate(inflater, container, false)


        //create a action for the add user popup
        listFragBinding.AddPeopleButton.setOnClickListener{
            findNavController().navigate(R.id.action_personFragment_to_addUserEntryFragment)
        }


        //view variable for the root view
        val view = listFragBinding.root

        //Recycler view from view
        val peopleRecyclerView = listFragBinding.PeopleList




        //adapt the recycler view to a adapter
        peopleRecyclerView.adapter = peopleAdapter

        //Set the layout manager to require the context for this fragment
        peopleRecyclerView.layoutManager = LinearLayoutManager(requireContext())



        //SET THE DATA to the adapter

        getAllPeopleConstantly()


        peopleAdapter.setPeopleArray(retrievedPeople)





        // Set the adapter
//        if (view is RecyclerView) {
//            with(view) {
//                layoutManager = when {
//                    columnCount <= 1 -> LinearLayoutManager(context)
//                    else -> GridLayoutManager(context, columnCount)
//                }
//                adapter = MyPersonRecyclerViewAdapter(PlaceholderContent.ITEMS)
//            }
//        }
        return view
    }

//    companion object {
//
//        // TODO: Customize parameter argument names
//        const val ARG_COLUMN_COUNT = "column-count"
//
//        // TODO: Customize parameter initialization
//        @JvmStatic
//        fun newInstance(columnCount: Int) =
//            PersonFragment().apply {
//                arguments = Bundle().apply {
//                    putInt(ARG_COLUMN_COUNT, columnCount)
//                    Log.e("New Instance", "New innnn")
//                }
//            }
//    }


    //Function to get all the users from firebase
    private fun getAllPeopleConstantly(){
        Log.e("Jungle", "Starting Query!")

        //Create Database reference
        val database = FirebaseDatabase.getInstance()

        val databaseRef = database.getReference("People")

        //Create a reference to the people section
//        val peopleRef = database.reference.child("People")

        databaseRef.addChildEventListener(object : ChildEventListener {


//            override fun onDataChange(snapshot: DataSnapshot) {
//                //Retrieve the data
//                val childSnapsot = snapshot.child("People").children
//                Log.e("Jungle", "jungle data grabbed")
//                Log.e("Jungle", "$childSnapsot")
//                val snapshotData = childSnapsot
//
//                //For each data in the child piece, log it out for now
////                childSnapsot
////                childSnapsot.forEach {
////                    val dataRetr = it.value
////
////                    Log.e("Jungle", "${dataRetr}")
////
////                }
//
//
//
//            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.e("Jungle", "Snapshot ADD")

                val personFromdataFromSnapShot = snapshot.getValue<Person>()

                //Assign person data to new person Object


//                Log.e("Jungle ADD", "$previousChildName")

                //Check if the converted user is not null,
                if (personFromdataFromSnapShot != null) {
                    //APPEND THE NEW ITEM if its not null
                    Log.e("Jungle ADDING!", "${personFromdataFromSnapShot.bio.toString()}")

                    //Add a new person to the retrieved People Array, and then do the following.
                    retrievedPeople.add(personFromdataFromSnapShot)

                    //every time a new person is ADDED to the array, recall Set people array
                    //This causes it to set the adapter to the newest retrieved People, as well as
                    //Call the Notify dataset change
                    peopleAdapter.setPeopleArray(retrievedPeople)

                }


            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                Log.e("Jungle", "Snapshot ch")

                if (initLaunch) {

                    Log.e("Jungle", "Snapshot Change")
                    //on change and Add can do the same thing
                    val personFromdataFromSnapShot = snapshot.getValue<Person>()

                    //Assign person data to new person Object


//                Log.e("Jungle ADD", "$previousChildName")

                    //Check if the converted user is not null,
                    if (personFromdataFromSnapShot != null) {
                        //APPEND THE NEW ITEM if its not null
                        Log.e("Jungle ADDING!", "${personFromdataFromSnapShot.bio.toString()}")

                        //Add a new person to the retrieved People Array, and then do the following.
                        retrievedPeople.add(personFromdataFromSnapShot)

                        //every time a new person is ADDED to the array, recall Set people array
                        //This causes it to set the adapter to the newest retrieved People, as well as
                        //Call the Notify dataset change
                        peopleAdapter.setPeopleArray(retrievedPeople)




                    }

                    initLaunch = false



                }




            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                Log.e("Jungle", "Snapshot REM")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                Log.e("Jungle", "Snapshot MOV")

            }

            override fun onCancelled(error: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("Failed", "loadPost:onCancelled", error.toException())
            }

        })

//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                val value = dataSnapshot.getValue(String::class.java)
//                Log.d(TAG, "Value is: $value")
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException())
//            }
//        })


//        val peopleListener = object : ValueEventListener {
//            //On the Data Change, Do the Following
//            override fun onDataChange(snapshot: DataSnapshot) {
//                //Retrieve the data
//                val data = snapshot.child("People").children
//                Log.e("Jungle", "jungle data grabbed")
//
//                //Log the value
//                Log.e("Jungle", "$data, jungle data")
////                Log.e("Jungle", "${snapshot.value}")
//
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Getting Post failed, log a message
//                Log.w("Failed", "loadPost:onCancelled", error.toException())
//            }
//
//
//        }





        }


}






