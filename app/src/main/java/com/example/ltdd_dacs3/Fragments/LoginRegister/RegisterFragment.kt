package com.example.ltdd_dacs3.Fragments.LoginRegister

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ltdd_dacs3.Data.User
import com.example.ltdd_dacs3.R
import com.example.ltdd_dacs3.Util.RegisterValidation
import com.example.ltdd_dacs3.Util.Resource
import com.example.ltdd_dacs3.ViewModel.RegisterViewModel
import com.example.ltdd_dacs3.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val TAG = "RegisterActivity"

@AndroidEntryPoint
class RegisterFragment: Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.txtLogin.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.apply {
            btnRegister.setOnClickListener {
                val user = User(
                    edtFirstNameRegister.text.toString().trim(),
                    edtLastNameRegister.text.toString().trim(),
                    edtEmailRegister.text.toString().trim()
                )
                val password = edtPassword.text.toString()
                val confirmPassword = edtConfirmPassword.text.toString()
                viewModel.createAccountWithEmailAndPassword(user, password, confirmPassword)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.register.collect{
                when(it){
                    is Resource.Loading -> {
                        binding.btnRegister.startAnimation()
                    }
                    is Resource.Success -> {
                        Log.d("test", it.data.toString())
                        binding.btnRegister.revertAnimation()
                    }
                    is Resource.Error -> {
                        Log.e(TAG,it.message.toString())
                        binding.btnRegister.revertAnimation()
                    }
                    else -> Unit
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.validation.collect{validation ->
                if (validation.email is RegisterValidation.Failed){
                    withContext(Dispatchers.Main){
                        binding.edtEmailRegister.apply {
                            requestFocus()
                            error = validation.email.message
                        }
                    }
                }

                if (validation.password is RegisterValidation.Failed){
                    withContext(Dispatchers.Main){
                        binding.edtPassword.apply {
                            requestFocus()
                            error = validation.password.message
                        }
                    }
                }
                if (validation.confirmPassword is RegisterValidation.Failed){
                    withContext(Dispatchers.Main){
                        binding.edtConfirmPassword.apply {
                            requestFocus()
                            error = validation.confirmPassword.message
                        }
                    }
                }
            }
        }

    }

}