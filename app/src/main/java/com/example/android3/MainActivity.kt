package com.example.android3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/*
    SQLite의 기본
    1. 데이터 베이스의 기본 개념
    [정의]
        - 대용량의 데이터 집합을 체계적으로 구성해놓은 것
        - 여러 사용자나 시스템이 서로 공유할 수 있어야 함
        📌 데이터베이스 관리 시스템: 데이터 베이스를 관리하는 시스템 또는 소프트웨어(SQL server, Access, Oracle Database, MySQL,DB2)
    [관계형 데이터베이스]
        - 계층형:
        - 망형:
        - 관계형:
        - 객체지향형:
        - 객체관계형:
        👉 관계형 DBMS를 많이 사용하고 일부 멀티미디어 분야에서 객체지향이나 관계형 사용
    [데이터베이스 관련 용오]
        - 데이터(data): 하나하나의 단편적 정보
        - 테이블(table): 데이터가 표 형태로 표현된 것
        - 데이터베이스(DB): 테이블이 저장되는 장소로 주로 원통모양으로 표현, 각 데이터베이스는 고유한 이름을 가짐
        - DBMS(DataBase Management System): 데이터베이스를 관리하는 시스템 또는 소프트웨어
        - 열(column/field): 각 테이블은 1개 이상의 열로 구성
        - 열이름: 각 열을 구분하는 이름, 이름은 각 테이블 안에서 중복 x
        - 데이터형식: 열의 데이터 형식, 테이블을 생성할 때 열 이름과 함께 지정
        - 행(row): 실제 데이터
        - SQL(Structured Query Language): 구조화된 질의 언어(=사용자와 DBMS가 소통하기 위한 언어)
    [SQLite에서 데이터베이스 구축]
        1단계                 2단계                         3단계
        DBMS 설치             데이터베이스 구축               응용 프로그램에서
        (이미 설치 완)         [1. 데이터베이스 생성]          구축된 데이터 활용
                             |2. 테이블 생성     |
                             |3. 데이터 입력     |
                             [4. 데이터 조회·활용 |
    [실습 -cmd로 진행]
        1. 앱 구동시켜 둘 것
        2. sdk -> platform-tools가 설치 되어있을 것
            ㄴ 위치 확인 메인(로비)화면에서 .3개 누르고 sdk manager 들어가서 설치 위치 확인
        나머지는 따로 정리

 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}