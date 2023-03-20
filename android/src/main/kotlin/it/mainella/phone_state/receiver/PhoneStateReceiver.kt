package it.mainella.phone_state.receiver

import android.app.NotificationManager
import android.app.StatusBarManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.service.notification.StatusBarNotification
import android.telecom.CallRedirectionService
import android.telecom.InCallService
import android.telecom.PhoneAccountHandle
import android.telephony.CellSignalStrengthWcdma
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
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
            @RequiresApi(Build.VERSION_CODES.Q)
            object : CallRedirectionService() {
                override fun onPlaceCall(
                    handle: Uri,
                    initialPhoneAccount: PhoneAccountHandle,
                    allowInteractiveResponse: Boolean
                ) {
                    status = PhoneStateStatus.CALL_ENDED
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}