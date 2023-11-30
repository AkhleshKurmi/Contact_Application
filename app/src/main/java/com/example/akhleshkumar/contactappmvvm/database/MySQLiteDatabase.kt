package com.example.akhleshkumar.contactappmvvm.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.akhleshkumar.contactappmvvm.modelclass.ContactData

class MySQLiteDatabase (val context: Context) {
    var DB_NAME = "My_Contact"
    var DB_VIRSION = 1
    var TABLE_NAME = "Contacts"
    var ID = "Id"
    var FNAME = "fName"

    var MNAME = "mName"
    var LNAME = "lName"
    var MOBILE = "Mobile"
    var EMAIL = "Email"
    var IMAGE = "Image"

    var CREATE_TABLE = "CREATE TABLE $TABLE_NAME($ID INTEGER PRIMARY KEY AUTOINCREMENT, $FNAME TEXT, $MNAME TEXT, $LNAME TEXT, $MOBILE TEXT, $EMAIL TEXT, $IMAGE)"

    var myopenHelper = MyopenHelper(context)
    var sqliteDataBase = myopenHelper.writableDatabase


    fun saveContact(fname:String,
                    mname:String,
                    lname : String,
                    mobile:String,
                    email:String
    ){
        val values = ContentValues()
        values.put(FNAME,fname)
        values.put(MNAME,mname)
        values.put(LNAME,lname)
        values.put(MOBILE,mobile)
        values.put(EMAIL,email)
        val rowId = sqliteDataBase.insert(TABLE_NAME,null,values)
        if (rowId > 0){
            Toast.makeText(context, "Saved Successfully", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show()
        }
    }

    fun fetchData(): ArrayList<ContactData>{
        val allData = ArrayList<ContactData>()

        val cursor = sqliteDataBase.rawQuery("SELECT * FROM $TABLE_NAME",null,null)

        if (cursor.count > 0){
            cursor.moveToFirst()
            do {
                val id = cursor.getInt(0)
                val fname = cursor.getString(1)
                val mname = cursor.getString(2)
                val lname = cursor.getString(3)
                val mobile = cursor.getString(4)
                val email  = cursor.getString(5)

                allData.add(ContactData(id,fname,mname,lname,mobile,email))

            }while (cursor.moveToNext())

        }
        else {
            Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
        }
        return allData

    }

    fun UpdateData(Id: Int,
                   fname:String,
                   mname :String,
                   lname:String,
                   mobile:String,
                   email:String
    ){
        val values = ContentValues()
        values.put(FNAME,fname)
        values.put(MNAME,mname)
        values.put(LNAME,lname)
        values.put(MOBILE,mobile)
        values.put(EMAIL,email)
        val rowId = sqliteDataBase.update(TABLE_NAME,values,"$ID = $Id",null)
        if (rowId > 0){
            Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteSingleRow(rowId : Int){
        val deleteRow = sqliteDataBase.delete(TABLE_NAME,"$ID = $rowId",null)
        if (deleteRow > 0 ){
            Toast.makeText(context, "$deleteRow Deleted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show()
        }
    }


    inner class MyopenHelper(context: Context) : SQLiteOpenHelper(context,DB_NAME,null,DB_VIRSION) {
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(CREATE_TABLE)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            TODO("Not yet implemented")
        }
    }
}