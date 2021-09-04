package com.example.wisdomtalk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class QuotesPageAdapter(private val quotes: List<Quote>) :
    RecyclerView.Adapter<QuotesPageAdapter.QuoteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        QuoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_quote, parent, false)
        )

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.bind(quotes[position])
    }

    override fun getItemCount()=quotes.size

    class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val quoteTextView: TextView = itemView.findViewById(R.id.txt_quote)
        private val nameTextView: TextView = itemView.findViewById(R.id.txt_name)

        fun bind(quote: Quote) {
            quoteTextView.text = quote.quote
            nameTextView.text = quote.name
        }
    }
}