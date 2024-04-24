package com.example.bbdu

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.bbdu.databinding.FragmentContactBinding

class Contact : Fragment() {
    companion object {
        private const val CALL_PHONE_PERMISSION_REQUEST_CODE = 100
    }

    private lateinit var binding: FragmentContactBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.expandable.setOnClickListener {
            toggleButtonsVisibility(binding.buttonsLayout)
        }
        binding.expandable2.setOnClickListener {
            toggleButtonsVisibility(binding.buttonsLayout2)
        }


        binding.button1.setOnClickListener {
            val num1 = ""
            showDialogue(num1)
        }
        binding.button2.setOnClickListener {
            val num2 = ""
            showDialogue(num2)
        }
        binding.button3.setOnClickListener {
            val num3 = ""
            showDialogue(num3)
        }


    }


    private fun toggleButtonsVisibility(view: View) {
        view.visibility =
            if (view.visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }

    private fun showDialogue(phoneNumber: String) {
        context?.let {
            AlertDialog.Builder(it)
                .setMessage("Do you want to make a call to $phoneNumber?")
                .setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                    makeCall(phoneNumber)
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }

    private fun makeCall(phoneNumber: String) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted, request the permission
            requestCallPermission()
        } else {
            initiateCall(phoneNumber)
        }
    }

    private fun requestCallPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.CALL_PHONE),
            CALL_PHONE_PERMISSION_REQUEST_CODE
        )
    }

    private fun initiateCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CALL_PHONE_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, make the call
                // You can implement additional logic here if needed
            }
        }
    }
}
