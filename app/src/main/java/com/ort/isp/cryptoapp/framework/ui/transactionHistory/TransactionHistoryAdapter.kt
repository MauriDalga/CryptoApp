package com.ort.isp.cryptoapp.framework.ui.transactionHistory

import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.ort.isp.cryptoapp.R
import com.ort.isp.cryptoapp.data.model.`in`.TransactionDetail
import com.ort.isp.cryptoapp.databinding.TransactionHistoryRowBinding
import com.ort.isp.cryptoapp.framework.ui.shared.inflate
import java.text.SimpleDateFormat
import java.util.*

class TransactionHistoryAdapter : RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder>() {

    var transactionsDetails = mutableListOf<TransactionDetail>()

    fun setTransactionsDetail(transactionsDetails: List<TransactionDetail>) {
        this.transactionsDetails = transactionsDetails.toMutableList()
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
        holder.bind(transactionDetails)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = TransactionHistoryRowBinding.bind(view)

        fun bind(transactionDetail: TransactionDetail) = with(binding) {
            if (transactionDetail.operation == "Debit") {
                operationIcon.setImageDrawable(
                    ResourcesCompat.getDrawable(itemView.resources, R.drawable.ic_debit, null)
                )
            } else {
                operationIcon.setImageDrawable(
                    ResourcesCompat.getDrawable(itemView.resources, R.drawable.ic_accredit, null)
                )
            }
            transactionAmount.text = transactionDetail.amount.toString()
            coinName.text = transactionDetail.coinName

            transactionDate.text =
                SimpleDateFormat("d MMM", Locale("es", "ar")).format(transactionDetail.date)
            transactionId.text = transactionDetail.walletAddress
        }
    }
}