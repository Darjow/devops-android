package com.hogent.android.ui.vms.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.hogent.android.R
import com.hogent.android.data.repositories.VmDetailRepository
import com.hogent.android.databinding.FragmentVmDetailsBinding

class VMDetailsFragment : Fragment() {

    private lateinit var binding: FragmentVmDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vm_details, container, false)

        var vm_id: Int = arguments!!.getInt("vm_id")
        val viewModelFactory = VMDetailsViewModelFactory(VmDetailRepository(vm_id))
        val viewModel = ViewModelProvider(this, viewModelFactory)[VMDetailsViewModel::class.java]

        binding.vmViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.navBack.observe(
            viewLifecycleOwner,
            Observer {
                if (it) {
                    NavHostFragment.findNavController(this).navigate(
                        VMDetailsFragmentDirections.actionFromDetailToVmlist()
                    )
                }
            }
        )

        return binding.root
    }
}
