package com.ort.isp.cryptoapp.framework.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.databinding.FragmentRegisterBinding
import com.ort.isp.cryptoapp.framework.ui.LoggedUserActivity
import com.ort.isp.cryptoapp.framework.ui.shared.showMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var registerViewModel: RegisterViewModel
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameEditText = binding.name
        val lastnameEditText = binding.lastname
        val emailEditText = binding.email
        val passwordEditText = binding.password
        val secondPasswordEditText = binding.secondPassword
        val registerButton = binding.register
        val loginLinkText = binding.login
        val loadingProgressBar = binding.loading

        registerViewModel.registerFormState.observe(viewLifecycleOwner,
            Observer { registerFormState ->
                registerFormState ?: return@Observer

                registerButton.isEnabled = registerFormState.isDataValid
                registerFormState.nameError?.let {
                    nameEditText.error = getString(it)
                }
                registerFormState.lastnameError?.let {
                    lastnameEditText.error = getString(it)
                }
                registerFormState.emailError?.let {
                    emailEditText.error = getString(it)
                }
                registerFormState.passwordError?.let {
                    passwordEditText.error = getString(it)
                }
                registerFormState.secondPasswordError?.let {
                    secondPasswordEditText.error = getString(it)
                }
            })

        registerViewModel.registerResult.observe(viewLifecycleOwner,
            Observer { registerResult ->
                registerResult ?: return@Observer

                when (registerResult) {
                    is Resource.Loading -> loadingProgressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        loadingProgressBar.visibility = View.GONE
                        startActivity(Intent(context, LoggedUserActivity::class.java))
                        activity?.finish()
                    }
                    is Resource.Error -> {
                        loadingProgressBar.visibility = View.GONE
                        showMessage(registerResult.message!!)
                    }
                    else -> {
                        loadingProgressBar.visibility = View.GONE
                    }
                }
            })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                registerViewModel.registerDataChanged(
                    nameEditText.text.toString(),
                    lastnameEditText.text.toString(),
                    emailEditText.text.toString(),
                    passwordEditText.text.toString(),
                    secondPasswordEditText.text.toString()
                )
            }
        }
        nameEditText.addTextChangedListener(afterTextChangedListener)
        lastnameEditText.addTextChangedListener(afterTextChangedListener)
        emailEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        secondPasswordEditText.addTextChangedListener(afterTextChangedListener)

        registerButton.setOnClickListener {
            if (registerViewModel.isFormDataValid()) {
                loadingProgressBar.visibility = View.VISIBLE
                registerViewModel.register(
                    nameEditText.text.toString(),
                    lastnameEditText.text.toString(),
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
        }

        loginLinkText.setOnClickListener {
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}