package com.example.cats

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CatFactsAPI {
    @GET ("facts/random?&amount=30")
    suspend fun getFacts(): Response<CatFactModel>
}
//PS.I wouldn't normally hardcode the "amount" but it's a small app so it was quicker