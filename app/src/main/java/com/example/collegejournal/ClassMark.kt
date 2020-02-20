package com.example.collegejournal

class ClassMark{
    private var MRT_ID:Int=0
    private var NAME:String=""

    constructor(mrt_id: Int, name: String) {
        this.MRT_ID = mrt_id
        this.NAME = name
    }

    fun setMarId(mrt_id: Int){
        this.MRT_ID=mrt_id
    }

    fun setName(name: String){
        this.NAME=name
    }

    fun getMrtId():Int{
        return MRT_ID
    }

    fun getName():String{
        return NAME
    }

}