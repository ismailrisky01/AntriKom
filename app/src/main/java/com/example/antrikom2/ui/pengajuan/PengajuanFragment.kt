package com.example.antrikom2.ui.pengajuan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.antrikom2.R
import com.example.antrikom2.databinding.FragmentPengajuanBinding


class PengajuanFragment : Fragment() {
    private var _binding: FragmentPengajuanBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPengajuanBinding.inflate(inflater,container,false)
        val dataDropdown = resources.getStringArray(R.array.list)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, dataDropdown)
        binding.autoCompleteTextView2.setAdapter(arrayAdapter)
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.IDPengajuanButtonAmbilAntrian.setOnClickListener {
            findNavController().navigate(R.id.action_pengajuanFragment_to_antrianFragment)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding =null
    }


}