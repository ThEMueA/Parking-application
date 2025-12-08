package com.example.parkingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SMSActivity :AppCompatActivity(), SmsClickInterface, SmsDeleteInterface {

    private lateinit var smsViewModel: CSViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SmsAdapter
    private lateinit var btnNew: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sms)

        btnNew = findViewById(R.id.btnNewSms)

        btnNew.setOnClickListener {
            val intent = Intent(this, AddEditSmsActivity::class.java)
            startActivity(intent)
        }



        recyclerView = findViewById(R.id.recyclerSms)
        adapter = SmsAdapter(this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        smsViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[CSViewModel::class.java]

        smsViewModel.allSms.observe(this) { list ->
            adapter.updateList(list)
        }
    }

    override fun onSmsClick(sms: Sms) {
        val intent = Intent(this, AddEditSmsActivity::class.java)
        intent.putExtra("smsId", sms.id)
        intent.putExtra("smsTitle", sms.smsTitle)
        intent.putExtra("smsNumber", sms.smsNumber)
        intent.putExtra("City", sms.smsCity)
        startActivity(intent)
    }

    override fun onDeleteClick(sms: Sms){
        smsViewModel.deleteSms(sms)
    }
}
