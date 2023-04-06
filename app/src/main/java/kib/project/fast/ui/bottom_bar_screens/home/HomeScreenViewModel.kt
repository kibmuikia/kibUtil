package kib.project.fast.ui.bottom_bar_screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kib.project.core.utils.NetworkCallResult
import kib.project.data.database.repositories.SampleRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeScreenViewModel(
    private val sampleRepository: SampleRepository,
) : ViewModel() {
    suspend fun sampleFetchMovieGenresListFromApi() {
        viewModelScope.launch {
            when (val resultGenres = sampleRepository.getMovieGenresList()) {
                is NetworkCallResult.Success -> {
                    Timber.i(":: genre-list-result[ ${resultGenres.data} ]")
                    // Sample response: :: genre-list-result[ GenreResponse(genres=[Genre(id=28, name=Action), Genre(id=12, name=Adventure), Genre(id=16, name=Animation), Genre(id=35, name=Comedy), Genre(id=80, name=Crime), Genre(id=99, name=Documentary), Genre(id=18, name=Drama), Genre(id=10751, name=Family), Genre(id=14, name=Fantasy), Genre(id=36, name=History), Genre(id=27, name=Horror), Genre(id=10402, name=Music), Genre(id=9648, name=Mystery), Genre(id=10749, name=Romance), Genre(id=878, name=Science Fiction), Genre(id=10770, name=TV Movie), Genre(id=53, name=Thriller), Genre(id=10752, name=War), Genre(id=37, name=Western)]) ]
                }

                is NetworkCallResult.Error -> {
                    Timber.i(":: Error[ msg = ${resultGenres.message} ].")
                }
            }
        }
    }
}