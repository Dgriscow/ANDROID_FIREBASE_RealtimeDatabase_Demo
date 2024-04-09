package com.example.android_firebase_realtimedatabase_demo

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.android_firebase_realtimedatabase_demo.databinding.FragmentAddUserEntryBinding
import com.google.firebase.Firebase
import com.google.firebase.database.database
import kotlin.math.log


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class addUserEntryFragment : Fragment() {

    private lateinit var addFragBinding: FragmentAddUserEntryBinding





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        addFragBinding = FragmentAddUserEntryBinding.inflate(inflater, container, false)

        val addViewRoot = addFragBinding.root


        //Add a on click for the add button
        addFragBinding.addUserButton.setOnClickListener{

            //Call The Function Handler
            addUserToRealtimeData()

        }

        // Inflate the layout for this fragment
        return addViewRoot
    }

    //This functions purpose is to put the new information for a entered user into firebase
    private fun addUserToRealtimeData(){

        val newUserFName:String = addFragBinding.newNameTxtEntry.text.toString()

        val newUserBio:String = addFragBinding.newBioTxtEntry.text.toString()

        if (inputCheck(newUserFName, newUserBio)){
            //Test was good, these items have values given


            //Push Values to realtime

            //first create new User Object Instance
            val newPersonToAdd:Person = Person(newUserFName, newUserBio)

            //Now Move onto the code to ADD to FB

            //create a reference to the database

            //Call function made to handle FB add Call
            writeNewPerson(newPersonToAdd)




            //Make a toast message after uploading
            Toast.makeText(requireContext(), "Successfully Added!", Toast.LENGTH_LONG).show()

            //Go back to the main screen
            findNavController().navigate(R.id.action_addUserEntryFragment_to_personFragment)

        }else{
            //Items do not have all entered data
            Toast.makeText(requireContext(), "Please fill out all the fields.", Toast.LENGTH_LONG).show()

        }




    }
    //This is a seperate Function to handle Adding to and From Firebase
    fun writeNewPerson(newPerson:Person) {
        val database = Firebase.database

        //make a reference to a collection of items "Users"
        //And add the new User TO the collection of "Users"
        Log.e("JUMANJ", "${newPerson.firstName.toString()}")


        database.reference.child("People").child(newPerson.firstName.toString()).setValue(newPerson)
    }

    //Input Check, check if the user entered all the data
    private fun inputCheck(name:String, bio:String):Boolean{
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(bio))
        //Check if the text in both name and last name and AGE is all present
    }


}