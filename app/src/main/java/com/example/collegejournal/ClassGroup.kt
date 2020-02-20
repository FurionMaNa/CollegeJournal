package com.example.collegejournal

class ClassGroup{
    private var NAME:String=""
    private var GRO_ID: Int =0

    constructor(name: String, gro_id: Int) {
        this.NAME = name
        this.GRO_ID = gro_id
    }

    fun SetName(name:String){
        NAME=name
    }

    fun SetGroId(gro_id: Int){
        GRO_ID=gro_id
    }

    fun GetName():String{
        return NAME
    }

    fun GetGroId():Int{
        return GRO_ID
    }

}