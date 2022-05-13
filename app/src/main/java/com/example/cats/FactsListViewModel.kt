package com.example.cats

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cats.util.DispatcherProvider
import com.example.cats.util.Resource
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FactsListViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    sealed class CatFactsEvent{
        class Success(val result: List<CatFactModelItem>): CatFactsEvent()
        class Failure(val errorText: String): CatFactsEvent()
        object Loading: CatFactsEvent()
        object Empty: CatFactsEvent()
    }

    private val _facts = MutableStateFlow<CatFactsEvent>(CatFactsEvent.Empty)
    val facts: StateFlow<CatFactsEvent> = _facts

 fun getFacts(){
     viewModelScope.launch(dispatchers.io){
         _facts.value = CatFactsEvent.Loading
         when (val catFactsResponse = repository.getCatFacts()){
             is Resource.Error -> _facts.value = CatFactsEvent.Failure(catFactsResponse.message!!)
             is Resource.Success -> {
                 val r = catFactsResponse.data!!
                 if (r == null){
                     _facts.value = CatFactsEvent.Failure("No cat facts to display")
                 }
                 else{
                     _facts.value = CatFactsEvent.Success(r)
                 }
             }
         }
     }
 }

}