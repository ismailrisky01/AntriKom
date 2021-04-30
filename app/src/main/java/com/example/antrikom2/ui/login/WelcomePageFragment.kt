package com.example.antrikom2.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.antrikom2.R
import com.example.antrikom2.databinding.FragmentWelcomePageBinding


class WelcomePageFragment : Fragment() {

lateinit var binding: FragmentWelcomePageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWelcomePageBinding.inflate(layoutInflater, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.IDWelcomeBtnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_welcomePageFragment_to_loginFragment)
        }
    }

}