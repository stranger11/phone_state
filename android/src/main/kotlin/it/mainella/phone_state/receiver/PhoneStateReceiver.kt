package it.mainella.phone_state.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telecom.Call
import android.telephony.TelephonyManager
import android.util.Log
import androidx.annotation.RequiresApi
import it.mainella.phone_state.utils.PhoneStateStatus

open class PhoneStateReceiver : BroadcastReceiver() {
    var status: PhoneStateStatus = PhoneStateStatus.NOTHING;
    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            status = when (intent?.getStringExtra(TelephonyManager.EXTRA_STATE)) {
                TelephonyManager.EXTRA_STATE_RINGING -> PhoneStateStatus.CALL_INCOMING
                TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                    @RequiresApi(Build.VERSION_CODES.M)
                    object : Call.Callback() {
                        override fun onStateChanged(call: Call?, state: Int) {
                            super.onStateChanged(call, state)
                            if(state == Call.STATE_DISCONNECTED) {
                                status = PhoneStateStatus.CALL_ENDED
                            }
                        }
                    }
                    PhoneStateStatus.CALL_STARTED
                }

                TelephonyManager.EXTRA_STATE_IDLE -> PhoneStateStatus.CALL_ENDED
                else -> PhoneStateStatus.NOTHING
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}