package com.example.calc.Hist

data class HistoryItem(
    val id: Int,
    val user_id: Int,
    val expression: String,
    val result: String,
    val created_at: String,
    val updated_at: String
)

data class HistoryResponse(
    val message: String,
    val data: List<HistoryItem>
)

