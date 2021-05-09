package com.example.antrikom2.ui.antrian

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.antrikom2.R
import com.example.antrikom2.databinding.ItemAntrianBinding
import com.example.antrikom2.util.ModelAntrian

class AntrianAdapter(val data: ArrayList<ModelAntrian>) :
    RecyclerView.Adapter<AntrianAdapter.ViewHolder>() {
    lateinit var binding: ItemAntrianBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemAntrianBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val datas = data[position]
        binding.IDItemNomorAntrian.text = datas.nomorAntrian
        binding.IDItemNamaKegiatan.text = datas.jenisAntrian
        binding.IDItemNamaMahasiswa.text = datas.nama
        binding.IDItemTanggal.text = datas.time
        val posisi = position + 1
        binding.IDItemWaktu.text = posisi.toString()

        holder.data(datas)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(binding: ItemAntrianBinding) : RecyclerView.ViewHolder(binding.root) {
        val bundle = Bundle()
        fun data(modelAntrian: ModelAntrian) {
            bundle.putString("nomorAntrian", modelAntrian.nomorAntrian)
        }

        init {
            binding.IDItemCardView.setOnClickListener {
                it.findNavController()
                    .navigate(R.id.action_antrianFragment_to_detailAntrianFragment, bundle)
            }
        }
    }
}