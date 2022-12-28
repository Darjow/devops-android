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
import com.hogent.android.database.DatabaseImp
import com.hogent.android.database.entities.BackupType
import com.hogent.android.database.entities.Project
import com.hogent.android.database.repositories.VmAanvraagRepository
import com.hogent.android.databinding.AddvmFragmentBinding
import com.hogent.android.util.closeKeyboardOnTouch
import timber.log.Timber
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class VmAanvraagFragment : Fragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application = requireNotNull(this.activity).application
        val binding: AddvmFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.addvm_fragment, container, false);
        val viewModelFactory = VmAanvraagFactoryModel(VmAanvraagRepository(DatabaseImp.getInstance(application)))
        val vmAanvraagView = ViewModelProvider(this, viewModelFactory)[VmAanvraagViewModel::class.java]

        binding.viewmodel = vmAanvraagView
        binding.lifecycleOwner = this
        binding.root.closeKeyboardOnTouch()

        initializeComponents(binding)


        return binding.root
    }




    private fun initializeComponents(binding: AddvmFragmentBinding) {
        val context = requireContext()

//MEMORY
        val spinner_memory = binding.root.findViewById<Spinner>(R.id.memoryVmAanvraagDropdownList)
        val listMemory = arrayListOf("1GB","2GB","4GB","8GB","16GB","32GB")
        val adapter_memory = ArrayAdapter(context, android.R.layout.simple_spinner_item, listMemory)
        adapter_memory.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinner_memory.adapter = adapter_memory

            spinner_memory.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                    val selected = listMemory[pos]
                    binding.viewmodel!!.memoryGBChanged(selected)
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

//CPU
        val seek = binding.root.findViewById<SeekBar>(R.id.aantalVcpuAanvraag);
        seek.max = 15
        val text = binding.root.findViewById<TextView>(R.id.titleVcpuAanvraag);
        seek?.setOnSeekBarChangeListener(object :  SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                text.text = getString(R.string.title_aantal_vcpu) + ' ' +p1.toString()
                binding.viewmodel!!.coresCpuChanged(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {           }
            override fun onStopTrackingTouch(p0: SeekBar?) {            }
        })

//BACKUP

        val listBackup = BackupType.values().map { it.toString() }
        val spinnerBackup = binding.root.findViewById<Spinner>(R.id.backupVmDropdownList)
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, listBackup)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinnerBackup.adapter = adapter
        spinnerBackup.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val selected = listBackup[pos]
                binding.viewmodel!!.backupTypeChanged(selected)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
//PROJECT
        val spinnerProject = binding.root.findViewById<Spinner>(R.id.spinner_project)
        val adapterProject = ArrayAdapter(context, android.R.layout.simple_spinner_item, arrayListOf<String>())
        adapterProject.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.viewmodel!!.projecten.observe(viewLifecycleOwner) {
            adapterProject.clear();
            it.forEach { project ->
                adapterProject.add(project.name)
            }
            adapterProject.notifyDataSetChanged()
        }
        spinnerProject.adapter = adapterProject

        spinnerProject.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, Id: Long) {
                val project_naam = parent!!.getItemAtPosition(pos) as String
                binding.viewmodel!!.projectChanged(project_naam)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }



        binding.groupModeVm.setOnCheckedChangeListener { radioGroup, i ->
            val value = binding.root.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            binding.viewmodel!!.modeChanged(value.text.toString())
        }

        binding.groupOsVm.setOnCheckedChangeListener { radioGroup, i ->
            val value = binding.root.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            binding.viewmodel!!.osChanged(value.text.toString())
        }

        binding.startDateVmAanvraag.setOnDateChangedListener { datePicker, year, month, day ->
            try {
                val start = LocalDate.of(year, month, day);
                binding.viewmodel!!.startDateChanged(start)
            }catch(e: Exception){
                Timber.d("Error during startup in startdatevmaanvraag changedlistener")
            }
        }

        binding.endDateVmAanvraag.setOnDateChangedListener { datePicker, year, month, day ->
            try {
                val end = LocalDate.of(year, month, day);
                binding.viewmodel!!.endDateChanged(end)
            }catch(e: Exception){
                Timber.d("Error during startup in enddatevmaanvraag changedlistener")
            }

        }
        binding.viewmodel!!.errorToast.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(),binding.viewmodel!!.form.value!!.getError(),Toast.LENGTH_SHORT).show()
                binding.viewmodel!!.doneToastingError()
            }
        }
        binding.viewmodel!!.success.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "Verzoek werd verstuurd", Toast.LENGTH_SHORT).show()
                NavHostFragment.findNavController(this).navigate(VmAanvraagFragmentDirections.actionFromRequestToList())
                binding.viewmodel!!.doneSuccess()
            }
        }

    }
}