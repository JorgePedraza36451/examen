package com.project.examen.data.service

import com.project.examen.data.response.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {
    @GET("api/character/")
    suspend fun getCharacterService(
        @Query("page") page: Int
    ): CharacterResponse
}