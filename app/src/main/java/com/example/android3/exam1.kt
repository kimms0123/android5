package com.example.android3

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/*
    1. SQLite 프로그래밍
    [SQLite 관련 클래스 동작]

    [SQLite 관련 클래스 및 인터페이스와 메소드]

 */

class exam1 : AppCompatActivity() {
    lateinit var myHelper: myDBHelper
    lateinit var edtName: EditText
    lateinit var edtNumber : EditText
    lateinit var edtNameResult : EditText
    lateinit var edtNumberResult: EditText
    lateinit var btnInit : Button
    lateinit var btnInsert : Button
    lateinit var btnSelect : Button
    lateinit var sqlDB : SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam1_main)
        title="가수 그룹 관리 DB"

        edtName = findViewById(R.id.edtName)
        edtNumber = findViewById(R.id.edtNumber)
        edtNumberResult = findViewById(R.id.edtNumberResult)
        edtNameResult = findViewById(R.id.edtNameResult)

        btnInit = findViewById(R.id.btnInit)
        btnSelect = findViewById(R.id.btnSelect)
        btnInsert = findViewById(R.id.btnInsert)

        myHelper = myDBHelper(this)
        btnInit.setOnClickListener {
            sqlDB = myHelper.writableDatabase
            myHelper.onUpgrade(sqlDB, 1, 2)
            sqlDB.close()
        }
        btnInsert.setOnClickListener {
            sqlDB = myHelper.writableDatabase
            sqlDB.execSQL("INSERT INTO groupTBL VALUES ('" + edtName.text.toString() + "' , "
                    + edtNumber.text.toString() + ");")
            sqlDB.close()
            Toast.makeText(applicationContext, "입력됨", Toast.LENGTH_SHORT).show()
        }
        btnSelect.setOnClickListener {
            sqlDB = myHelper.readableDatabase
            var cursor: Cursor
            cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;", null)

            var strNames = "그룹이름" + "\r\n" + "------" + "\r\n"
            var strNumbers = "인원" + "\r\n" + "------" + "\r\n"

            while (cursor.moveToNext()) {
                strNames += cursor.getString(0) + "\r\n"
                strNumbers += cursor.getString(1) + "\r\n"
            }

            edtNameResult.setText(strNames)
            edtNumberResult.setText(strNumbers)

            cursor.close()
            sqlDB.close()
        }

    }
    inner class myDBHelper(context: Context) : SQLiteOpenHelper(context, "groupDB", null, 1){
        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL("CREATE TABLE groupTBL(gName CHAR(20) PRIMARY KEY, gNumber INTEGER);")
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("DROP TABLE IF EXISTS groupTBL")
            onCreate(p0)
        }
    }
}