package kib.project.fast.utils

import android.Manifest
import android.provider.Telephony
import kib.project.fast.BuildConfig

const val EXPANSION_TRANSITION_DURATION = 450

/*
* Tutorial: [ version 3 of The Movie Database (TMDB) API: https://developers.themoviedb.org/3/getting-started/introduction ].
*
* https://api.themoviedb.org/3/movie/76341?api_key=<<api_key>>
* */
val BASEURL = if (BuildConfig.BUILD_TYPE == "release") {
    "https://f472-41-90-187-56.ngrok.io"
} else {
    "https://f472-41-90-187-56.ngrok.io" //"https://api.themoviedb.org/3/"
}

const val PERMISSION_READ_SMS = Manifest.permission.READ_SMS
const val PERMISSION_RECEIVE_SMS = Manifest.permission.RECEIVE_SMS
const val ACTION_SMS_RECEIVE = Telephony.Sms.Intents.SMS_RECEIVED_ACTION
const val MPESA = "MPESA"