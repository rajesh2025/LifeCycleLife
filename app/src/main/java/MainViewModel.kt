import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap

class MainViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    val filteredData: LiveData<String> =
        savedStateHandle.getLiveData<String>("query")

    fun setQuery(query: String) {
        savedStateHandle["query"] = query
    }


}