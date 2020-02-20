package com.example.collegejournal

class ClassJournal{

    private var BookData=ArrayList<ClassBook>()

    constructor(bookdata: ArrayList<ClassBook>) {
        this.BookData = bookdata
    }

    fun setBookData(bookdata: ArrayList<ClassBook>){
        BookData=bookdata
    }

    fun getBookData():ArrayList<ClassBook>{
        return BookData
    }
}