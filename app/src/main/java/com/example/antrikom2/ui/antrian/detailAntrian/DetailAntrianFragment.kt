package com.example.antrikom2.ui.antrian.detailAntrian

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.antrikom2.R
import com.example.antrikom2.databinding.FragmentDetailAntrianBinding
import com.example.antrikom2.util.ModelAntrian
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*


class DetailAntrianFragment : Fragment() {
    lateinit var binding: FragmentDetailAntrianBinding  // deklarasi view binding
    // 2 lifecycle frag yaitu oncreateview dan onviewcreated
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
// membuat inflate di fragment
        binding = FragmentDetailAntrianBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val date = SimpleDateFormat("ddMyyyy")
        val currentDateNow = date.format(Date())

// mengambil argument di antrian adapter
        arguments?.let { data ->
            nomorUrut(data.getString("nomorAntrian").toString(), currentDateNow)
            binding.IDDetailAtrianTxtNoAntrian.text = data.getString("nomorAntrian")
            binding.IDDetailAtrianTxtNama.text = data.getString("namaAntrian")
            binding.IDDetailAntrianBtnSelesai.setOnClickListener {
                //deklarasiin bundle buat nyimpen
                val bundle = Bundle()
                bundle.putString("nomorAntrian", data.getString("nomorAntrian"))
                bundle.putString("idAntrian", data.getString("idAntrian"))
                findNavController().navigate(
                    R.id.action_detailAntrianFragment_to_scannerQrFragment,
                    bundle
                )
            }
            // setup buat button batal
            binding.IDDetailAntrianBtnBatal.setOnClickListener {
                MaterialAlertDialogBuilder(requireContext()).setIcon(R.drawable.logo)
                    .setMessage(resources.getString(R.string.teks_konfirmasi))
                    // buat kalo tidak dia ga bakal ngelakuin apa apa
                    .setNegativeButton(resources.getString(R.string.tidak)){
                            dialog, which ->
                    }
                    // kalo iya dia bakalan ngubah value
                    .setPositiveButton(resources.getString(R.string.ya)){
                            dialog, which -> val idAntrian = data.getString("idAntrian")
                        FirebaseDatabase.getInstance().reference
                            .child("SistemAntrian/Antrian/$currentDateNow/$idAntrian/status").setValue("Batal")
                            .addOnSuccessListener { findNavController().navigate(R.id.action_detailAntrianFragment_to_antrianFragment) }
                    }.show()
            }
        }


    }

    // fungsi nomer urut
    fun nomorUrut(antrian: String, date: String) {
        // tempat firebasenya dimana jadi dia hitung klo yang equal aktif aja
        FirebaseDatabase.getInstance().reference.child("SistemAntrian").child("Antrian")
            .child(date).orderByChild("status").equalTo("Aktif")
            .addValueEventListener(object : ValueEventListener {// add value itu realtime
            var totalAntrian = 0

                //perubahan datanya
                override fun onDataChange(snapshot: DataSnapshot) {
                    totalAntrian = 0
                    // akan nambah 1 tiap childrennya
                    for (data in snapshot.children) {
                        totalAntrian += 1
                        //ambil value dari model antrian (nama, nim, dll)
                        val user = data.getValue(ModelAntrian::class.java) as ModelAntrian
                        if (user.nomorAntrian == antrian) {
                            break
                        }
                    }
                    //jadiin string no berapa antriannya
                    totalAntrian -= 1
                    binding.IDDetailAntrianAntrianSkr.text = totalAntrian.toString()
                    Log.d("DetailAntrian", totalAntrian.toString())
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}