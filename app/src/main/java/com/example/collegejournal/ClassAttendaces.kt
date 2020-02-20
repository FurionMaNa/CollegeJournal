package com.example.collegejournal

class ClassAttendaces{
    private var ATT_ID:Int=0
    private var SIGN:String=""
    private var NAME:String=""

    constructor(att_id: Int, sign: String, name: String) {
        this.ATT_ID = att_id
        this.SIGN = sign
        this.NAME = name
    }

    fun setAttId(value:Int){
        this.ATT_ID=value
    }

    fun getAttId():Int{
        return ATT_ID
    }

    fun setSign(value:String){
        this.SIGN=value
    }

    fun getSign():String{
        return SIGN
    }

    fun setName(value:String){
        this.NAME=value
    }

    fun getName():String{
        return NAME
    }
}