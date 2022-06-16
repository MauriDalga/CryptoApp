package com.ort.isp.cryptoapp.framework.ui.marketPrice

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ort.isp.cryptoapp.R
import com.ort.isp.cryptoapp.data.model.`in`.MarketCoin
import com.ort.isp.cryptoapp.databinding.CoinAccountRowBinding
import com.ort.isp.cryptoapp.databinding.MarketPriceRowBinding
import com.ort.isp.cryptoapp.framework.ui.shared.inflate
import com.ort.isp.cryptoapp.framework.ui.shared.toBase64Bitmap

class MarketPriceAdapter : RecyclerView.Adapter<MarketPriceAdapter.ViewHolder>() {

    var coinAccounts = mutableListOf<MarketCoin>()

    fun setCoinAccount(coinAccounts: List<MarketCoin>) {
        this.coinAccounts = coinAccounts.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.market_price_row, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = coinAccounts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coinAccount = coinAccounts[position]
        holder.bind(coinAccount)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = MarketPriceRowBinding.bind(view)

        fun bind(marketCoin: MarketCoin) = with(binding) {
            coinIcon.setImageBitmap(marketCoin.coin.icon.toBase64Bitmap())
            coinName.text = "${marketCoin.coin.longName}\n${marketCoin.coin.shortName}"
            coinPrice.text = "$COIN ${marketCoin.price}"
        }
    }
}

private const val COIN = "USD"