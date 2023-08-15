package com.hogent.android.ui.vms.overview

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hogent.android.R
import com.hogent.android.data.repositories.VmOverviewRepository
import com.hogent.android.databinding.FragmentVmlistBinding
import com.hogent.android.util.closeKeyboardOnTouch
import kotlinx.coroutines.runBlocking
import timber.log.Timber


class VMListFragment : Fragment() {

    private lateinit var viewModel: VMListViewModel
    private lateinit var application: Application

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentVmlistBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_vmlist, container, false);
        application = requireNotNull(this.activity).application

        val viewModelFactory = VMListViewModelFactory(VmOverviewRepository());

        viewModel = ViewModelProvider(this, viewModelFactory)[(VMListViewModel::class.java)];

        binding.overviewViewModel = viewModel
        binding.lifecycleOwner = this
        binding.root.closeKeyboardOnTouch()

        runBlocking {
            viewModel.refreshProjects()
        }

        val recyclerView: RecyclerView = binding.root.findViewById(R.id.project_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this.context);

        Timber.i("VMListFragment:")
        Timber.i("Total VMS %d", viewModel.virtualmachine.value?.size?: 0)

        viewModel.projecten.observe(viewLifecycleOwner, Observer {
            recyclerView.adapter = ProjectListAdapter(
                it,
                viewModel.virtualmachine.value,
                this.context);

        })

        return binding.root
    }

    override fun onResume() {
        super.onResume();
        runBlocking {
            viewModel.refreshProjects();
        }
    }
}