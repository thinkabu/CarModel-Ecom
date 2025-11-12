package com.example.ltdd_dacs3.Fragments.LoginRegister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ltdd_dacs3.Activities.MainActivity
import com.example.ltdd_dacs3.R
import com.example.ltdd_dacs3.Util.Resource
import com.example.ltdd_dacs3.ViewModel.LoginViewModel
import com.example.ltdd_dacs3.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnLoginLogin.setOnClickListener{
                val email = edtEmailLogin.text.toString().trim()
                val password = edtPasswordLogin.text.toString()
                viewModel.login(email, password)
            }
        }

        binding.txtRegister.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.login.collect {
                when(it){
                    is Resource.Loading -> {
                        binding.btnLoginLogin.startAnimation()
                    }
                    is Resource.Success ->{
                        binding.btnLoginLogin.revertAnimation()
                        Intent(requireActivity(), MainActivity::class.java).also { intent ->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(intent)
                        }
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        binding.btnLoginLogin.revertAnimation()
                    }
                    else -> Unit
                }
            }
        }

    }

}