package com.example.antrikom2.ui.antrian

import android.os.Bundle
import android.service.controls.actions.ModeAction
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.antrikom2.R
import com.example.antrikom2.databinding.FragmentDetailAntrianBinding
import com.example.antrikom2.util.ModelAntrian
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*


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
        val date = SimpleDateFormat("ddMyyyy")
        val currentDateNow = date.format(Date())

        arguments?.let {data->
            FirebaseDatabase.getInstance().reference.child("SistemAntrian").child("Antrian").child(currentDateNow)
                .orderByChild("nomorAntrian").equalTo(data.getString("nomorAntrian")).addValueEventListener(
                    object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var key =""
                            snapshot.children.forEach {
                                val modeAntrian =
                                    it.getValue(ModelAntrian::class.java) as ModelAntrian
                                binding.IDDetailAtrianTxtNoAntrian.text = modeAntrian.nomorAntrian
                                binding.IDDetailAtrianTxtNama.text = modeAntrian.nama

                                key = it.key.toString()
                            }
                            binding.IDDetailAntrianBtnSelesai.setOnClickListener {
                                Toast.makeText(requireContext(), "key = "+key, Toast.LENGTH_SHORT).show()
                                val bundle = Bundle()
                                bundle.putString("nomorAntrian", data.getString("nomorAntrian"))
                                bundle.putString("keyAntrian", key)

                                findNavController().navigate(
                                    R.id.action_detailAntrianFragment_to_scannerQrFragment,
                                    bundle
                                )
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })

        }

        FirebaseDatabase.getInstance().reference.child("SistemAntrian").child("Antrian").child(currentDateNow)
            .orderByChild("status").equalTo("Aktif").addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {

                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

    }
}