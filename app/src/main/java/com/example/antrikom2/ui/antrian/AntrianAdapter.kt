package com.example.antrikom2.ui.antrian

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.antrikom2.R
import com.example.antrikom2.databinding.ItemAntrianBinding
import com.example.antrikom2.util.ModelAntrian

class AntrianAdapter(val data:ArrayList<ModelAntrian>) : RecyclerView.Adapter<AntrianAdapter.ViewHolder>() { //Delarasi RecyclerView
    //Fragment LifeCycle onCreateView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAntrianBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    //Mengisi data ke setiap list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val datas = data[position]
        holder.binding.IDItemNomorAntrian.text = datas.nomorAntrian
        holder.binding.IDItemNamaKegiatan.text = datas.jenisAntrian
        holder.binding.IDItemNamaMahasiswa.text = datas.nama
        holder.binding.IDItemTanggal.text = datas.time

        holder.data(datas)
    }

    //Mendapatkan jumlah data
    override fun getItemCount(): Int {
        return data.size
    }

    // Setting Class View Holder
    class ViewHolder(val binding: ItemAntrianBinding) : RecyclerView.ViewHolder(binding.root) {
        val bundle = Bundle() //Deklarasi Bundle

        //menampung kiriman modelAntrian
        fun data(modelAntrian: ModelAntrian) {
            bundle.putString("nomorAntrian", modelAntrian.nomorAntrian)
            bundle.putString("namaAntrian", modelAntrian.nama)
            bundle.putString("idAntrian", modelAntrian.idAntrian)
        }

        init {
            //Pindah halaman dan kirim data
            binding.IDItemCardView.setOnClickListener {
                it.findNavController()
                    .navigate(R.id.action_antrianFragment_to_detailAntrianFragment, bundle)
            }
        }
    }
}