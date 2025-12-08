package com.example.parkingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SmsAdapter(
    private val smsClickInterface: SmsClickInterface,
    private val smsDeleteInterface: SmsDeleteInterface
) : RecyclerView.Adapter<SmsAdapter.SmsViewHolder>() {

    private val allSms = ArrayList<Sms>()

    inner class SmsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTV: TextView = itemView.findViewById(R.id.tvSmsTitle)
        val numberTV: TextView = itemView.findViewById(R.id.tvSmsNumber)
        val cityTV: TextView = itemView.findViewById(R.id.tvSmsCity)
        val deleteIV: ImageView = itemView.findViewById(R.id.ivDeleteSms)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.sms_item, parent, false)
        return SmsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SmsViewHolder, position: Int) {
        val sms = allSms[position]
        holder.titleTV.text = sms.smsTitle
        holder.numberTV.text = sms.smsNumber
        holder.cityTV.text = sms.smsCity

        holder.deleteIV.setOnClickListener {
            smsDeleteInterface.onDeleteClick(sms)
        }

        holder.itemView.setOnClickListener {
            smsClickInterface.onSmsClick(sms)
        }
    }

    override fun getItemCount(): Int = allSms.size

    fun updateList(newList: List<Sms>) {
        allSms.clear()
        allSms.addAll(newList)
        notifyDataSetChanged()
    }
}

interface SmsClickInterface {
    fun onSmsClick(sms: Sms)
}

interface SmsDeleteInterface {
    fun onDeleteClick(sms: Sms)
}
