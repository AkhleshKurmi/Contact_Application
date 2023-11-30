package com.example.akhleshkumar.contactappmvvm.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.akhleshkumar.contactappmvvm.viewmodel.MyContactViewModel

class MyViewModelFactory(var context: Context) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyContactViewModel::class.java)){
            return MyContactViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown Class")
    }
}