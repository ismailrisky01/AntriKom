package com.example.antrikom2.ui.antrian.detailAntrian

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.antrikom2.R
import com.example.antrikom2.databinding.FragmentScannerQrBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.text.SimpleDateFormat
import java.util.*
class ScannerQrFragment : Fragment(), ZXingScannerView.ResultHandler {
    //ngebuat binding view
    lateinit var binding: FragmentScannerQrBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentScannerQrBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setScannerProperties()
        //toast no antrian
        arguments?.let {
            Toast.makeText(requireContext(), "datas" + it.getString("nomorAntrian"), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setScannerProperties() {
        binding.qrCodeScanner.setFormats(listOf(BarcodeFormat.QR_CODE))
        binding.qrCodeScanner.setAutoFocus(true)
        binding.qrCodeScanner.setLaserColor(R.color.design_default_color_error)
        binding.qrCodeScanner.setMaskColor(R.color.design_default_color_primary)
        if (Build.MANUFACTURER.equals("REALME", ignoreCase = true))
            binding.qrCodeScanner.setAspectTolerance(0.5f)
    }

    override fun onResume() {
        super.onResume()
        //permission kamera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requireActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(Manifest.permission.CAMERA),
                    112
                )
                return
            }
        }
        //kamera nyala
        binding.qrCodeScanner.startCamera()
        binding.qrCodeScanner.setResultHandler(this)
    }

    override fun handleResult(p0: Result?) {
        //kalo ga null dan textnya itu filkom dia akan run
        if (p0 != null) {
            if (p0.text == "Filkom"){
                val date = SimpleDateFormat("ddMyyyy")
                val currentDateNow = date.format(Date())
                //ngubah jadi selesai dan balik ke dasboard
                arguments?.let {
                    FirebaseDatabase.getInstance().reference.child("SistemAntrian").child("Antrian")
                        .child(currentDateNow).child(it.getString("idAntrian").toString()).child("status")
                        .setValue("Selesai").addOnSuccessListener {
                        val navOption = NavOptions.Builder().setPopUpTo(R.id.dashboardFragment,true).build()
                        findNavController().navigate(R.id.action_scannerQrFragment_to_antrianFragment)
                    }
                }
                //salah akan ada toast
            }else{
                Toast.makeText(requireContext(), "Qr Code Salah", Toast.LENGTH_SHORT).show()
            }
            onResume()
        }
    }
    // pause dia bakal stop kamera
    override fun onPause() {
        super.onPause()
        binding.qrCodeScanner.stopCamera()
    }
}