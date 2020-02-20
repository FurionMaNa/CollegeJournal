package com.example.collegejournal

import android.os.AsyncTask
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import android.R
import android.app.PendingIntent.getActivity
import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import java.lang.ref.WeakReference


class TestServer : AsyncTask<String, Void, Boolean>() {

    override fun doInBackground(vararg strings: String): Boolean {
        return isURLReachable(strings[0])
    }

    fun isURLReachable(ip:String): Boolean {
        try {
            val url = URL("http://"+ip)   // Change to "http://google.com" for www  test.
            val urlc = url.openConnection() as HttpURLConnection
            urlc.setConnectTimeout(10 * 1000)          // 10 s.
            urlc.connect()
            if (urlc.getResponseCode() === 200) {        // 200 = "OK" code (http connection is fine).
                return true
            } else {
                return false
            }
        } catch (e1: MalformedURLException) {
            return false
        } catch (e: IOException) {
            return false
        }
    }
}
