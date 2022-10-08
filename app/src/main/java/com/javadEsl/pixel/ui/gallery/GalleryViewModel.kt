package com.javadEsl.pixel.ui.gallery

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.javadEsl.pixel.NetworkHelper
import com.javadEsl.pixel.data.PixelRepository
import com.javadEsl.pixel.data.topics.TopicsModelItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val pixelRepository: PixelRepository,
    private val networkHelper: NetworkHelper,
    private val pref: SharedPreferences,
) : ViewModel() {

    val allPhotos = pixelRepository.getAllPhotos().cachedIn(viewModelScope)

    fun topicPhotos(topicId: String) =
        pixelRepository.getTopicsPhotos(topicId).cachedIn(viewModelScope)

    private val _liveDataTopics = MutableLiveData<List<TopicsModelItem>>()
    val liveDataTopics: LiveData<List<TopicsModelItem>> = _liveDataTopics
    fun topicsList() {
        viewModelScope.launch {
            if (!networkHelper.hasInternetConnection()) {
                _liveDataTopics.postValue(emptyList())
                return@launch
            }

            try {
                val response = pixelRepository.getTopics()
                val photos = response.toMutableList()
                if (photos.isNotEmpty()) {
                    photos.add(0, TopicsModelItem(id = "user_type", title = "recommended for you"))
                }
                if (response.isNotEmpty()) {
                    _liveDataTopics.postValue(photos)
                }
            } catch (e: Exception) {
                Log.e("TAG", "getAutocomplete: $e")
            }

        }
    }

    fun onTopicItemClick(topicsModelItem: TopicsModelItem, position: Int) {
        val editor = pref.edit()
        editor.putInt("topic_position_item", position)
        editor.putString("topic_id_item", topicsModelItem.id)
        editor.apply()
    }

    fun getTopicIdAndPosition(): Pair<String, Int> {
        val position = pref.getInt("topic_position_item", 0)
        val id = pref.getString("topic_id_item", TopicsModelItem.Type.USER) ?: TopicsModelItem.Type.USER
        return Pair(id, position)
    }

}