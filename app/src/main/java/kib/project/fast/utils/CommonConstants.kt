package kib.project.fast.utils

import kib.project.fast.BuildConfig

const val EXPANSION_TRANSITION_DURATION = 450

val BASEURL = if (BuildConfig.BUILD_TYPE == "release") {
    "https://sample-prod-api.com/v1/"
} else {
    "https://sample-staging-api.com/v1/"
}