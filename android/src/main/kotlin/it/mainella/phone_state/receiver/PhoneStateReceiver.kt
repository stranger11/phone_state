package it.mainella.phone_state.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.TELEPHONY_SERVICE
import android.content.Intent
import android.telephony.TelephonyManager
import it.mainella.phone_state.utils.PhoneStateStatus


var status: PhoneStateStatus = PhoneStateStatus.NOTHING;


open class PhoneStateReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            val telephonyManager = context?.getSystemService(TELEPHONY_SERVICE) as TelephonyManager


            status = when (intent?.getStringExtra(TelephonyManager.EXTRA_STATE)) {

                TelephonyManager.EXTRA_STATE_RINGING -> PhoneStateStatus.CALL_INCOMING
                TelephonyManager.EXTRA_STATE_OFFHOOK -> PhoneStateStatus.CALL_STARTED
                TelephonyManager.EXTRA_STATE_IDLE -> PhoneStateStatus.CALL_ENDED
                else -> PhoneStateStatus.NOTHING
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
