package com.ort.isp.cryptoapp.framework.ui.marketPrice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ort.isp.cryptoapp.R
import com.ort.isp.cryptoapp.data.model.`in`.MarketCoin
import com.ort.isp.cryptoapp.databinding.FragmentMarketPriceBinding
import com.ort.isp.cryptoapp.framework.data.local.CoinsCache.getCoins
import com.ort.isp.cryptoapp.framework.ui.shared.TitledNavActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarketPriceFragment : Fragment() {

    private lateinit var adapter: MarketPriceAdapter
    private var _binding: FragmentMarketPriceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        adapter = MarketPriceAdapter()
        _binding = FragmentMarketPriceBinding.inflate(inflater, container, false)
        (activity as TitledNavActivity).setNavTitle(getString(R.string.market_price_title))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loading
        binding.coinAccountList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@MarketPriceFragment.adapter
        }
        updateUIWithCoins()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUIWithCoins() {
        val marketCoin: MutableList<MarketCoin> = ArrayList()
// Here we suppose to put the real time value of each coin, but we mock this because we didn't make on time to add this feature
        for (coin in getCoins()) marketCoin.add(MarketCoin(coin, RANDOM_NUMBER_MOCK))
        adapter.setCoinAccount(marketCoin)
    }

}

private const val RANDOM_NUMBER_MOCK = "40"