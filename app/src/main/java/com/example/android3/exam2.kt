package com.example.android3

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity

/*
    멀티미디어와 구글 지도
    1. 오디오
        - MediaPlayer 클래스 : 음악과 동영상 재생
            - start() : 시작
            - pause() : 일시정지
            - stop() : 정지
    [리소스 파일과 저장 폴더
        - 응용 프로그램 제작 시 사용하는 다양한 리소스 파일이 저장되는 위치

    리소스                            |         폴더             |     저장파일
    --------------------------------------------------------------------------------
    그림파일                           |     /res/drawable       |    .png/.jpg/.xml
    메뉴파일                           |     /res/menu           |    .xml
    기타 xml 파일                      |     /res/xml            |    .xml
    raw 파일(음악, 동영상, 텍스트 파일)   |     /res/raw            |    .mp3/.mp4/.txt
    레이아웃 파일                       |     /res/layout         |    .xml
    문자열(String)                     |    /res/values          |    string.xml
    문자 배열(String Array)            |     /res/values         |    arrays.xml
    색상 값                            |    /res/values          |    colors.xml
    스타일                             |    /res/values          |    styles.xml
    테마                              |   /res/values            |   themes.xml
    ================================================================================
 */
class exam2  : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam2_main)

        var mPlayer : MediaPlayer
        mPlayer = MediaPlayer.create(this, R.raw.song1)

        var switch1 = findViewById<Switch>(R.id.switch1)
        switch1.setOnClickListener {
            if (switch1.isChecked == true)
                mPlayer.start()
            else
                mPlayer.stop()
        }
    }

}