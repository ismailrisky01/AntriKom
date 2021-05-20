package com.example.antrikom2.ui.antrian

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.antrikom2.R
import com.example.antrikom2.databinding.FragmentAntrianBinding
import com.example.antrikom2.util.ModelAntrian
import com.example.antrikom2.util.SharedPref
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
    var dataArray = ArrayList<ModelAntrian>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backHandler()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAntrianBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataArray = ArrayList<ModelAntrian>()
        val myPreference = SharedPref(requireContext())
        val date = SimpleDateFormat("ddMyyyy")
        val currentDateNow = date.format(Date())
        binding.IDAntrianRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        FirebaseDatabase.getInstance().reference.child("SistemAntrian").child("Antrian")
            .child(currentDateNow).orderByChild("status").equalTo("Aktif").addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        dataArray.clear()
                        snapshot.children.forEach {
                            val modelAntrian = it.getValue(ModelAntrian::class.java) as ModelAntrian
                            if(modelAntrian.nim == myPreference.getData().NIM){
                                dataArray.add(modelAntrian)
                            }
                        }

                        binding.IDAntrianRecyclerView.adapter = AntrianAdapter(dataArray)
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
    }



    fun backHandler() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_antrianFragment_to_dashboardFragment)
                }
            }
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

}