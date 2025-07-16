package com.example.android3

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import org.w3c.dom.ls.LSInput
import java.io.File

class exam2_2 : AppCompatActivity() {

    var listViewMP3: ListView? = null
    lateinit var btnPlay: Button
    lateinit var btnStop: Button
    lateinit var tvMP3: TextView
    lateinit var pbMP3: ProgressBar

    lateinit var mp3List: ArrayList<String>
    lateinit var selectedMP3: String

    // ✅ [수정 내용 #1] 직접 지정된 경로 사용
    val mp3Path = "/storage/emulated/0/"
    lateinit var mPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam2_2_main)
        title = "간단 MP3 플레이어"

        // ✅ [수정 내용 #2] 권한 요청은 그대로 유지
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            1
        )

        mp3List = ArrayList()

        // ✅ [수정 내용 #3] 직접 지정한 경로에서 MP3 파일 검색
        val listFiles = File(mp3Path).listFiles()
        if (listFiles != null) {
            for (file in listFiles) {
                val fileName = file.name
                if (fileName.endsWith(".mp3", ignoreCase = true)) {
                    mp3List.add(fileName)
                }
            }
        }

        if (mp3List.isEmpty()) {
            Toast.makeText(this, "MP3 파일이 없습니다.", Toast.LENGTH_LONG).show()
            return
        }

        listViewMP3 = findViewById(R.id.listViewMp3)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, mp3List)
        listViewMP3!!.choiceMode = ListView.CHOICE_MODE_SINGLE
        listViewMP3!!.adapter = adapter
        listViewMP3!!.setItemChecked(0, true)

        selectedMP3 = mp3List[0]

        listViewMP3!!.setOnItemClickListener { _, _, position, _ ->
            selectedMP3 = mp3List[position]
        }

        btnPlay = findViewById(R.id.btnPlay)
        btnStop = findViewById(R.id.btnStop)
        tvMP3 = findViewById(R.id.tvMP3)
        pbMP3 = findViewById(R.id.pbMP3)

        btnPlay.setOnClickListener {
            mPlayer = MediaPlayer()
            mPlayer.setDataSource(mp3Path + selectedMP3)
            mPlayer.prepare()
            mPlayer.start()
            btnPlay.isClickable = false
            btnStop.isClickable = true
            tvMP3.text = "실행중인 음악 : $selectedMP3"
            pbMP3.visibility = View.VISIBLE
        }

        btnStop.setOnClickListener {
            mPlayer.stop()
            mPlayer.reset()
            btnPlay.isClickable = true
            btnStop.isClickable = false
            tvMP3.text = "실행중인 음악 : "
            pbMP3.visibility = View.INVISIBLE
        }

        btnStop.isClickable = false
    }
}
