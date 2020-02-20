package com.example.collegejournal

class ClassBook{
    private var MAR_ID:Int=0
    private var STU_ID: Int =0
    private var NAME: String =""
    private var marName: String =""
    private var SIGN: String =""
    private var ATE_ID: Int =0

    constructor(mar_id: Int, stu_id: Int, name: String, marname: String, sign: String, ate_id: Int) {
        this.MAR_ID = mar_id
        this.STU_ID = stu_id
        this.NAME = name
        this.marName = marname
        this.SIGN = sign
        this.ATE_ID = ate_id
    }

    fun setMar_ID(mar_id: Int){
        MAR_ID=mar_id
    }

    fun setStu_ID(stu_id: Int){
        STU_ID=stu_id
    }

    fun setName(name: String){
        NAME=name
    }

    fun setMar_Name(marname: String){
        marName=marname
    }

    fun setSign(sign: String){
        SIGN=sign
    }

    fun setAte_ID(ate_id: Int){
        ATE_ID=ate_id
    }

    fun getMar_ID():Int{
        return MAR_ID
    }

    fun getStu_ID(): Int{
        return STU_ID
    }

    fun getName(): String{
        return NAME
    }

    fun getMar_Name(): String{
        return marName
    }

    fun getSign(): String{
        return SIGN
    }

    fun getAte_ID(): Int{
        return ATE_ID
    }
}