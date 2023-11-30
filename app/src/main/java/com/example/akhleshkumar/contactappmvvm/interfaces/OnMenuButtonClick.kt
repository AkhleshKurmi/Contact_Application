package com.example.akhleshkumar.contactappmvvm.interfaces

import android.view.View
import com.example.akhleshkumar.contactappmvvm.modelclass.ContactData

interface OnMenuButtonClick {
    fun onMenuButtonClick(contact:ContactData, view :View)
}