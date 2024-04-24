package com.example.bbdu

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi

class PreviousYearPaper : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_previous_year_paper)

        // Find WebView and ProgressBar from the layout
        webView = findViewById(R.id.webView)
        progressBar = findViewById(R.id.progressBarPyq)

        // Enable JavaScript (if required)
        webView.settings.javaScriptEnabled = true

        // Load webpage URL
        val url = "https://bbdu.ac.in/question-paper-even-sem-21-22/"
        webView.loadUrl(url)

        // Set a WebViewClient to handle page navigation within the WebView
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url?.endsWith(".pdf") == true) {
                    // If URL ends with ".pdf", open it in a PDF viewer
                    openPdfInViewer(url)
                    return true
                }
                if (url != null) {
                    view?.loadUrl(url)
                }
                return true
            }
        }

        // Set a WebChromeClient to handle progress changes
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress < 100 && progressBar.visibility == ProgressBar.GONE) {
                    progressBar.visibility = ProgressBar.VISIBLE
                }
                progressBar.progress = newProgress
                if (newProgress == 100) {
                    progressBar.visibility = ProgressBar.GONE
                }
            }
        }
    }

    private fun openPdfInViewer(pdfUrl: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.parse(pdfUrl), "application/pdf")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // Handle exception if no PDF viewer app is installed
            Toast.makeText(this, "No PDF viewer installed,Install one from PlayStore", Toast.LENGTH_SHORT).show()
        }
    }
}
