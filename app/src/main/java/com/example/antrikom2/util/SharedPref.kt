package com.example.antrikom2.util

import android.content.Context

class SharedPref(context: Context) {
    val USERNIM = "nim"
    val USERNIM_KEY = "nim_data"


    val PASSWORD ="password"
    val PASSWORD_KEY = "password_data"

    val NAMA ="nama"
    val NAMA_KEY = "nama_data"


    val preference_username = context.getSharedPreferences(USERNIM,Context.MODE_PRIVATE)

    val preference_password = context.getSharedPreferences(PASSWORD,Context.MODE_PRIVATE)

    val preference_nama = context.getSharedPreferences(NAMA,Context.MODE_PRIVATE)


    fun getNIM():String?{
        return preference_username.getString(USERNIM_KEY,"")
    }

    fun setNIM(dataInput:String){
        val data= preference_username.edit()
        data.putString(USERNIM_KEY, dataInput)
        data.apply()
    }

    fun getPassword():String?{
        return preference_password.getString(PASSWORD_KEY,"")
    }
    fun setPassword(dataInput:String){
        val data= preference_password.edit()
        data.putString(PASSWORD_KEY, dataInput)
        data.apply()
    }

    fun getNama():String?{
        return preference_nama.getString(NAMA_KEY,"")
    }
    fun setNama(dataInput:String){
        val data= preference_nama.edit()
        data.putString(NAMA_KEY, dataInput)
        data.apply()
    }


}