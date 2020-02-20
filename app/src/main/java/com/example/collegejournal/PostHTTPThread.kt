package com.example.collegejournal

import android.os.AsyncTask
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class PostHTTPThread : AsyncTask<String, Void, String>() {

    override fun onPreExecute() {
        super.onPreExecute()

    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
    }

    override fun doInBackground(vararg strings: String): String? {
        try {
            val myURL = strings[0]
            val parammetrs = strings[1]
            var data: ByteArray? = null
            try {
                val url = URL(myURL)
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.doOutput = true
                conn.doInput = true
                conn.setRequestProperty("Content-Length", "" + Integer.toString(parammetrs.toByteArray().size))
                val os = conn.outputStream
                data = parammetrs.toByteArray(charset("UTF-8"))
                os.write(data)
                conn.connect()
                conn.responseCode
            } catch (e: MalformedURLException) {
            } catch (e: IOException) {
            } catch (e: Exception) {
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}
