package com.example.antrikom2.ui.antrian

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.antrikom2.databinding.FragmentAntrianBinding
import com.example.antrikom2.util.ModelAntrian
import com.example.antrikom2.util.SharedPref
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AntrianFragment : Fragment() {
   lateinit var binding: FragmentAntrianBinding //Deklarasi View Binding Antrian Fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentAntrianBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataArray = ArrayList<ModelAntrian>() //Deklarasi Array Model Antrian
        val myPreference = SharedPref(requireContext()) // Deklarasi Class SharedPref
        val date = SimpleDateFormat("ddMyyyy")
        val currentDateNow = date.format(Date()) // Get date sekarang

        // Penerapan RecyclerView
        GlobalScope.launch {
            binding.IDAntrianRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            FirebaseDatabase.getInstance().reference.child("SistemAntrian").child("Antrian")
                .child(currentDateNow).orderByChild("status").equalTo("Aktif")
                .addValueEventListener(
                    object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            dataArray.clear()
                            snapshot.children.forEach {
                                val modelAntrian =
                                    it.getValue(ModelAntrian::class.java) as ModelAntrian
                                if (modelAntrian.nim == myPreference.getData().NIM) {
                                    dataArray.add(modelAntrian)
                                }
                            }
                            binding.IDAntrianRecyclerView.adapter = AntrianAdapter(dataArray)
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })
        }
    }
}