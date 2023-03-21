package it.mainella.phone_state.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.TELECOM_SERVICE
import android.content.Context.TELEPHONY_SERVICE
import android.content.Intent
import android.os.Build
import android.telecom.Call
import android.telecom.DisconnectCause
import android.telecom.TelecomManager
import android.telephony.PhoneStateListener
import android.telephony.PhoneStateListener.LISTEN_CALL_STATE
import android.telephony.TelephonyCallback
import android.telephony.TelephonyManager
import android.util.Log
import androidx.annotation.RequiresApi
import it.mainella.phone_state.utils.PhoneStateStatus

open class PhoneStateReceiver : BroadcastReceiver() {
    var status: PhoneStateStatus = PhoneStateStatus.NOTHING;

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onReceive(context: Context?, intent: Intent?) {
        try {


            val manager = context?.getSystemService(TELEPHONY_SERVICE) as TelephonyManager


            val callback = MyPhoneStateListener()
            manager.registerTelephonyCallback({it.run()},callback )
          //  manager.isInCall

//            TelephonyManager.EXTRA_FOREGROUND_CALL_STATE
//
//            status = when (intent?.getStringExtra(TelephonyManager.EXTRA_STATE)) {
//                TelephonyManager.EXTRA_STATE_RINGING -> PhoneStateStatus.CALL_INCOMING
//                TelephonyManager.EXTRA_STATE_OFFHOOK -> {
//                    object : Call.Callback() {
//                        override fun onStateChanged(call: Call?, state: Int) {
//                            super.onStateChanged(call, state)
//                            if(state == Call.STATE_DISCONNECTED) {
//                                status = PhoneStateStatus.CALL_ENDED
//                            }
//                        }
//                    }
//                    PhoneStateStatus.CALL_STARTED
//                }
//
//                TelephonyManager.EXTRA_STATE_IDLE -> PhoneStateStatus.CALL_ENDED
//                else -> PhoneStateStatus.NOTHING
//            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    @RequiresApi(Build.VERSION_CODES.S)
    inner class MyPhoneStateListener : TelephonyCallback(), TelephonyCallback.CallStateListener {
        override fun onCallStateChanged(state: Int) {
            when (state) {
                TelephonyManager.CALL_STATE_RINGING -> {

                }

                TelephonyManager.CALL_STATE_OFFHOOK -> {
                    status = PhoneStateStatus.CALL_STARTED
                }
                TelephonyManager.CALL_STATE_IDLE -> {

                }
            }
        }
    }


}