package com.hogent.android.ui.klant

import android.os.Bundle
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
import com.hogent.android.database.repositories.CustomerRepository
import com.hogent.android.databinding.FragmentProfielBinding
import com.hogent.android.util.closeKeyboardOnTouch


class CustomerProfileFragment: Fragment() {

    override fun onCreateView( inflater: LayoutInflater,   container: ViewGroup?,   savedInstanceState: Bundle?): View? {
        val binding: FragmentProfielBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profiel, container, false);

        val viewModelFactory = CustomerViewModelFactory(CustomerRepository());

        val customerView = ViewModelProvider(this, viewModelFactory)[(CustomerViewModel::class.java)];

        binding.customerViewModel = customerView
        binding.lifecycleOwner = this
        binding.root.closeKeyboardOnTouch()

        customerView.success.observe(viewLifecycleOwner, Observer{
            if(it){
                Toast.makeText(requireContext(),"Contactpersoon werd aangepast", Toast.LENGTH_LONG).show()
                customerView.doneSuccessToast()
            }
        })
        customerView.error.observe(viewLifecycleOwner, Observer{
            if(it){
                Toast.makeText(requireContext(),customerView.getError(), Toast.LENGTH_LONG).show();
                customerView.doneErrorToast()
            }
        })

        customerView.failsafeRedirect.observe(viewLifecycleOwner, Observer{
            if(it){
                NavHostFragment.findNavController(this).navigate(CustomerProfileFragmentDirections.actionFromProfileToLogin())
                customerView.doneNavigation()
            }
        })


        return binding.root
    }
}