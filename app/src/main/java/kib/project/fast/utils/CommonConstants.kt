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
    "https://api.themoviedb.org/3/" // "https://sample-prod-api.com/v1/"
} else {
    "https://5334-196-107-168-122.ngrok.io/transaction/" //"https://api.themoviedb.org/3/" // "https://sample-staging-api.com/v1/"
}

const val PERMISSION_READ_SMS = Manifest.permission.READ_SMS
const val PERMISSION_RECEIVE_SMS = Manifest.permission.RECEIVE_SMS
const val ACTION_SMS_RECEIVE = Telephony.Sms.Intents.SMS_RECEIVED_ACTION