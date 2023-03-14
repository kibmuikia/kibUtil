package kib.project.fast.utils

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
    "https://api.themoviedb.org/3/" // "https://sample-staging-api.com/v1/"
}