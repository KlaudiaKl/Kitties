package com.example.cats

import com.example.cats.util.Resource

interface MainRepository {
    suspend fun getCatFacts(): Resource<CatFactModel>
}