package com.example.antrikom2

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.antrikom2.R
import com.example.antrikom2.databinding.FragmentScannerQrBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScannerQrFragment : Fragment(), ZXingScannerView.ResultHandler {

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requireActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(Manifest.permission.CAMERA),
                    112
                )
                return
            }
        }
        binding.qrCodeScanner.startCamera()
        binding.qrCodeScanner.setResultHandler(this)
    }

//    override fun handleResult(p0: Result?) {
//        if (p0 != null) {
//            Toast.makeText(requireContext(), p0.text, Toast.LENGTH_SHORT).show()
//            onResume()
//        }
//    }

    override fun onPause() {
        super.onPause()
        binding.qrCodeScanner.stopCamera()
    }

    override fun handleResult(p0: Result?) {
        TODO("Not yet implemented")
    }
}