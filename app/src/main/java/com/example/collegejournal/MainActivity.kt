package com.example.collegejournal

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.net.ConnectivityManager
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class MainActivity : AppCompatActivity() {

    var myPrefereces: SharedPreferences? = null
    var ColorT:String=""
    val Colors:Array<String> = arrayOf(   "#23CFBD",      "#FF0000","#FFE4E1",          "#2F4F4F",    "#808080", "#90EE90", "#D3D3D3",        "#FFFACD")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (findViewById<View>(R.id.btnCon)as Button).setOnClickListener {
            if(TestServer().execute((findViewById<View>(R.id.editIpText)as EditText).getText().toString()).get()) {
                val intent = Intent(this, BookActivity::class.java)
                intent.putExtra("ip", (findViewById<View>(R.id.editIpText) as EditText).getText().toString())
                startActivity(intent)
            }else{
                Toast.makeText(applicationContext,"Такого сервера нет",Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onStart() {
        super.onStart()
        ColorT="0"
        try {
            val file = InputStreamReader(openFileInput("ColorFile.txt"))
            val br = BufferedReader(file)
            var line = br.readLine()
            val all = StringBuilder()
            while (line != null) {
                all.append(line + "\n")
                line = br.readLine()
            }
            br.close()
            file.close()
            var i:Int=0
            ColorT=""
            while (i < all.length-1) {
                ColorT+=all[i].toString()
                i++
            }
            DrawMenu(ColorT.toInt())
        }catch (e: IOException) {
        }
    }

    fun DrawMenu(position:Int){
        getSupportActionBar()!!.setBackgroundDrawable(ColorDrawable(Color.parseColor(Colors[position])))
        getSupportActionBar()!!.setTitle(Html.fromHtml("<font color='#000000'>Оценки</font>"))
        val btnC=findViewById<View>(R.id.btnCon)as Button
        btnC!!.setBackgroundColor(Color.parseColor(Colors[position]))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.setting_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when(item.itemId){
                R.id.Setting->Setting()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun Setting(){
        val intent= Intent(this,SettingActivity::class.java)
        startActivity(intent)
    }
}
