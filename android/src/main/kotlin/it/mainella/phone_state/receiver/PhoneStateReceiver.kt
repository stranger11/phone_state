package it.mainella.phone_state.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.TELEPHONY_SERVICE
import android.content.Intent
import android.os.Build
import android.telecom.Call
import android.telecom.DisconnectCause
import android.telecom.TelecomManager
import android.telephony.PhoneStateListener
import android.telephony.PhoneStateListener.LISTEN_CALL_STATE
import android.telephony.TelephonyManager
import android.util.Log
import androidx.annotation.RequiresApi
import it.mainella.phone_state.utils.PhoneStateStatus

open class PhoneStateReceiver : BroadcastReceiver() {
    var status: PhoneStateStatus = PhoneStateStatus.NOTHING;
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onReceive(context: Context?, intent: Intent?) {
        try {

            val telephonyManager = context?.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
            val monitor = StateMonitor()
            telephonyManager.listen(monitor, LISTEN_CALL_STATE)

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

    private inner class StateMonitor : PhoneStateListener() {

        override fun onCallStateChanged(state: Int, phoneNumber: String?) {
            when (state) {
                TelephonyManager.CALL_STATE_IDLE -> PhoneStateStatus.CALL_ENDED
                TelephonyManager.CALL_STATE_OFFHOOK -> PhoneStateStatus.CALL_STARTED
                TelephonyManager.CALL_STATE_RINGING -> PhoneStateStatus.CALL_INCOMING
            }
        }
    }
}