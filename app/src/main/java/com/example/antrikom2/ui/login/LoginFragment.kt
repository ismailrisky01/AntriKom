package com.example.antrikom2.ui.login

import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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


class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.IDLoginBtnLogin.setOnClickListener {
            val nim = binding.IDLoginInputNIM.text.toString()
            val password = binding.IDLoginInputPassword.text.toString()
            FirebaseDatabase.getInstance().reference.child("SistemAntrian").child("Akun")
                .orderByChild("NIM").equalTo(nim).addValueEventListener(
                    object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            snapshot.children.forEach { dataSnapshot ->
                                val data = dataSnapshot.getValue(ModelAuth::class.java) as ModelAuth
                                Log.d("LoginFragment", data.toString())

                                if (data.PASSWORD == password) {
                                    Toast.makeText(
                                        requireContext(),
                                        "Success Login",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val myPreference = SharedPref(requireContext())
                                    myPreference.setNIM(data.NIM)
                                    myPreference.setPassword(data.PASSWORD)
                                    myPreference.setNama(data.Nama)

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

}