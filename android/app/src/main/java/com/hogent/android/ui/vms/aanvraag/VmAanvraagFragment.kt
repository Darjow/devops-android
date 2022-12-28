package com.hogent.android.ui.vms.aanvraag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.hogent.android.R
import com.hogent.android.databinding.AddvmFragmentBinding
import com.hogent.android.util.closeKeyboardOnTouch
import java.time.LocalDate
import java.util.*

class VmAanvraagFragment : Fragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application = requireNotNull(this.activity).application
        val binding: AddvmFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.addvm_fragment, container, false);
        val viewModelFactory = VmAanvraagFactoryModel(application)
        val vmAanvraagView = ViewModelProvider(this, viewModelFactory)[VmAanvraagViewModel::class.java]

        binding.viewmodel = vmAanvraagView
        binding.lifecycleOwner = this
        binding.root.closeKeyboardOnTouch()

        initializeComponents(binding)


        vmAanvraagView.errorToast.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it){
                Toast.makeText(requireContext(),  vmAanvraagView.form.value!!.getError(), Toast.LENGTH_SHORT).show()
                vmAanvraagView.doneToastingError()
            }
        })
        vmAanvraagView.success.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it){
                Toast.makeText(requireContext(), "Verzoek werd verstuurd", Toast.LENGTH_SHORT).show()
                NavHostFragment.findNavController(this).navigate(VmAanvraagFragmentDirections.actionFromRequestToList())
                vmAanvraagView.doneSuccess()
            }
        })

        return binding.root
    }

    private fun initializeComponents(binding: AddvmFragmentBinding) {
        val spinner_memory = binding.root.findViewById<Spinner>(R.id.memoryVmAanvraagDropdownList)
        val listMemory = arrayListOf("2GB","4GB","6GB","8GB","10GB","12GB","14GB","16GB")
        val contex = this.context!!
        if(spinner_memory != null){
            val adapter = ArrayAdapter(contex, android.R.layout.simple_spinner_item, listMemory)
            spinner_memory.adapter = adapter
        }
        spinner_memory.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val selected = listMemory[pos]
                binding.viewmodel.memoryGBChanged(selected)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        val seek = binding.root.findViewById<SeekBar>(R.id.aantalVcpuAanvraag);
        val text = binding.root.findViewById<TextView>(R.id.titleVcpuAanvraag);
        seek?.setOnSeekBarChangeListener(object :  SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                text.text = getString(R.string.title_aantal_vcpu) + ' ' +p1.toString()
                binding.viewmodel.coresCpuChanged(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        val listBackup = arrayListOf("Dagelijks","Wekenlijks","Maandelijks","Nooit")
        val spinnerBackup = binding.root.findViewById<Spinner>(R.id.backupVmDropdownList)
        val adapter = ArrayAdapter(contex, android.R.layout.simple_spinner_item, listBackup)
        spinnerBackup.adapter = adapter
        spinnerBackup.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val selected = listBackup[pos]
                binding.viewmodel.backupTypeChanged(selected)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.groupModeVm.setOnCheckedChangeListener { radioGroup, i ->
            val value = binding.root.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            binding.viewmodel.modeChanged(value.text.toString())
        }

        binding.groupOsVm.setOnCheckedChangeListener { radioGroup, i ->
            val value = binding.root.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            binding.viewmodel.osChanged(value.text.toString())
        }

        binding.startDateVmAanvraag.setOnDateChangedListener { datePicker, year, month, day ->
            val start = LocalDate.of(year, month ,day);
            binding.viewmodel.startDateChanged(start)
        }

        binding.endDateVmAanvraag.setOnDateChangedListener { datePicker, year, month, day ->
            val end = LocalDate.of(year, month ,day);
            binding.viewmodel.endDateChanged(end)
        }
    }
}