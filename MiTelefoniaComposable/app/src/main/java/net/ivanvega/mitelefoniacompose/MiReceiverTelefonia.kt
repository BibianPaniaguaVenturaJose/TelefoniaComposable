package net.ivanvega.mitelefoniacompose

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log
import android.telephony.TelephonyManager
import android.widget.Toast


class MiReceiverTelefonia: BroadcastReceiver()
{
    companion object {
        private var isRinging: Boolean = false
    }

    object ScreenViewModelSingleton {
        private val viewModel = ScreenViewModel()
        fun getInstance(): ScreenViewModel {
            return viewModel
        }
    }


    override fun onReceive(p0: Context?, intent: Intent?) {
        val action: String? = intent?.getAction()
        //Uri uri = intent.getData();
        action?.let { Log.d("MiBroadcast", it) }
         var strMensaje: String = ""
        //Uri uri = intent.getData();
        if (action == Intent.ACTION_BOOT_COMPLETED) {
        }
        if (Intent.ACTION_INPUT_METHOD_CHANGED === action) {
        }
        if (action == Intent.ACTION_BOOT_COMPLETED) {
        }
        if (action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val bndSMS: Bundle? = intent?.getExtras()
            val pdus = bndSMS?.get("pdus") as Array<Any>?
            val smms: Array<SmsMessage?> = arrayOfNulls<SmsMessage>(pdus!!.size)
            for (i in smms.indices) {
                smms[i] = SmsMessage.createFromPdu(pdus!![i] as ByteArray)
                strMensaje +="${"Mensaje: " + smms[i]?.getOriginatingAddress()}\n" +
                        "${smms[i]?.getMessageBody().toString()}"
            }
            Log.d("MiBroadcast", strMensaje)
        } else if (action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val extras = intent?.extras
            if (extras != null) {
                val state = extras.getString(TelephonyManager.EXTRA_STATE)

                if (state == TelephonyManager.EXTRA_STATE_RINGING) {
                    isRinging = true
                    Log.d("Llamada", "Sonando $isRinging")
                } else if (state == TelephonyManager.EXTRA_STATE_OFFHOOK) {
                    isRinging = false
                    Log.d("Llamada", "Tomo llamada $isRinging")
                } else if (state == TelephonyManager.EXTRA_STATE_IDLE && isRinging) {

                    Log.d("Llamada", "Dejo de sonar $isRinging")
                    val viewModel = ScreenViewModelSingleton.getInstance()
                    viewModel.sendSMS()
                    isRinging = false

                }
            }
        }


        val sb = StringBuilder()
        sb.append("Action: " + intent?.getAction() + "\n")
        sb.append("URI: " + intent?.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n")
        val log = sb.toString()
        Log.d("MiBroadcast", log)

    }
}