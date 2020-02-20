package com.example.collegejournal

class InstalUpdateDelete constructor(var Student:Int,var LPR:Int,var MRT:Int,
                                     var MAR:Int,var iud:String,var Pos:Int,var ATE:Int, var ATT:Int){
    private var STU_ID:Int?=Student
    private var LPR_ID:Int?=LPR
    private var MRT_ID:Int?=MRT
    private var MAR_ID:Int?=MAR
    private var ATE_ID:Int?=ATE
    private var ATT_ID:Int?=ATT
    private var IUD:String?=iud
    private var PositionTable:Int?=Pos

    fun setStu_Id(value:Int){
        this.STU_ID=value
    }

    fun setLpr_Id(value:Int){
        this.LPR_ID=value
    }

    fun setMrt_Id(value:Int){
        this.MRT_ID=value
    }

    fun setMar_Id(value:Int){
        this.MAR_ID=value
    }

    fun setAte_Id(value:Int){
        this.ATE_ID=value
    }

    fun setAtt_Id(value:Int){
        this.ATT_ID=value
    }

    fun setPositionTable(value:Int){
        this.PositionTable=value
    }

    fun setIUD(value:String){
        this.IUD=value
    }//

    fun getStu_Id(): Int? {
        return STU_ID
    }

    fun getLpr_Id(): Int? {
        return LPR_ID
    }

    fun getMrt_Id(): Int? {
        return MRT_ID
    }

    fun getMar_Id(): Int? {
        return MAR_ID
    }

    fun getAte_Id(): Int? {
        return ATE_ID
    }

    fun getAtt_Id(): Int? {
        return ATT_ID
    }

    fun getPositionTable(): Int? {
        return PositionTable
    }

    fun getIUD(): String? {
        return IUD
    }

}