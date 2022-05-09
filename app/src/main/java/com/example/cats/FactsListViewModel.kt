package com.example.cats

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FactsListViewModel() : ViewModel() {
    private var factsLiveData = MutableLiveData<List<CatFactModelItem>>()
    var showProgress: Boolean = false

    fun getFacts() {
        showProgress = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.api.getFacts()
            if (response.isSuccessful) {
                if (response.body() != null) {
                    factsLiveData.postValue(response.body()!!)
                }
                showProgress = false
            } else {
                Log.d("NO DATA", "Did not fetch data")
                showProgress = false
            }
        }
    }

    fun observeFactsLiveData(): LiveData<List<CatFactModelItem>> {
        return factsLiveData
    }

    fun isResponseBodyNull(): Boolean {
        if (factsLiveData.value.isNullOrEmpty())
            return true
        return false
    }
}