package com.hogent.android.ui.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.hogent.android.R
import com.hogent.android.data.repositories.CustomerRepository
import com.hogent.android.databinding.FragmentRegisterBinding
import com.hogent.android.ui.login.LoginFragment
import com.hogent.android.util.closeKeyboardOnTouch

class RegisterFragment : Fragment() {

    private lateinit var viewModel : RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRegisterBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_register, container, false
        )
        val application = requireNotNull(this.activity).application
        val factory = RegisterFactoryModel(CustomerRepository(), application)
        viewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]

        binding.lifecycleOwner = this
        binding.registerViewModel = viewModel
        binding.root.closeKeyboardOnTouch()

        Log.d("registercreated", "register fragment created")
        viewModel.navigateHome.observe(viewLifecycleOwner, Observer{
            if(it){
                NavHostFragment.findNavController(this).navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                viewModel.navigated()
            }
        })

        val loginFrag = LoginFragment()
        val bundle = Bundle()
        bundle.putString("message", "Log in met je account.")
        loginFrag.arguments = bundle

        viewModel.navToLogin.observe(viewLifecycleOwner, Observer {
            if(it){
                NavHostFragment.findNavController(this).navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                viewModel.navigated()
            }
        })

        viewModel.requireToast.observe(viewLifecycleOwner, Observer{
            if(it){
                println("Should send toast here")
                Toast.makeText(requireContext(), viewModel.registerForm.value!!.getError(), Toast.LENGTH_SHORT).show()
                viewModel.errorSent()
            }
        })


        return binding.root
    }
}