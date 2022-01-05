package com.example.testphonr

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.auth.api.credentials.*
class MainActivity : AppCompatActivity() {

    lateinit var open_btn: EditText
    lateinit var setText: TextView

    lateinit var btn: Button
   // private val CREDENTIAL_PICKER_REQUEST = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn = findViewById(R.id.button)
        setText = findViewById(R.id.textView)


        // set on click listener to button
        // to open the phone selector dialog
        btn.setOnClickListener {
            getPhoneNumber()
        }
    }
    private fun getPhoneNumber(){
        val request=HintRequest.Builder()
            .setPhoneNumberIdentifierSupported(true)
            .build()
        val options=CredentialsOptions.Builder()
            .forceEnableSaveDialog().build()
        val crend_client=Credentials.getClient(applicationContext,options)
        val intent=crend_client.getHintPickerIntent(request)
        try {
            startIntentSenderForResult(
                intent.intentSender,
                2002, null, 0, 0, 0, Bundle()
            )
        } catch (e: IntentSender.SendIntentException) {
            e.printStackTrace()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==2002 && resultCode== RESULT_OK){
            val cred: Credential?=data?.getParcelableExtra(Credential.EXTRA_KEY)
            cred.apply {
                if (cred != null) {
                    setText.text=cred.id
                }
            }
        }
        else  {
            Toast.makeText(this, "No phone numbers found", Toast.LENGTH_LONG).show();
        }
    }
}