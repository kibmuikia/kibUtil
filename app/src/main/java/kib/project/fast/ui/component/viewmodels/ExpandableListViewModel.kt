package kib.project.fast.ui.component.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kib.project.fast.ui.component.models.ExpandableCardModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExpandableListViewModel : ViewModel() {
    private val _cards: MutableStateFlow<List<ExpandableCardModel>> = MutableStateFlow(emptyList());
    val cards = _cards.asStateFlow()

    private val _expandedCardIdsList = MutableStateFlow(listOf<Int>())
    val expandedCardIdsList: StateFlow<List<Int>> get() = _expandedCardIdsList

    init {
        getFakeData()
    }

    private fun getFakeData() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val testList = arrayListOf<ExpandableCardModel>()
                repeat(5) { testList += ExpandableCardModel(id = it, title = "Card $it") }
                _cards.emit(testList)
            }
        }
    }

    fun onCardArrowClicked(cardId: Int) {
        _expandedCardIdsList.value = _expandedCardIdsList.value.toMutableList().also { list ->
            if (list.contains(cardId)) list.remove(cardId) else list.add(cardId)
        }
    }

}