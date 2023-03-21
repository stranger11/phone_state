package it.mainella.phone_state.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.TELECOM_SERVICE
import android.content.Context.TELEPHONY_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
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
import io.flutter.BuildConfig
import io.flutter.view.AccessibilityBridge.Action
import it.mainella.phone_state.utils.PhoneStateStatus


var status: PhoneStateStatus = PhoneStateStatus.NOTHING;


open class PhoneStateReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            if (Build.BRAND == "Huawei") {
                val intentService = Intent(context, MyCallService::class.java)
                context?.startService(intentService)
            } else
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

@RequiresApi(Build.VERSION_CODES.M)
class MyCallService : InCallService() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCallRemoved(call: Call?) {
        super.onCallRemoved(call)
    }

    override fun onConnectionEvent(call: Call?, event: String?, extras: Bundle?) {
        super.onConnectionEvent(call, event, extras)
    }

    override fun onCallAdded(call: Call?) {
        super.onCallAdded(call)
        val callBack = TelecomCallCallback()
        call?.registerCallback(callBack)
    }
}


@RequiresApi(Build.VERSION_CODES.M)
class TelecomCallCallback : Call.Callback() {
    override fun onCallDestroyed(call: Call?) {
        super.onCallDestroyed(call)
        status = PhoneStateStatus.CALL_ENDED
    }
}