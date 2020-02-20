package com.example.collegejournal

class ClassLp{
    private var LPR_ID:Int=0
    private var NUM: Int =0
    private var LESSON_DATE: String =""

    constructor(lpr_id: Int, num: Int, lesson_date: String) {
        this.LPR_ID = lpr_id
        this.NUM = num
        this.LESSON_DATE = lesson_date
    }

    fun setLpr_Id(lpr_id: Int){
        LPR_ID=lpr_id
    }

    fun getLpr_Id():Int{
        return LPR_ID
    }

    fun setNum(num: Int){
        NUM=num
    }

    fun getNum():Int{
        return NUM
    }

    fun setLesson_Date(lesson_date: String){
        LESSON_DATE=lesson_date
    }

    fun getLesson_Date():String{
        return LESSON_DATE
    }
}