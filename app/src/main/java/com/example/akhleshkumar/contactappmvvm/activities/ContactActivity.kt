package com.example.akhleshkumar.contactappmvvm.activities

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.akhleshkumar.contactappmvvm.R
import com.example.akhleshkumar.contactappmvvm.adapter.MyRecyclerViewAdapter
import com.example.akhleshkumar.contactappmvvm.databinding.ActivityContactBinding
import com.example.akhleshkumar.contactappmvvm.databinding.ContactCreateDialogBinding
import com.example.akhleshkumar.contactappmvvm.factory.MyViewModelFactory
import com.example.akhleshkumar.contactappmvvm.interfaces.OnCallButtonClick
import com.example.akhleshkumar.contactappmvvm.interfaces.OnMenuButtonClick
import com.example.akhleshkumar.contactappmvvm.modelclass.ContactData
import com.example.akhleshkumar.contactappmvvm.viewmodel.MyContactViewModel

class ContactActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {
    lateinit var binding : ActivityContactBinding
    lateinit var myContactViewModel: MyContactViewModel
    lateinit var myRecyclerViewAdapter: MyRecyclerViewAdapter
    lateinit var contactData : ContactData
    var rowId = 0
    var mobile = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_contact)
        myContactViewModel = ViewModelProvider(this, MyViewModelFactory(this))[MyContactViewModel::class.java]

        binding.viewModel = myContactViewModel
        binding.lifecycleOwner = this

        var dialogBinding = ContactCreateDialogBinding.inflate(layoutInflater)
        var dialogCreate = Dialog(this)
        dialogCreate.setContentView(dialogBinding.root)
        dialogCreate.setCancelable(false)
        var layoutParms = WindowManager.LayoutParams()
        layoutParms.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParms.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialogCreate.window!!.attributes = layoutParms
        dialogBinding.viewModel = myContactViewModel
        dialogBinding.lifecycleOwner = this
        myContactViewModel.dialog.observe(this){
            if (it){
                dialogCreate.show()
            }else{
                dialogCreate.dismiss()
                setData()
            }
        }


     setData()

    }



    fun setData(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        myContactViewModel.fetchContact().observe(this){

        myRecyclerViewAdapter = MyRecyclerViewAdapter(it,object : OnMenuButtonClick{
            override fun onMenuButtonClick(contact: ContactData,view: View) {
                rowId = contact.id
                contactData = contact
             popUpMenu(view)
            }
        }, object : OnCallButtonClick{
            override fun onCallButtonClick(number: String) {
                mobile = number
            checkPermission(number)
            }

        })
            binding.recyclerView.adapter = myRecyclerViewAdapter
            myRecyclerViewAdapter.notifyDataSetChanged()
        }

    }

    fun popUpMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.context_menu, popupMenu.menu)
        popupMenu.show()
        popupMenu.setOnMenuItemClickListener(this)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.delete -> {

                alertDelete()

            }

            R.id.Update -> {

                UpdateDialog()

            }
        }
        return  false
    }

    fun alertDelete(){
        var deleteDialog = AlertDialog.Builder(this)
        deleteDialog.setMessage("Are you sure to Delete")
        deleteDialog.setTitle("Delete")
        deleteDialog.setCancelable(false)
        deleteDialog.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                myContactViewModel.deleteContact(rowId)
                setData()
                dialog!!.dismiss()
            }

        })
        deleteDialog.setNegativeButton("No", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog?.dismiss()
            }

        })
        var alert = deleteDialog.create()
        alert.show()
    }

    fun UpdateDialog(){
        myContactViewModel.updateContact(contactData)
        setData()
    }

    fun checkPermission(mobile :String){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),1001)
        }else{
            callIntent(mobile)
        }
    }
    fun callIntent(mobile:String){
        var intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$mobile")
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1001){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                callIntent(mobile)
            }else{
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),1001)
            }
        }
    }
}