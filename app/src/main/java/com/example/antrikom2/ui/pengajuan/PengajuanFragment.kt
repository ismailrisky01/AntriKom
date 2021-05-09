package com.example.antrikom2.ui.pengajuan

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.antrikom2.MainActivity
import com.example.antrikom2.R
import com.example.antrikom2.databinding.ActivityMainBinding
import com.example.antrikom2.databinding.FragmentPengajuanBinding

class PengajuanFragment : Fragment() {
    private var _binding: FragmentPengajuanBinding? = null
    private val binding get() = _binding!!
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    lateinit var contentView: RemoteViews
    private val appID = "ID"
    private val desc = "Desc"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPengajuanBinding.inflate(inflater, container, false)
        val dataDropdown = resources.getStringArray(R.array.list)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, dataDropdown)
        binding.autoCompleteTextView2.setAdapter(arrayAdapter)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.IDPengajuanButtonAmbilAntrian.setOnClickListener {
            findNavController().navigate(R.id.action_pengajuanFragment_to_antrianFragment)
            notif()
        }
    }



    fun notif() {

        notificationManager =
            getActivity()?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        contentView = RemoteViews(context?.packageName, R.layout.fragment_notifikasi)
        contentView.setTextViewText(R.id.ID_Notif_txtNotifTittle, "AntriKom")
        contentView.setTextViewText(R.id.ID_Notif_txtNotifDesc, "Kamu berhasil mengambil antrian!")
        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            12,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel =
                NotificationChannel(appID, desc, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GRAY
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(context, appID)
                .setContent(contentView)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        this.resources,
                        R.drawable.ic_launcher_foreground
                    )
                )
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
            notificationManager.notify(12, builder.build())
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}