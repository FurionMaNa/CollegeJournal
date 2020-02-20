package com.example.collegejournal

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Html
import android.view.*
import android.widget.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_book.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.MalformedURLException

class BookActivity : AppCompatActivity() {

    var SelectGroup:Int=0
    var SelectSubject:Int=0
    var SelectDate:Int=0
    var NumDate:Int=0
    var IP:String = ""
    var SubjectData= ArrayList<ClassSubject>()
    var GroupsData= ArrayList<ClassGroup>()
    var DatesData=ArrayList<ClassLp>()
    var Journal=ArrayList<ClassJournal>()
    var MarksData=ArrayList<ClassMark>()
    var AttendacesData=ArrayList<ClassAttendaces>()
    var LprArray=ArrayList<Int>()
    var Num=ArrayList<Int>()
    var Dat=ArrayList<String>()
    var MarksIUD=ArrayList<InstalUpdateDelete>()
    var ColorT:String="0"
    var SelectBook:Int=0
    var InsertInto:Boolean=true
    var Buf:Int=-1
    var Text_Color:String=""
    val Colors:Array<String> = arrayOf(   "#23CFBD",      "#FF0000","#FFE4E1",          "#2F4F4F",    "#808080", "#90EE90", "#D3D3D3",        "#FFFACD")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
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
        }catch (e: IOException) {
        }
        DrawBook(ColorT.toInt())
        MarkAttendacesCreate()
    }

    fun DrawBook(position:Int){
        val btnBack=findViewById<View>(R.id.button2) as Button
        val btnNext=findViewById<View>(R.id.button) as Button
        val spinnerGroup = findViewById<View>(R.id.spinnerGroups) as Spinner
        val spinnerSubject = findViewById<View>(R.id.spinnerSubjects) as Spinner
        getSupportActionBar()!!.setBackgroundDrawable(ColorDrawable(Color.parseColor(Colors[position])))
        btnBack.setBackgroundColor(Color.parseColor(Colors[position]))
        btnNext.setBackgroundColor(Color.parseColor(Colors[position]))
        spinnerGroup.setBackgroundColor(Color.parseColor(Colors[position]))
        spinnerSubject.setBackgroundColor(Color.parseColor(Colors[position]))
        getSupportActionBar()!!.setTitle(Html.fromHtml("<font color='#000000'>Оценки</font>"))
        Text_Color="#000000"
        btnNext.setTextColor(Color.parseColor(Text_Color))
        btnBack.setTextColor(Color.parseColor(Text_Color))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.popup_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when(item.itemId){
                R.id.Save->Save()
                R.id.Refresh->Refresh()
                R.id.Setting->Setting()
                R.id.Exit->finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun Save(){
        var i:Int=0
        val gson = Gson()
        if(TestServer().execute(IP).get()) {
            if (MarksIUD.count() > 0) {
                Toast.makeText(applicationContext, "Данные сохраняются", Toast.LENGTH_SHORT).show()
            }
            for (i in 0 until MarksIUD.count()) {
                when (MarksIUD[i].getIUD()) {
                    "INS" -> PostHTTPThread().execute("http://$IP/WEB_SERVER/insertMark.php", gson.toJson(MarksIUD[i]))
                    "UPD" -> PostHTTPThread().execute("http://$IP/WEB_SERVER/updateMark.php", gson.toJson(MarksIUD[i]))
                    "DEL" -> PostHTTPThread().execute("http://$IP/Web_server/deleteMark.php", gson.toJson(MarksIUD[i]))
                    "INA" -> PostHTTPThread().execute("http://$IP/Web_server/insertAttendances.php", gson.toJson(MarksIUD[i]))
                    "UPA" -> PostHTTPThread().execute("http://$IP/Web_server/updateAttendances.php", gson.toJson(MarksIUD[i]))
                    "DEA" -> PostHTTPThread().execute("http://$IP/Web_server/deleteAttendances.php", gson.toJson(MarksIUD[i]))
                }
            }
            if (MarksIUD.count() > 0) {
                Toast.makeText(applicationContext, "Данные сохранены", Toast.LENGTH_SHORT).show()
            }
            MarksIUD.clear()
        }else{
            Toast.makeText(applicationContext,"Пропало подключение к серверу, проверьте соединение с интернетом",Toast.LENGTH_SHORT).show()
        }
    }

    fun Refresh(){
        Buf=SelectDate
        MarkAttendacesCreate()
    }

    fun Setting(){
        val intent= Intent(this,SettingActivity::class.java)
        startActivity(intent)
    }

    fun MarkAttendacesCreate(){
        Toast.makeText(applicationContext, "Загрузка данных", Toast.LENGTH_SHORT).show()
        IP=this.intent.getStringExtra("ip")
        var Mark:String=""
        try {
            Mark = GetHTTPThread().execute("http://$IP/Web_server/Mark.php").get()
            MarksData= this!!.deserializeMark(Mark)!!
            var Attendaces:String=""
            Attendaces=GetHTTPThread().execute("http://$IP/Web_server/ATTENDACES.php").get()
            AttendacesData= this!!.deserializeAttendaces(Attendaces)!!
            GroupCreate()
        }catch (e:Exception){
            Toast.makeText(applicationContext,"Пропало подключение к серверу, проверьте соединение с интернетом",Toast.LENGTH_SHORT).show()

        }
    }

    fun GroupCreate(){
        var Groups:String=""
        try {
            Groups=GetHTTPThread().execute("http://$IP/Web_server/Groups.php").get()
            GroupsData.clear()
            GroupsData = this!!.deserializeGroup(Groups)!!
            var Data= emptyArray<String>();
            for (i in 0 until GroupsData.count()) {
                Data+= GroupsData[i].GetName()
            }
            val adapterGroups = ArrayAdapter(this, android.R.layout.simple_spinner_item, Data)
            adapterGroups.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            val spinnerGroups = findViewById<View>(R.id.spinnerGroups) as Spinner
            spinnerGroups.adapter = adapterGroups
            spinnerGroups.prompt = "Выбор группы"
            if(SelectGroup<Data.count()){
                spinnerGroups.setSelection(SelectGroup)
            }else {
                spinnerGroups.setSelection(0)
            }
            spinnerGroups.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    SelectGroup = position
                    Save()
                    BuildSubjects()
                }
                override fun onNothingSelected(arg0: AdapterView<*>) {}
            }
        }catch (e: Exception){
            Toast.makeText(applicationContext,"Пропало подключение к серверу, проверьте соединение с интернетом",Toast.LENGTH_SHORT).show()
        }
    }

    fun BuildSubjects(){
        var Subjects:String="";
        try{
            Subjects=GetHTTPThread().execute("http://$IP/Web_server/Subject.php?id=${GroupsData[SelectGroup].GetGroId()}").get()
            SubjectData.clear()
            SubjectData= this.deserializeSubject(Subjects)!!
            var Data= emptyArray<String>()
            for (i in 0 until SubjectData.count()) {
                Data+= SubjectData[i].GetName()
            }
            val adapterSubject = ArrayAdapter(this, android.R.layout.simple_spinner_item, Data)
            adapterSubject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            val spinnerSubject = findViewById<View>(R.id.spinnerSubjects) as Spinner
            spinnerSubject.adapter = adapterSubject
            spinnerSubject.prompt = "Выбор предмета"
            if(SelectGroup<Data.count()){
                spinnerSubject.setSelection(SelectSubject)
            }else {
                spinnerSubject.setSelection(0)
            }
            spinnerSubject.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    SelectSubject = position
                    Save()
                    BuildDates()
                }
                override fun onNothingSelected(arg0: AdapterView<*>) {}
            }
        }catch (e:Exception){
            Toast.makeText(applicationContext,"Пропало подключение к серверу, проверьте соединение с интернетом",Toast.LENGTH_SHORT).show()
        }
    }

    fun BuildDates(){
        var DatesLpr:String=""
        try{
            DatesLpr=GetHTTPThread().execute("http://$IP/Web_server/Date.php?idg=${GroupsData[SelectGroup].GetGroId()}&ids=${SubjectData[SelectSubject].GetSubId()}").get()
            DatesData.clear()
            SelectDate=0
            DatesData= this.deserializeDate(DatesLpr)!!
            Num=ArrayList<Int>()
            LprArray=ArrayList<Int>()
            var f:Boolean=true
            for (i in 0 until DatesData.count()) {
                if (Num.count() > 0) {
                    f = true
                    var j = 0
                    while (j < Num.count()) {
                        if (Num[j] == DatesData[i].getNum()) {
                            f = false
                            break
                        }
                        j++
                    }
                    if (f) {
                        Num.add(DatesData[i].getNum())
                        LprArray.add(DatesData[i].getLpr_Id())
                    }
                } else {
                    Num.add(DatesData[0].getNum())
                    LprArray.add(DatesData[0].getLpr_Id())
                }
            }
            ViewDate()
            BuildBook(true)
            var tblLayout = findViewById<View>(R.id.tlHead)as TableLayout
            tblLayout.removeAllViews()
            var LLH=LLHead
            var tableRow:TableRow = TableRow(this);
            tableRow.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
            var Head:Array<String> = arrayOf("Фамилия, Имя, Отчество","Усп","Пос")
            var j:Int=0
            while(j<3) {
                var TV: TextView = TextView(this)
                TV.setTextColor(getResources().getColor(android.R.color.black))
                TV.setTextSize(15.toFloat())
                TV.setHeight(TV.getTextSize().toInt() * 2)
                when(ColorT.toInt()){
                    0 -> TV.setBackgroundResource(R.drawable.grid3)
                    1 -> TV.setBackgroundResource(R.drawable.grid32)
                    2 -> TV.setBackgroundResource(R.drawable.grid33)
                    3 -> TV.setBackgroundResource(R.drawable.grid35)
                    4 -> TV.setBackgroundResource(R.drawable.grid36)
                    5 -> TV.setBackgroundResource(R.drawable.grid37)
                    6 -> TV.setBackgroundResource(R.drawable.grid38)
                    7 -> TV.setBackgroundResource(R.drawable.grid310)
                }

                TV.setText(Head[j])
                if ((j % 3) == 0) {
                    TV.setWidth((LLH.getWidth() / (1.3).toFloat()).toInt())
                } else {
                    TV.setWidth((LLH.getWidth() - (LLH.getWidth() / (1.3).toFloat()).toInt()) / 2)
                }
                TV.setTypeface(Typeface.create("monospace", Typeface.BOLD))
                TV.setHeight(LLH.getHeight())
                TV.setGravity(Gravity.CENTER)
                tableRow.addView(TV,j)
                j++
            }
            tblLayout.addView(tableRow)
        }catch (e:Exception){
            Toast.makeText(applicationContext,"Пропало подключение к серверу, проверьте соединение с интернетом",Toast.LENGTH_SHORT).show()
        }
    }

    fun ViewDate(){
        var i=0
        if((Buf!=-1)&&(Buf<Num.count())) {
            SelectDate = Buf
            Buf=-1
        }
        Dat=ArrayList<String>()
        for (i in 0 until DatesData.count()) {
            if(Num[SelectDate]==DatesData[i].getNum()){
                Dat.add(DatesData[i].getLesson_Date())
                NumDate=DatesData[i].getLpr_Id()
            }
        }
        while((Dat.count()-1)!=2){
            Dat.add(" ")
        }
        val gvDates = findViewById<View>(R.id.gvDate) as GridView
        gvDates!!.setPadding(0,0,5,0)
        var adapter = ArrayAdapter<String>(this, R.layout.item, R.id.tvText, Dat)
        gvDates!!.setAdapter(adapter)
        gvDates!!.onItemClickListener = object : AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {}
            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
        gvDates!!.setAdapter(adapter)
        var Width:Int=LLDate.getWidth()
        val btn1=findViewById<View>(R.id.button) as Button
        val btn2=findViewById<View>(R.id.button2) as Button
        var NumTv=findViewById<View>(R.id.tvNum) as TextView
        var Buff=(Width/3.2).toInt()
        btn1.setLayoutParams(LinearLayout.LayoutParams(Buff, gvDates!!.height))
        btn2.setLayoutParams(LinearLayout.LayoutParams(Buff, gvDates!!.height))
        Width-=(Buff*2)
        Buff=(Width/5.3).toInt()
        NumTv.text = "№${Num[SelectDate]}"
        NumTv.setTextSize(15.toFloat())
        NumTv.setLayoutParams(LinearLayout.LayoutParams(Buff,gvDates!!.height))
        LLDate.invalidate()
    }

    fun onCreateDialogMarks(position:Int, posTable:Int){
        var Marks= emptyArray<String>()
        for (i in 0 until MarksData.count()) {
            Marks+=MarksData[i].getName()
        }
        Marks+="Удалить"
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Выбор оценки") // заголовок для диалога
        builder.setItems(Marks) { dialog, item ->
            run {
                if (item < Marks.count() - 1){// || (Book[posTable * (SelectDate + 1)] == "")) {
                    if (InsertInto) {
                        var BUFF = InstalUpdateDelete(Journal[SelectDate].getBookData()[position].getStu_ID(), LprArray[SelectDate], MarksData[item].getMrtId(), 0, "INS", position, 0, 0)
                        MarksIUD.add(BUFF)
                        Journal[SelectDate].getBookData()[position].setMar_Name(Marks[item])
                        BuildBook(false)
                    } else {
                        var i: Int = 0
                        var f: Boolean = false
                        while (i < MarksIUD.count()) {
                            if ((MarksIUD[i].getPositionTable() == position) && (MarksIUD[i].getIUD() == "INS")) {
                                f = true
                                break
                            }
                            i++
                        }
                        if (f) {
                            var BUFF = InstalUpdateDelete(Journal[SelectDate].getBookData()[position].getStu_ID(), LprArray[SelectDate], MarksData[item].getMrtId(), 0, "INS", position, 0, 0)
                            MarksIUD.set(i, BUFF)
                        } else {
                            var BUFF = InstalUpdateDelete(Journal[SelectDate].getBookData()[position].getStu_ID(), LprArray[SelectDate], MarksData[item].getMrtId(), Journal[SelectDate].getBookData()[position].getMar_ID(), "UPD", position, 0, 0)
                            MarksIUD.add(BUFF)
                        }
                        Journal[SelectDate].getBookData()[position].setMar_Name(Marks[item])
                        BuildBook(false)
                    }
                } else {
                    if(Journal[SelectDate].getBookData()[position].getMar_Name()!="") {
                        var i: Int = 0
                        var f: Boolean = false
                        while (i < MarksIUD.count()) {
                            if ((MarksIUD[i].getPositionTable() == position) && (MarksIUD[i].getIUD() == "INS")) {
                                f = true
                                break
                            }
                            i++
                        }
                        if (f) {
                            MarksIUD.removeAt(i)
                            Journal[SelectDate].getBookData()[position].setMar_Name("")
                        } else {
                            var BUFF = InstalUpdateDelete(0, 0, 0, Journal[SelectDate].getBookData()[position].getMar_ID(), "DEL", position, 0, 0)
                            Journal[SelectDate].getBookData()[position].setMar_Name("")
                            MarksIUD.add(BUFF)
                        }
                        BuildBook(false)
                    }else{
                        Toast.makeText(applicationContext, "Нечего удалять", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        builder.setCancelable(false)
        builder.show()
    }

    fun onCreateDialogAttendance(position:Int, posTable:Int){
        var Attendance= emptyArray<String>()
        for (i in 0 until AttendacesData.count()) {
            Attendance+=AttendacesData[i].getName()
        }
        Attendance+="Удалить"
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Выбор отметки посещаемости") // заголовок для диалога
        builder.setItems(Attendance) { dialog, item ->
            run {
                if (item < Attendance.count() - 1){// || (Book[posTable * (SelectDate + 1)] == "")) {
                    if (InsertInto) {
                        var BUFF = InstalUpdateDelete(Journal[SelectDate].getBookData()[position].getStu_ID(), LprArray[SelectDate], 0, 0, "INA", position, 0, AttendacesData[item].getAttId())
                        MarksIUD.add(BUFF)
                        Journal[SelectDate].getBookData()[position].setSign(AttendacesData[item].getSign())
                        BuildBook(false)
                    } else {
                        var i: Int = 0
                        var f: Boolean = false
                        while (i < MarksIUD.count()) {
                            if ((MarksIUD[i].getPositionTable() == position) && (MarksIUD[i].getIUD() == "INA")) {
                                f = true
                                break
                            }
                            i++
                        }
                        if (f) {
                            var BUFF = InstalUpdateDelete(Journal[SelectDate].getBookData()[position].getStu_ID(), LprArray[SelectDate], 0, 0, "INA", position, 0, AttendacesData[item].getAttId())
                            MarksIUD.set(i, BUFF)
                            Journal[SelectDate].getBookData()[position].setSign(AttendacesData[item].getSign())
                        } else {
                            var BUFF = InstalUpdateDelete(0, 0, 0, 0, "UPA", position, Journal[SelectDate].getBookData()[position].getAte_ID(), AttendacesData[item].getAttId())
                            MarksIUD.add(BUFF)
                            Journal[SelectDate].getBookData()[position].setSign(AttendacesData[item].getSign())
                        }
                        BuildBook(false)
                    }
                } else {
                    if(Journal[SelectDate].getBookData()[position].getSign()!="") {
                        var i: Int = 0
                        var f: Boolean = false
                        while (i < MarksIUD.count()) {
                            if ((MarksIUD[i].getPositionTable() == position) && (MarksIUD[i].getIUD() == "INA")) {
                                f = true
                                break
                            }
                            i++
                        }
                        if (f) {
                            MarksIUD.removeAt(i)
                            Journal[SelectDate].getBookData()[position].setSign("")
                        } else {
                            var BUFF = InstalUpdateDelete(0, 0, 0, 0, "DEA", position, Journal[SelectDate].getBookData()[position].getAte_ID(), 0)
                            MarksIUD.add(BUFF)
                            Journal[SelectDate].getBookData()[position].setSign("")
                        }
                        BuildBook(false)
                    }else{
                        Toast.makeText(applicationContext, "Нечего удалять", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        builder.setCancelable(false)
        builder.show()
    }

    fun BuildBook(load:Boolean){
        if(load) {
            Journal.clear()
            for (i in 0 until Num.count()) {
                var DatesBook:String=""
                try{
                    DatesBook = GetHTTPThread().execute("http://$IP/Web_server/Book.php?idg=${GroupsData[SelectGroup].GetGroId()}&lpr=${LprArray[i]}").get()
                }catch (e:Exception){
                    Toast.makeText(applicationContext,"Пропало подключение к серверу, проверьте соединение с интернетом",Toast.LENGTH_SHORT).show()
                    finish()
                }
                var B=this.deserializeBook(DatesBook)!!
                var BCJ=ClassJournal(B)
                Journal.add(BCJ)
            }
        }
        var COLS:Int = 3;
        var tblLayout = findViewById<View>(R.id.tableLayout)as TableLayout
        tblLayout.removeAllViews()
        var K:Int=0
        var t:Int=0
        var i:Int=0
        var SCV=scrollView
        while(i<(Journal[SelectDate].getBookData().count())) {///dataLprID.count())/3
            var tableRow:TableRow = TableRow(this);
            tableRow.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            var j:Int=0
            while(j<COLS) {
                var TV:TextView= TextView(this)
                TV.setTextColor(getResources().getColor(android.R.color.black))
                TV.setTextSize(15.toFloat())
                TV.setHeight(LLHead.getHeight())
                when(j){
                    0->TV.setText(Journal[SelectDate].getBookData().get(i).getName())
                    1->TV.setText(Journal[SelectDate].getBookData().get(i).getMar_Name())
                    2->TV.setText(Journal[SelectDate].getBookData().get(i).getSign())
                }
                if(i%2==0){
                    TV.setBackgroundResource(R.drawable.grid)
                }else{
                    TV.setBackgroundResource(R.drawable.grid2)
                }
                if((t % 3) == 0){
                    TV.setPadding(10, 20, 0, 0)
                    TV.setGravity(Gravity.LEFT)
                    TV.setWidth((SCV.getWidth()/(1.3).toFloat()).toInt())
                }else{
                    TV.setPadding(0, 20, 0, 0)
                    TV.setGravity(Gravity.CENTER_HORIZONTAL)
                    TV.setWidth((SCV.getWidth()-(SCV.getWidth()/(1.3).toFloat()).toInt())/2)
                }
                TV.setTag(t)
                TV.setOnClickListener {
                    var position:Int= TV.getTag() as Int
                    if ((position % 3) != 0) {
                        SelectBook=position
                        if (((position - 1) % 3) != 0) {
                            InsertInto = TV.text==""//если ячейка пустая то добаляем иначе изменяем
                            onCreateDialogAttendance((position/3).toInt(),position)//посещаемость
                        } else {
                            InsertInto = TV.text==""//если ячейка пустая то добаляем иначе изменяем
                            onCreateDialogMarks((position/3).toInt(),position)//оценка
                        }
                    }
                }
                tableRow.addView(TV,j)
                K++
                t++
                j++
            }
            tblLayout.addView(tableRow,i)
            i++
        }
    }

    fun deserializeGroup(JSonString: String): ArrayList<ClassGroup>? {
        val gson = Gson()
        val token = object : TypeToken<ArrayList<ClassGroup>>() {}
        return try {
            gson.fromJson<ArrayList<ClassGroup>>(JSonString, token.type)
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    fun deserializeSubject(JSonString: String): ArrayList<ClassSubject>? {
        val gson = Gson()
        val token = object : TypeToken<ArrayList<ClassSubject>>() {}
        return try {
            gson.fromJson<ArrayList<ClassSubject>>(JSonString, token.type)
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    fun deserializeDate(JSonString: String): ArrayList<ClassLp>? {
        val gson = Gson()
        val token = object : TypeToken<ArrayList<ClassLp>>() {}
        return try {
            gson.fromJson<ArrayList<ClassLp>>(JSonString, token.type)
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    fun deserializeBook(JSonString: String): ArrayList<ClassBook>? {
        val gson = Gson()
        val token = object : TypeToken<ArrayList<ClassBook>>() {}
        return try {
            gson.fromJson<ArrayList<ClassBook>>(JSonString, token.type)
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    fun deserializeMark(JSonString: String): ArrayList<ClassMark>? {
        val gson = Gson()
        val token = object : TypeToken<ArrayList<ClassMark>>() {}
        return try {
            gson.fromJson<ArrayList<ClassMark>>(JSonString, token.type)
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    fun deserializeAttendaces(JSonString: String): ArrayList<ClassAttendaces>? {
        val gson = Gson()
        val token = object : TypeToken<ArrayList<ClassAttendaces>>() {}
        return try {
            gson.fromJson<ArrayList<ClassAttendaces>>(JSonString, token.type)
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    fun onClickBack(view: View){
        if(SelectDate>0){
            SelectDate--
            ViewDate()
            BuildBook(false)
        }else{
            Toast.makeText(applicationContext,"Это самое первое занятие в списке",Toast.LENGTH_SHORT).show();
        }
    }

    fun onClickNext(view: View){
        if(SelectDate<Num.count()-1){
            SelectDate++
            ViewDate()
            BuildBook(false)
        }else{
            Toast.makeText(applicationContext,"Это последнее занятие в списке",Toast.LENGTH_SHORT).show();
        }
    }

}
