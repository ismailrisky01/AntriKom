package com.example.antrikom2.ui.antrian

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.antrikom2.R
import com.example.antrikom2.databinding.FragmentDetailAntrianBinding


class DetailAntrianFragment : Fragment() {
    lateinit var binding: FragmentDetailAntrianBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailAntrianBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.IDDetailAntrianBtnSelesai.setOnClickListener {
            findNavController().navigate(R.id.action_detailAntrianFragment_to_scannerQrFragment)
        }
    }
}