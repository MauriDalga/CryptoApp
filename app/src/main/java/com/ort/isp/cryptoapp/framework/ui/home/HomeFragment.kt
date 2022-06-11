package com.ort.isp.cryptoapp.framework.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ort.isp.cryptoapp.R
import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.databinding.FragmentHomeBinding
import com.ort.isp.cryptoapp.framework.ui.shared.TitledNavActivity
import com.ort.isp.cryptoapp.framework.ui.shared.showMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: CoinAccountAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        adapter = CoinAccountAdapter()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        (activity as TitledNavActivity).setNavTitle(getString(R.string.home_title))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loadingProgressBar = binding.loading
        binding.coinAccountList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@HomeFragment.adapter
        }

        homeViewModel.userFullData.observe(viewLifecycleOwner, Observer { userFullData ->
            userFullData ?: return@Observer

            when (userFullData) {
                is Resource.Loading -> loadingProgressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    loadingProgressBar.visibility = View.GONE
                    adapter.setCoinAccount(userFullData.data!!.coinAccounts)
                }
                else -> {
                    loadingProgressBar.visibility = View.GONE
                    showMessage(userFullData.message!!)
                }
            }

        })

        homeViewModel.fetchUserData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}