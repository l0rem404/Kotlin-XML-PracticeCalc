package com.example.calc.Hist

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calc.R
import com.example.calc.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var historyList: MutableList<HistoryItem>
    private lateinit var clearBtn: Button
    private lateinit var backBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        historyList = mutableListOf()
        historyAdapter = HistoryAdapter(historyList)
        recyclerView.adapter = historyAdapter

        clearBtn = findViewById(R.id.btnClear)
        backBtn = findViewById(R.id.btnBack)

        clearBtn.setOnClickListener{clearHistory()}
        backBtn.setOnClickListener{onBackPressed()}

        indexHistory()
    }

    private fun indexHistory() {
        RetrofitClient.instance.indexHistory().enqueue(object : Callback<HistoryResponse> {
            override fun onResponse(call: Call<HistoryResponse>, response: Response<HistoryResponse>) {
                if (response.isSuccessful) {
                    historyList.clear()
                    response.body()?.let {
                        historyList.addAll(it.data)  // Access the "data" array from the response
                    }
                    historyAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@HistoryActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                Toast.makeText(this@HistoryActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun clearHistory() {
        RetrofitClient.instance.clearHistory().enqueue(object : Callback<HistoryResponse> {
            override fun onResponse(call: Call<HistoryResponse>, response: Response<HistoryResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@HistoryActivity, response.body()?.message, Toast.LENGTH_SHORT).show()

                    historyList.clear()
                    historyAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@HistoryActivity, "Failed to clear history", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                Toast.makeText(this@HistoryActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


}
