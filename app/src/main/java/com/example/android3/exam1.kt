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
        - 앱 데이터(회원 정보, 메모, 게시판)를 로컬에 저장가능
    [SQLite]
        - 앱 전용 데이터베이스 파일을 /data/data/앱패키지/databases/에 생성
        - 앱마다 독립적인 데이터베이스 파일로 운영
        - SQL 문법 그대로 사용 가능
    [기본 동작 흐름]
        1. SQLiteOpenHelper 상속 클래스 작성
            - onCreate() : DB 처음 생성될 때 호출되어 테이블 갱신
            - onUpgrade() : DB 버전이 변경될 때 호출되어 테이블 갱신
        2. SQLiteDataBase 객체 열기
            - writeableDatabase : 쓰기/읽기 가능
            - readableDatabase : 읽기 전용
        3. SQL 쿼리 실행
            - execSQL() : INSERT, UPDATE, DELETE 테이블 생성 등 실행
            - rawQuery() : SELECT 쿼리 실행 후 Cursor로 결과 받기
        4. 결과 처리 및 DB 닫기
            - close() : DB 닫기
    [SQLite 관련 클래스의 동작]
        - SQLiteOpenHelper  : 데이터베이스 생성과 버전 관리 담당. (onCreate(), onUpgrade() 오버라이딩)
        - SQLiteDatabase    : 데이터베이스를 열고, 쿼리를 실행하는 객체.(execSQL(), rawQuery() 제공)
        - Cursor            : rawQuery() 결과로 반환된 테이블 형태 데이터를 탐색하는 객체
        - ContentValues     : key-value 형태로 데이터를 담아 insert/update에 사용 가능(옵션)

              | 클래스 |              | 메서드 |                    | 설명 |             |
        | :--------------- | :------------------------ | :----------------------------|
        | SQLiteOpenHelper | `getWritableDatabase()`   | 읽기/쓰기 가능한 DB 객체 반환    |
        | SQLiteOpenHelper | `getReadableDatabase()`   | 읽기 전용 DB 객체 반환          |
        | SQLiteDatabase   | `execSQL()`               | SQL 실행 (결과 반환 X)         |
        | SQLiteDatabase   | `rawQuery()`              | SELECT 쿼리 실행 후 Cursor 반환 |
        | Cursor           | `moveToNext()`            | 다음 행으로 이동                |
        | Cursor           | `getString()`, `getInt()` | 현재 행에서 컬럼 값 가져오기      |


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
    lateinit var btnUpdate : Button
    lateinit var btnDelete : Button
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
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)

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
            btnSelect.callOnClick()
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
        btnUpdate.setOnClickListener {
            sqlDB = myHelper.writableDatabase
            sqlDB.execSQL("UPDATE groupTBL SET gNumber = ${edtNumber.text} WHERE gName = '${edtName.text}';")
            sqlDB.close()
            btnSelect.callOnClick()
            Toast.makeText(applicationContext, "수정됨", Toast.LENGTH_SHORT).show()
        }
        btnDelete.setOnClickListener {
            sqlDB = myHelper.writableDatabase
            sqlDB.execSQL("DELETE FROM groupTBL WHERE gName = '${edtName.text}';")
            btnSelect.callOnClick()
            sqlDB.close()
            Toast.makeText(applicationContext, "삭제됨", Toast.LENGTH_SHORT).show()
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