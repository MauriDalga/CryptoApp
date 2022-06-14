package com.ort.isp.cryptoapp.framework.ui.transactionHistory

import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.ort.isp.cryptoapp.R
import com.ort.isp.cryptoapp.data.model.`in`.TransactionDetail
import com.ort.isp.cryptoapp.databinding.TransactionHistoryRowBinding
import com.ort.isp.cryptoapp.framework.ui.shared.inflate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class TransactionHistoryAdapter : RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder>() {

    var transactionsDetails = mutableListOf<TransactionDetail>()
    lateinit var userId: String

    fun setTransactionsDetail(transactionsDetails: List<TransactionDetail>, userId: String) {
        this.transactionsDetails = transactionsDetails.toMutableList()
        this.userId = userId
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.transaction_history_row, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = transactionsDetails.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transactionDetails = transactionsDetails[position]
        holder.itemView.resources
        holder.bind(transactionDetails, userId)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = TransactionHistoryRowBinding.bind(view)

        fun bind(transactionDetail: TransactionDetail, userId: String) = with(binding) {
            if (transactionDetail.senderId == userId) {
                operationIcon.setImageDrawable(
                    ResourcesCompat.getDrawable(itemView.resources, R.drawable.ic_debit, null)
                )
            } else {
                operationIcon.setImageDrawable(
                    ResourcesCompat.getDrawable(itemView.resources, R.drawable.ic_accredit, null)
                )
            }
            transactionAmount.text = transactionDetail.amount.toString()
            coinName.text = transactionDetail.coin.longName

            transactionDate.text = DateTimeFormatter.ofPattern("d MMM", Locale("es", "ar"))
                .format(LocalDateTime.parse(transactionDetail.date))
        }
    }
}