package com.example.antrikom2.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.antrikom2.R
import com.example.antrikom2.databinding.FragmentLoginBinding
import com.example.antrikom2.util.ModelAuth
import com.example.antrikom2.util.SharedPref
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    //Deklarasi view binding dari fragment_login
    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        //Hide Action Bar
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        //return value binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Event Handler Button ID_Login_BtnLogin
        binding.IDLoginBtnLogin.setOnClickListener {
            val nim = binding.IDLoginInputNIM.text.toString()
            val password = binding.IDLoginInputPassword.text.toString()

            //Menjalankan Background Task dengan lifecycleScope
            lifecycleScope.launch(Dispatchers.Main){
                login(nim, password)
            }
        }
    }

    private fun login(nim: String, password: String){
        //Mengakses data di FirebaseDatabase dengan path "SistemAntrian/Akun"
        FirebaseDatabase.getInstance().reference.child("SistemAntrian").child("Akun")
            .orderByChild("NIM").equalTo(nim).addValueEventListener(object : ValueEventListener {
                //Terdapat 2 fungsi:onDataChange dan onCancelled
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { dataSnapshot ->
                        //Deklarasi variabel sebagai ModelAuth
                        val data = dataSnapshot.getValue(ModelAuth::class.java) as ModelAuth
                        Log.d("LoginFragment", data.toString())

                        if (data.PASSWORD == password) {
                            Toast.makeText(
                                requireContext(),
                                "Success Login",
                                Toast.LENGTH_SHORT
                            ).show()

                            //Deklarasi SharedPref
                            val myPreference = SharedPref(requireContext())

                            //Input data NIM, PASSWORD dan Nama ke dalam ModelAuth
                            val data = ModelAuth(data.NIM,data.PASSWORD,data.Nama)

                            //Set data "data" ke dalam myPreference (SharedPref)
                            myPreference.setData(data)

                            //Navigasi dari fragment login ke dashboard
                            val navOption = NavOptions.Builder().setPopUpTo(R.id.dashboardFragment,true).build()
                            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment,null,navOption)
                        } else {
                            Toast.makeText(requireContext(), "Password Salah", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
    }
}