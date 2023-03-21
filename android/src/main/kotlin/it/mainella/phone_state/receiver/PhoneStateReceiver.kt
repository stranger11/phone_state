package it.mainella.phone_state.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.TELECOM_SERVICE
import android.content.Context.TELEPHONY_SERVICE
import android.content.Intent
import android.os.Build
import android.telecom.Call
import android.telecom.DisconnectCause
import android.telecom.InCallService
import android.telecom.TelecomManager
import android.telephony.PhoneStateListener
import android.telephony.PhoneStateListener.LISTEN_CALL_STATE
import android.telephony.TelephonyCallback
import android.telephony.TelephonyManager
import android.util.Log
import androidx.annotation.RequiresApi
import io.flutter.view.AccessibilityBridge.Action
import it.mainella.phone_state.utils.PhoneStateStatus

open class PhoneStateReceiver : BroadcastReceiver() {
    var status: PhoneStateStatus = PhoneStateStatus.NOTHING;
    override fun onReceive(context: Context?, intent: Intent?) {
        try {
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