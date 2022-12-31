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
import com.hogent.android.database.DatabaseImp
import com.hogent.android.databinding.FragmentVmlistBinding
import com.hogent.android.util.AuthenticationManager
import com.hogent.android.util.closeKeyboardOnTouch
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

        val db = DatabaseImp.getInstance(application);
        val viewModelFactory = VMListViewModelFactory(db);

        viewModel = ViewModelProvider(this, viewModelFactory)[(VMListViewModel::class.java)];

        binding.overviewViewModel = viewModel
        binding.lifecycleOwner = this
        binding.root.closeKeyboardOnTouch()

        val recyclerView: RecyclerView = binding.root.findViewById(R.id.project_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this.context);

        Timber.i("VMListFragment:")
        Timber.i(viewModel.virtualmachine.value.toString())

        viewModel.projecten.observe(viewLifecycleOwner, Observer {
            recyclerView.adapter = ProjectListAdapter(
                it,
                viewModel.virtualmachine.value,
                this.context);

        })


        return binding.root
    }
}



//kunt je deze klasse nie in u xml zetten? lijkt zeer onoverzichtelijk en moeilijk
//om hier iets bij te kunnen visualiseren / aanpassingen aandoen in de toekomst.
//kijk naar mijn klantprofiel voor een voorbeeld mss, je kunt daar met invisible werken.

    /*private fun setupView() {
        if (viewModel.projecten != null) {
            viewModel.projecten.value?.forEach { i ->
                Timber.i(i.toString())
                var textview = TextView(activity)
                textview.setPadding(10)
                textview.setBackground(getResources().getDrawable(R.drawable.textview_border))
                textview.setText(i.name.toString())
                textview.setTypeface(null, Typeface.BOLD)
                textview.setTextSize(20F)

                var tablelayout = TableLayout(activity)
                tablelayout.setPadding(5)
                tablelayout.tooltipText = textview.text
                var toptableRow = TableRow(activity)
                toptableRow.setBackground(getResources().getDrawable(R.drawable.textview_border))
                val toprowcolumn1 = TextView(activity)
                toprowcolumn1.setText("Status")
                toprowcolumn1.setPadding(20)
                toprowcolumn1.setTextSize(17F)
                val toprowcolumn2 = TextView(activity)
                toprowcolumn2.setText("Virtual Machine")
                toprowcolumn2.setPadding(20)
                toprowcolumn2.setTextSize(17F)
                val toprowcolumn3 = TextView(activity)
                toprowcolumn3.setText("Klant")
                toprowcolumn3.setPadding(17)
                toprowcolumn3.setTextSize(17F)
                toptableRow.addView(toprowcolumn1)
                toptableRow.addView(toprowcolumn2)
                toptableRow.addView(toprowcolumn3)
                tablelayout.addView(toptableRow)

                viewModel.virtualmachine.value?.forEach { j ->
                    if (j.project_id == i.id) {
                        Timber.i(j.toString())
                        var tableRow = TableRow(activity)
                        tableRow.setBackground(getResources().getDrawable(R.drawable.textview_border))
                        tableRow.setPadding(5)
                        val tv1 = TextView(activity)
                        tv1.setPadding(20)
                        tv1.setTextSize(17F)
                        tv1.setText(j.status.toString())
                        val tv2 = TextView(activity)
                        tv2.setPadding(20)
                        tv2.setTextSize(17F)
                        tv2.setText(j.name.toString())
                        val tv3 = TextView(activity)
                        tv3.setPadding(20)
                        tv3.setTextSize(17F)
                        tv3.setText(AuthenticationManager.getInstance(application).klant.value!!.email.toString())
                                tableRow . addView (tv1)
                                tableRow . addView (tv2)
                                tableRow . addView (tv3)
                                tablelayout . addView (tableRow)
                                textview . setOnClickListener () {
                            if (tablelayout.getVisibility() == View.GONE) {
                                tablelayout.setVisibility(View.VISIBLE)
                            } else {
                                tablelayout.setVisibility(View.GONE)
                            }
                        }
                                tablelayout . setVisibility (View.GONE)

                    }
                    binding.projectRecyclerview.addView(textview)
                    binding.projectRecyclerview.addView(tablelayout)
                }
            }
        } else {

            var textViewNoProject = TextView(activity)
            textViewNoProject.setText("No Projects")
            textViewNoProject.setBackground(getResources().getDrawable(R.drawable.textview_border))

            binding.projectRecyclerview.addView(textViewNoProject)
        }


    }*/
