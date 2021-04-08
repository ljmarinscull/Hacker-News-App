package com.project.hackernews.ui.main

import androidx.lifecycle.*
import com.project.hackernews.data.model.NewObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.project.hackernews.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: IRepository
    ) : ViewModel() {

    private val _news = MutableLiveData<Result<ArrayList<NewObject>>>()
    val newsLiveData: LiveData<Result<ArrayList<NewObject>>> = _news
    private var deletedIds = listOf<String>()

    fun getNews(isOnline: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                _news.value = Result.Loading()
                var finaleResult: Result<ArrayList<NewObject>>? = null
                withContext(Dispatchers.IO) {
                    val result = repo.loadNews(isOnline)
                    val resultFiltered = result.filter { it.objectID !in deletedIds }
                    finaleResult = Result.Success(ArrayList(resultFiltered))
                }
                _news.value = finaleResult!!
            } catch (e: Exception){
                _news.value = Result.Error(e)
            }
        }
    }

    fun setDeletedIds(newDeletedIds: List<String>) {
        deletedIds = newDeletedIds
    }
}