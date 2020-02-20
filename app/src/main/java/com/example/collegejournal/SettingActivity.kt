package com.example.collegejournal

import android.annotation.SuppressLint
import android.app.Activity
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import java.io.IOException
import java.io.OutputStreamWriter
import android.R.id.edit
import android.content.Context


class SettingActivity : AppCompatActivity() {

    val dataColor:Array<String> = arrayOf("Сине-зелёный", "Красный","Туманная роза",    "Тёмно серый","Серый",   "Cвeтлo-зeлёный", "Светло-серый",   "Лимонно-кремовый")
    val Colors:Array<String> = arrayOf(   "#23CFBD",      "#FF0000","#FFE4E1",          "#2F4F4F",    "#808080", "#90EE90", "#D3D3D3",        "#FFFACD")
    var ColorT:Int = 0
    var myPrefereces: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        SettingCreate()
    }

    fun DrawSetting(position:Int){
        val spinnerColor = findViewById<View>(R.id.spinnerColor) as Spinner
        getSupportActionBar()!!.setBackgroundDrawable(ColorDrawable(Color.parseColor(Colors[position])))
        spinnerColor.setBackgroundColor(Color.parseColor(Colors[position]))
        getSupportActionBar()!!.setTitle(Html.fromHtml("<font color='#000000'>Оценки</font>"))
    }

    fun SettingCreate(){
        myPrefereces=this.getSharedPreferences("uses", Context.MODE_PRIVATE)
        ColorT= this!!.myPrefereces!!.getString("ColorPosition", "0").toInt()

        val spinnerColor = findViewById<View>(R.id.spinnerColor) as Spinner
        val adapterColor = ArrayAdapter(this, android.R.layout.simple_spinner_item, dataColor)
        adapterColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerColor.adapter = adapterColor
        spinnerColor.prompt = "Выберите цвет элементов"
        spinnerColor.setSelection(ColorT.toInt())
        spinnerColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @SuppressLint("ResourceType")
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                DrawSetting(position)
                val editor = myPrefereces?.edit()
                editor?.putString("ColorHEX", Colors[position])
                editor?.putString("ColorPosition", position.toString())
                editor?.apply()
                try {
                    val file = OutputStreamWriter(openFileOutput("ColorFile.txt", Activity.MODE_PRIVATE))
                    file.write ("$position")
                    file.flush ()
                    file.close ()
                } catch (e : IOException) {
                }
            }
            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
    }
}
