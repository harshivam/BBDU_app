package com.example.bbdu

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.bbdu.databinding.FragmentHomeBinding


class Home : Fragment() {

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.LayoutNotifications.setOnClickListener {

            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            val intent = Intent(requireContext(), Notifications::class.java)
            startActivity(intent)

        }
        binding.OfficialWebLayout.setOnClickListener {
            val url = "https://bbdu.ac.in"
            webOpener(url, view)
        }

        binding.resultLayout.setOnClickListener {
            val url = "https://bbdu.ac.in/result/"

            webOpener(url, view)
        }

        binding.previousYearLayout.setOnClickListener {
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            val intent = Intent(requireContext(), PreviousYearPaper::class.java)
            startActivity(intent)
        }
        binding.dashboardLottie.setOnClickListener {
            val intent = Intent(requireContext(), Dashboard::class.java)
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            startActivity(intent)
        }


    }

    private fun webOpener(url: String, view: View) {
        view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
        val i = Intent(Intent.ACTION_VIEW)
        i.setData(Uri.parse(url))
        startActivity(i)
    }

}