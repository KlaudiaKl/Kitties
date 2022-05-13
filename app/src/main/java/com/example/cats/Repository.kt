package com.example.cats

import com.example.cats.util.Resource
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: CatFactsAPI
) : MainRepository {
    override suspend fun getCatFacts(): Resource<CatFactModel> {

        return try {
            val response = api.getFacts()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else{
                Resource.Error(response.message())
            }

        }catch (e: Exception){
            Resource.Error(e.message?:"There's an error")
        }
    }
}