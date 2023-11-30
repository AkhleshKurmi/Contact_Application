package com.example.akhleshkumar.contactappmvvm.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.akhleshkumar.contactappmvvm.modelclass.ContactData
import com.example.akhleshkumar.contactappmvvm.repository.MyContactRepository

class MyContactViewModel(val context: Context) : ViewModel(){
    var repository =MyContactRepository(context)

    var dialogTitle = MutableLiveData<String>()
    var btnSaveUpdateText = MutableLiveData<String>()
    var btnSaveUpdate = MutableLiveData<Boolean>()
    var fName = MutableLiveData<String>()
    var mName = MutableLiveData<String>()
    var lName = MutableLiveData<String>()
    var mobile = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var dialog = MutableLiveData<Boolean>()

    var id = 0
    init {
        dialogTitle.value = "Save"
        fName.value = ""
        mName.value = ""
        lName.value = ""
        mobile.value = ""
        email.value = ""
        btnSaveUpdateText.value = "Save"
        btnSaveUpdate.value = false
    }

    fun createDialog(){
        dialogTitle.value = "Save"
        btnSaveUpdateText.value = "Save"
        dialog.value = true
    }

    fun cancelDialog(){
        dialog.value = false
    }


    fun createAndUpdateContact(){
        if (fName.value.isNullOrEmpty()|| lName.value.isNullOrEmpty()
            || mobile.value.isNullOrEmpty()){
            Toast.makeText(context, "Fill all mandatory field", Toast.LENGTH_SHORT).show()
        }else {
            if (btnSaveUpdate.value!!) {
                repository.updateContact(
                    id,
                    fName.value!!,
                    mName.value!!,
                    lName.value!!,
                    mobile.value!!,
                    email.value!!
                )
                fName.value = ""
                mName.value = ""
                lName.value = ""
                mobile.value = ""
                email.value = ""
                dialog.value = false
            } else {
                repository.createContact(
                    fName.value!!,
                    mName.value!!,
                    lName.value!!,
                    mobile.value!!,
                    email.value!!
                )
                fName.value = ""
                mName.value = ""
                lName.value = ""
                mobile.value = ""
                email.value = ""
                dialog.value = false
            }
        }
    }

    fun fetchContact() : LiveData<ArrayList<ContactData>>{
        return repository.fetchContact()
    }

    fun deleteContact(rowId:Int){
        repository.deleteAData(rowId)
    }

    fun updateContact(contactData: ContactData){
        id = contactData.id
        fName.value = contactData.fname
        mName.value = contactData.mname
        lName.value = contactData.lname
        mobile.value = contactData.mobile
        email.value = contactData.Email
        dialogTitle.value = "Update"
        btnSaveUpdateText.value = "Update"
        btnSaveUpdate.value = true
        dialog.value = true
    }

}