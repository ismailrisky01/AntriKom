package com.example.antrikom2.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.antrikom2.MainActivity
import com.example.antrikom2.R
import com.example.antrikom2.databinding.FragmentDashboardBinding
import com.example.antrikom2.util.SharedPref


class DashboardFragment : Fragment() {
    lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        return binding.root
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myPreference = SharedPref(requireContext())
        if (myPreference.getData().NIM == "" && myPreference.getData().PASSWORD == "") {
            val navOption = NavOptions.Builder().setPopUpTo(R.id.loginFragment,true).build()
            findNavController().navigate(R.id.action_dashboardFragment_to_welcomePageFragment,null,navOption)
        }


        binding.IDDashboardImg.setOnClickListener {

        }
    }


}