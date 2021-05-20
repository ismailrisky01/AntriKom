package com.example.antrikom2.ui.antrian

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.antrikom2.R
import com.example.antrikom2.databinding.ItemAntrianBinding
import com.example.antrikom2.util.ModelAntrian

class AntrianAdapter(val data:ArrayList<ModelAntrian>) :
    RecyclerView.Adapter<AntrianAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAntrianBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val datas = data[position]
        holder.binding.IDItemNomorAntrian.text = datas.nomorAntrian
        holder.binding.IDItemNamaKegiatan.text = datas.jenisAntrian
        holder.binding.IDItemNamaMahasiswa.text = datas.nama
        holder.binding.IDItemTanggal.text = datas.time

        holder.data(datas)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(val binding: ItemAntrianBinding) : RecyclerView.ViewHolder(binding.root) {
        val bundle = Bundle()

        fun data(modelAntrian: ModelAntrian) {
            bundle.putString("nomorAntrian", modelAntrian.nomorAntrian)
            bundle.putString("namaAntrian", modelAntrian.nama)
            bundle.putString("idAntrian", modelAntrian.idAntrian)
        }

        init {
            binding.IDItemCardView.setOnClickListener {
                it.findNavController()
                    .navigate(R.id.action_antrianFragment_to_detailAntrianFragment, bundle)
            }
        }
    }
}