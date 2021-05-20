package com.example.antrikom2.ui.antrian

import android.os.Bundle
import android.service.controls.actions.ModeAction
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        binding.button.setOnClickListener {

        }

        arguments?.let { data ->
            nomorUrut(data.getString("nomorAntrian").toString(), currentDateNow)
            binding.IDDetailAtrianTxtNoAntrian.text = data.getString("nomorAntrian")
            binding.IDDetailAtrianTxtNama.text = data.getString("namaAntrian")
            binding.IDDetailAntrianBtnSelesai.setOnClickListener {

                                val bundle = Bundle()
                                bundle.putString("nomorAntrian", data.getString("nomorAntrian"))
                bundle.putString("idAntrian", data.getString("idAntrian"))

                                findNavController().navigate(
                                    R.id.action_detailAntrianFragment_to_scannerQrFragment,
                                    bundle
                                )
                            }
        }




    }

    fun nomorUrut(antrian: String, date: String) {
        FirebaseDatabase.getInstance().reference.child("SistemAntrian").child("Antrian")
            .child(date).orderByChild("status").equalTo("Aktif")
            .addValueEventListener(object : ValueEventListener {
                var totalAntrian = 0

                override fun onDataChange(snapshot: DataSnapshot) {

                    for (data in snapshot.children) {
                        totalAntrian = totalAntrian + 1
                        val user = data.getValue(ModelAntrian::class.java) as ModelAntrian
                        if (user.nomorAntrian == antrian) {
                            break
                        }
                    }
                    binding.IDDetailAntrianAntrianSkr.text =
                        totalAntrian.toString()
                    Log.d("DetailAntrian", totalAntrian.toString())
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}