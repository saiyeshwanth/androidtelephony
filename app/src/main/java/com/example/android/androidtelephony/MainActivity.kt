package com.example.android.androidtelephony

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.telephony.SmsManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var sms_permission: Boolean = false
    var call_permission: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var sms_status = ContextCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.SEND_SMS)
        var call_status = ContextCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.CALL_PHONE)

        if (sms_status == PackageManager.PERMISSION_GRANTED) {
            sms_permission = true
        }
        if (call_status == PackageManager.PERMISSION_GRANTED) {
            call_permission = true

            if (sms_status != PackageManager.PERMISSION_GRANTED || call_status != PackageManager.PERMISSION_GRANTED) {
                reqPermissions()
            } else if (sms_status != PackageManager.PERMISSION_GRANTED) {
                reqSmsPermission()
            } else if (call_status != PackageManager.PERMISSION_GRANTED) {
                reqCallPermissions()
            }
        }
        bt1.setOnClickListener {
            if (sms_permission) {
                val SManager: SmsManager = SmsManager.getDefault()
                SManager.sendTextMessage(et1.text.toString(), null, et2.text.toString(), null, null)
            } else {
                reqSmsPermission()
            }
        }

        bt2.setOnClickListener {
            if (call_permission){
                val i=Intent()
                i.action=Intent.ACTION_CALL
                i.data= Uri.parse("tel:${et1.text.toString()}")
                startActivity(i)
            }else
                reqCallPermissions()
        }

    }//oncreate

    fun reqPermissions() {
        ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf(android.Manifest.permission.SEND_SMS, android.Manifest.permission.CALL_PHONE),
            11
        )
    }

    fun reqSmsPermission() {
        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.SEND_SMS), 12)
    }

    fun reqCallPermissions() {
        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.CALL_PHONE), 13)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            11-> {
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    sms_permission=true
                }
                if (grantResults[1]==PackageManager.PERMISSION_GRANTED)
                {
                    call_permission=true
                }
            }
            12->
            {
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    sms_permission=true
                }
            }
            13-> if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                call_permission=true
            }
        }
    }
}
