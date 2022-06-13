package com.ort.isp.cryptoapp.framework.ui.shareQr

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import com.ort.isp.cryptoapp.R
import com.ort.isp.cryptoapp.data.repository.LoginRepository
import com.ort.isp.cryptoapp.databinding.FragmentShareQrBinding
import com.ort.isp.cryptoapp.framework.data.local.QR_IMAGE_PATH
import com.ort.isp.cryptoapp.framework.data.local.QR_IMAGE_TYPE
import com.ort.isp.cryptoapp.framework.ui.shared.TitledNavActivity
import com.ort.isp.cryptoapp.framework.ui.shared.logout
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject


@AndroidEntryPoint
class ShareQrFragment : Fragment() {

    @Inject
    lateinit var loginRepository: LoginRepository
    private var _binding: FragmentShareQrBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShareQrBinding.inflate(inflater, container, false)
        (activity as TitledNavActivity).setNavTitle(getString(R.string.share_QR_title))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setImageQr()
        binding.sendQrButton.setOnClickListener {
            shareQrImage()
        }
    }

    private fun setImageQr() {
        loginRepository.getSession()?.let {
            val imgFile = File(context?.filesDir, QR_IMAGE_PATH)
            if (imgFile.exists()) {
                binding.qrCodeImage.setImageURI(Uri.fromFile(imgFile))
            }
        } ?: logout()
    }

    private fun shareQrImage() {
        val loggedUserLocalData = loginRepository.getSession()
        val intent: Intent = ShareCompat.IntentBuilder(context!!)
            .setType(QR_IMAGE_TYPE)
            .setSubject(context!!.getString(R.string.share_qr_subject))
            .setStream(Uri.parse(loggedUserLocalData!!.cachedQrImageUri))
            .setChooserTitle(R.string.share_qr_title)
            .createChooserIntent()
            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(Intent.createChooser(intent, getString(R.string.share_qr_title)))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}