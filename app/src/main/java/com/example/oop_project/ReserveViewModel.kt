import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.oop_project.NaverPlace
import com.example.oop_project.NaverSearchRepository

class ReserveViewModel(private val repository: NaverSearchRepository) : ViewModel() {
    private val _places = MutableLiveData<List<NaverPlace>>()
    val places: LiveData<List<NaverPlace>> get() = _places

    fun searchPlaces(searchWord: String) {
        repository.searchNaver(searchWord) { places ->
            _places.postValue(places)
        }
    }
}
