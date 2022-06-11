package com.ort.isp.cryptoapp.framework.ui.home

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ort.isp.cryptoapp.R
import com.ort.isp.cryptoapp.data.model.`in`.CoinAccount
import com.ort.isp.cryptoapp.databinding.CoinAccountRowBinding
import com.ort.isp.cryptoapp.framework.ui.shared.inflate


class CoinAccountAdapter : RecyclerView.Adapter<CoinAccountAdapter.ViewHolder>() {

    var coinAccounts = mutableListOf<CoinAccount>()

    fun setCoinAccount(coinAccounts: List<CoinAccount>) {
        this.coinAccounts = coinAccounts.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.coin_account_row, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = coinAccounts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coinAccount = coinAccounts[position]
        holder.bind(coinAccount)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CoinAccountRowBinding.bind(view)

        fun bind(coinAccount: CoinAccount) = with(binding) {
            val imageBytes = Base64.decode(coinAccount.coin.icon, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            coinIcon.setImageBitmap(decodedImage)
            coinName.text = "${coinAccount.coin.longName}\n${coinAccount.coin.shortName}"
            coinBalance.text = "${coinAccount.balance} ${coinAccount.coin.shortName}"
        }
    }
}