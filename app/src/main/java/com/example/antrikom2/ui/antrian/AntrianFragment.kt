package com.example.antrikom2.ui.antrian

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.antrikom2.R
import com.example.antrikom2.databinding.FragmentAntrianBinding
import com.example.antrikom2.util.ModelAntrian
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AntrianFragment : Fragment() {
    private var _binding: FragmentAntrianBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAntrianBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataArray = ArrayList<ModelAntrian>()
        dataArray.add(ModelAntrian("Aktif","\t\n" +
                "205150209111007", "Ismail","Proposal","F1","12-02-2021"))
        dataArray.add(ModelAntrian("Aktif","\t\n" +
                "205150209111007", "Risky","Proposal","F2","12-02-2021"))


        binding.IDAntrianRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.IDAntrianRecyclerView.adapter = AntrianAdapter(dataArray)
    }

}