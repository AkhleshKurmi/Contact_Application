package com.example.akhleshkumar.contactappmvvm.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.akhleshkumar.contactappmvvm.database.MySQLiteDatabase
import com.example.akhleshkumar.contactappmvvm.modelclass.ContactData

class MyContactRepository(var context: Context) {

    var mySQLiteDatabase = MySQLiteDatabase(context)

    var contactList = MutableLiveData<ArrayList<ContactData>>()

    fun createContact(fname:String,
                      mname:String,
                      lname : String,
                      mobile:String,
                      email:String){
        mySQLiteDatabase.saveContact(fname, mname, lname, mobile, email)
    }
    fun fetchContact() :MutableLiveData<ArrayList<ContactData>>{
         contactList.value = mySQLiteDatabase.fetchData()

        return contactList
    }
    fun deleteAData(rowId:Int){
        mySQLiteDatabase.deleteSingleRow(rowId)
    }

    fun updateContact(Id: Int,
                      fname:String,
                      mname :String,
                      lname:String,
                      mobile:String,
                      email:String){
        mySQLiteDatabase.UpdateData(Id, fname, mname, lname, mobile, email)
    }


}