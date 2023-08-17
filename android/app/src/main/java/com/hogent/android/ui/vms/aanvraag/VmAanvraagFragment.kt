package com.hogent.android.ui.vms.aanvraag

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.internal.ViewUtils
import com.hogent.android.R
import com.hogent.android.data.entities.BackupType
import com.hogent.android.data.entities.OperatingSystem
import com.hogent.android.data.repositories.VmAanvraagRepository
import com.hogent.android.databinding.AddvmFragmentBinding
import com.hogent.android.util.clearForm
import com.hogent.android.util.closeKeyboardOnTouch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.time.LocalDate
import java.time.Month

class VmAanvraagFragment : Fragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: AddvmFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.addvm_fragment, container, false);
        val viewModelFactory = VmAanvraagFactoryModel(VmAanvraagRepository())
        val vmAanvraagView = ViewModelProvider(this, viewModelFactory)[VmAanvraagViewModel::class.java]

        binding.viewmodel = vmAanvraagView
        binding.lifecycleOwner = this
        binding.root.closeKeyboardOnTouch()

        initializeComponents(binding)

        vmAanvraagView.errorToast.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(),vmAanvraagView.form.value!!.getError(),Toast.LENGTH_SHORT).show()
                vmAanvraagView.doneToastingError()
            }
        }
        vmAanvraagView.success.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "Verzoek werd verstuurd", Toast.LENGTH_SHORT).show()
                binding.vmaanvraaglayout.clearForm()
                vmAanvraagView.doneSuccess()
            }
        }

        vmAanvraagView.projectNaamCheck.observe(viewLifecycleOwner){
            if (it) {
                val toast = Toast.makeText(requireContext(), "Projectnaam moet uniek zijn!", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.TOP, 0 , 0)
                    toast.show()
                vmAanvraagView.naamCheckProjectReset()
            }
        }

        vmAanvraagView.closeKeyBoard.observe(viewLifecycleOwner){
            if(it){
                ViewUtils.hideKeyboard(this.view!!);
                vmAanvraagView.keyboardHidden()
                Toast.makeText(requireContext(), "Je hebt een nieuw project toegevoegd!", Toast.LENGTH_SHORT).show()
            }
        }

        vmAanvraagView.vmNaamBestaatAl.observe(viewLifecycleOwner){
            if(it){
                Toast.makeText(requireContext(), "Naam van virtualmachine moet uniek zijn!", Toast.LENGTH_SHORT).show()
                vmAanvraagView.naamCheckVmReset()
            }
        }

      /*  vmAanvraagView.navToList.observe(viewLifecycleOwner){
            if(it){
                NavHostFragment.findNavController(this).navigate(VmAanvraagFragmentDirections.actionFromRequestToList());
                vmAanvraagView.doneNavToList()
            }
        }*/

        return binding.root
    }

    private fun initializeComponents(binding: AddvmFragmentBinding) {
        val context = requireContext()
        val tintBlack = Color.BLACK
        val tintlist = ColorStateList.valueOf(tintBlack)
//OS
        val buttonContainer: RadioGroup = binding.root.findViewById(R.id.group_os_vm)
        OperatingSystem.values().sortedBy { it.name }.forEachIndexed{ index, it ->
            if(it.name != "NONE"){
                val btn = RadioButton(buttonContainer.context)
                btn.text = it.toString()
                btn.setHintTextColor(Color.BLACK)
                btn.setTextColor(Color.BLACK)
                btn.buttonTintList = tintlist
                buttonContainer.addView(btn)
            }
        }

//MEMORY
        val spinner_memory = binding.root.findViewById<Spinner>(R.id.memoryVmAanvraagDropdownList)
        val listMemory = arrayListOf("","1GB","2GB","4GB","8GB","16GB","32GB")
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

        val listBackup : MutableList<String> =  mutableListOf("")
            listBackup.addAll(BackupType.values().map { it.toString() })
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

        Timber.d("Lijst van projects: ", binding.viewmodel!!.projecten.value.toString())

        binding.viewmodel!!.projecten.observe(viewLifecycleOwner) {
            adapterProject.clear();
            adapterProject.add("")
            adapterProject.add("+ Project toevoegen")
            it.projects?.forEach { project ->
                adapterProject.add(project.name)
            }
            adapterProject.notifyDataSetChanged()
        }
        spinnerProject.adapter = adapterProject

        spinnerProject.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, Id: Long) {
                val project_naam = parent!!.getItemAtPosition(pos) as String
                var form =binding.root.findViewById<LinearLayout>(R.id.ProjectMakenForm)
                if(project_naam.equals("+ Project toevoegen")){
                    val animation = AnimationUtils.loadAnimation(context, com.google.android.material.R.anim.abc_fade_in)
                    form.startAnimation(animation)
                    form.visibility = View.VISIBLE
                    binding.viewmodel!!.projectChanged(project_naam)
                }
                else{
                    binding.viewmodel!!.projectChanged(project_naam)
                    form.visibility = View.GONE
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        binding.groupOsVm.setOnCheckedChangeListener { radioGroup, i ->
            val value = binding.root.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            binding.viewmodel!!.osChanged(value?.text.toString())
        }

        binding.startDateVmAanvraag.setOnDateChangedListener { datePicker, year, month, day ->
            try {
                val start = LocalDate.of(year, Month.of(Month.values()[month].ordinal + 1), day);
                binding.viewmodel!!.startDateChanged(start)
            } catch (e: Exception) {
                Timber.d(e.message);
                Timber.d(e.stackTraceToString())
            }
        }


        binding.endDateVmAanvraag.setOnDateChangedListener { datePicker, year, month, day ->
            try {
                val end = LocalDate.of(year, Month.of(Month.values()[month].ordinal + 1), day);
                binding.viewmodel!!.endDateChanged(end)
            }catch(e: Exception){
                Timber.d(e.message)
                Timber.d(e.stackTraceToString())
            }

        }


    }
}