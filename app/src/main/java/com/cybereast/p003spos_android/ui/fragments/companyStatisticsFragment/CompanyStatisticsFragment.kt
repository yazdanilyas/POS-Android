package com.cybereast.p003spos_android.ui.fragments.companyStatisticsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cybereast.p003spos_android.base.BaseFragment
import com.cybereast.p003spos_android.databinding.CompanyStatisticsFragmentBinding
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry


class CompanyStatisticsFragment : BaseFragment() {

    companion object {
        fun newInstance() = CompanyStatisticsFragment()
    }

    private lateinit var mBinding: CompanyStatisticsFragmentBinding
    private lateinit var viewModel: CompanyStatisticsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = CompanyStatisticsFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CompanyStatisticsViewModel::class.java)
    }

    private fun setBarChart() {
        val entries = ArrayList<BarEntry>()

        entries.add(BarEntry(8f, 0f))
        entries.add(BarEntry(2f, 1f))
        entries.add(BarEntry(5f, 2f))

        val barDataSet = BarDataSet(entries, "Cells")

        val labels = ArrayList<String>()
        labels.add("A")
        labels.add("B")
        labels.add("C")

        val data = BarData(barDataSet, barDataSet, barDataSet)
        mBinding.idBarChart.data = data // set the data and list of lables into chart

        //mBinding.idBarChart.description  // set the description
        //barDataSet.setColors(ColorTemplate.COLORFUL_COLORS)
        //barDataSet.color = resources.getColor(R.color.colorAccent)
        mBinding.idBarChart.animateY(5000)
    }

}