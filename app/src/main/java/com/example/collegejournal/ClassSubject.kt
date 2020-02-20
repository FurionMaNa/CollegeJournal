package com.example.collegejournal

class ClassSubject{
    private var NAME:String=""
    private var SUB_ID: Int =0

    constructor(name: String, sub_id: Int) {
        this.NAME = name
        this.SUB_ID = sub_id
    }

    fun SetName(name:String){
        NAME=name
    }

    fun SetSubId(sub: Int){
        SUB_ID=sub
    }

    fun GetName():String{
        return NAME
    }

    fun GetSubId():Int{
        return SUB_ID
    }

}