package com.example.akhleshkumar.contactappmvvm.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.akhleshkumar.contactappmvvm.databinding.CustomItemViewBinding
import com.example.akhleshkumar.contactappmvvm.interfaces.OnCallButtonClick
import com.example.akhleshkumar.contactappmvvm.interfaces.OnMenuButtonClick
import com.example.akhleshkumar.contactappmvvm.modelclass.ContactData
import kotlin.random.Random

class MyRecyclerViewAdapter (var list: List<ContactData>, var onOptionButtonClick: OnMenuButtonClick,
                             var onCallButtonClick: OnCallButtonClick) : RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>(){

    inner class MyViewHolder(val binding: CustomItemViewBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            CustomItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return list.size

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val person = list[position]
        holder.binding.name.text = "${person.fname} ${person.mname} ${person.lname}"
        val fnamefl = person.fname.get(0)


        val rnd = Random
        val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        holder.binding.contactImage.circleBackgroundColor = color
        holder.binding.fletters.setBackgroundColor(color)
        if (person.lname.isNotEmpty()) {
            val lnamefl = person.lname.get(0)
            holder.binding.fletters.text = "$fnamefl$lnamefl"
        }
        holder.binding.mobile.text = "+91 ${person.mobile}"

        holder.binding.menuDot.setOnClickListener { view ->
            onOptionButtonClick.onMenuButtonClick(
                person,
                view!!
            )
        }



        holder.binding.btnCall.setOnClickListener {
            onCallButtonClick.onCallButtonClick(person.mobile)
        }

    }

}