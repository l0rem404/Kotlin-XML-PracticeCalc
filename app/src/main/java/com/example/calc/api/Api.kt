package com.example.calc.api

import com.example.calc.Hist.HistoryItem
import com.example.calc.Hist.HistoryResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST


data class AuthResponse(
    val token: String,
    val tokenType: String
)


interface ApiService {
    @POST("register")
    fun register(@Body user: UserReg): Call<AuthResponse>

    @POST("login")
    fun login(@Body user: UserLogIn): Call<AuthResponse>

    @POST("logout")
    suspend fun logout(): LogoutResponse

    @POST("history")
    suspend fun saveHistory(@Body request: CalcReq): CalcResponse

    @GET("history")
    fun indexHistory(): Call<HistoryResponse>

    @DELETE("history/clear")
    fun clearHistory(): Call<HistoryResponse>


}





