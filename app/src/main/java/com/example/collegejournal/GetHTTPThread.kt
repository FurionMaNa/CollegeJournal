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
import android.widget.Toast
import java.lang.ref.WeakReference


class GetHTTPThread : AsyncTask<String, Void, String>() {

    override fun onPreExecute() {
        super.onPreExecute()

    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
    }

    override fun doInBackground(vararg strings: String): String {
        try {
            val urlString = strings[0]
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.setRequestProperty("Content-Type", "application/json")
            val inputStream = connection.inputStream
            val isr = InputStreamReader(inputStream)
            val bRead = BufferedReader(isr)
            val sBuilder = StringBuilder()
            var line: String=bRead.readLine()
            while (line != null) {
                sBuilder.append(line)
                try {
                    line = bRead.readLine()
                }catch (e: Exception){
                    break
                }
            }
            isr.close()
            inputStream.close()
            return sBuilder.toString()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null.toString()
    }

}
