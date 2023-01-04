package com.hogent.android.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.hogent.android.R
import com.hogent.android.database.repositories.CustomerRepository
import com.hogent.android.databinding.FragmentLoginBinding
import com.hogent.android.util.AuthenticationManager
import com.hogent.android.util.closeKeyboardOnTouch

class LoginFragment : Fragment() {

    override fun onCreateView(   inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?    ): View? {
        val binding: FragmentLoginBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        val viewModelFactory = LoginViewModelFactory(CustomerRepository());
        val loginView = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java];

        binding.loginViewModel = loginView;
        binding.lifecycleOwner = this;

        binding.root.closeKeyboardOnTouch();


        loginView.success.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(requireContext(), "Welkom terug!", Toast.LENGTH_SHORT)
                    .show();
                NavHostFragment.findNavController(this).navigate(LoginFragmentDirections.loginToProfile());
                loginView.doneSuccess();
            }
        });

        loginView.navToRegister.observe(viewLifecycleOwner, Observer {
            if(it){
                NavHostFragment.findNavController(this).navigate(LoginFragmentDirections.loginToRegister())
                loginView.doneNavigating()
            }
        })

        loginView.errorToast.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(requireContext(), "Verkeerde inloggegevens.", Toast.LENGTH_SHORT)
                    .show();
                loginView.doneErrorToast();
            }
        })


        Toast.makeText(context, "Log in met je account", Toast.LENGTH_SHORT).show()
        return binding.root
    }
    }

