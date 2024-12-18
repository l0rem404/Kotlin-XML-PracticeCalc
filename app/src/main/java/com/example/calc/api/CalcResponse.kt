package com.example.calc.api

data class CalcResponse(
    val message: String,
    val data: HistoryData
)

data class HistoryData(
    val expression: String,
    val result: String,
    val user_id: Int,
)


