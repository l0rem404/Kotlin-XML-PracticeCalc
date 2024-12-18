package com.example.calc.Hist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calc.R

class HistoryAdapter(private val historyList: List<HistoryItem>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.hist_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val historyData = historyList[position]
        holder.expression.text = historyData.expression
        holder.res.text = historyData.result
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expression: TextView = itemView.findViewById(R.id.txtExpression)
        val res: TextView = itemView.findViewById(R.id.txtRes)
    }
}
