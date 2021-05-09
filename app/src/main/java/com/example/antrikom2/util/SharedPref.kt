package com.example.antrikom2.util

import android.content.Context

class SharedPref(context: Context) {
    val IDShared = "Data"
    val NIM_KEY = "nim_data"
    val PASSWORD_KEY = "password_data"
    val NAMA_KEY = "nama_data"

    val preference_id = context.getSharedPreferences(IDShared, Context.MODE_PRIVATE)

    fun setData(modelAuth: ModelAuth){
        val data = preference_id.edit()
        data.putString(NIM_KEY, modelAuth.NIM)
        data.putString(NAMA_KEY, modelAuth.Nama)
        data.putString(PASSWORD_KEY, modelAuth.PASSWORD)
        data.apply()
    }

    fun getData() : ModelAuth{
        val NIM = preference_id.getString(NIM_KEY, "").toString()
        val Nama = preference_id.getString(NAMA_KEY, "").toString()
        val PASSWORD = preference_id.getString(PASSWORD_KEY, "").toString()
        val data = ModelAuth(NIM, PASSWORD, Nama)
        return data
    }
}