package net.ivanvega.mitelefoniacompose

import android.telephony.SmsManager
import androidx.lifecycle.ViewModel

class ScreenViewModel: ViewModel() {

    fun sendSMS(){
        val smsManage = SmsManager.getDefault()
        smsManage.sendTextMessage("431162267",
            null,
            "Te llamo depues, de momento no puedo",null,null
            )

    }

}