package com.example.antrikom2.util

import androidx.recyclerview.widget.DiffUtil

class MyDiffUtil(private val oldList: List<ModelAntrian>, private val newList: List<ModelAntrian>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].time == newList[newItemPosition].time
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].status != newList[newItemPosition].status -> {
                false
            }
            oldList[oldItemPosition].nim != newList[newItemPosition].nim->{
                false
            }
            oldList[oldItemPosition].nama != newList[newItemPosition].nama->{
                false
            }
            oldList[oldItemPosition].jenisAntrian != newList[newItemPosition].jenisAntrian->{
                false
            }
            oldList[oldItemPosition].nomorAntrian != newList[newItemPosition].nomorAntrian->{
                false
            }oldList[oldItemPosition].time != newList[newItemPosition].time->{
                false
            }
            else->true

        }
    }
}