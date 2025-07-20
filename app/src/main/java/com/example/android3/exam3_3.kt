package com.example.android3

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.File
import java.text.SimpleDateFormat
import java.util.TreeMap

// 4. 스레드 응용 (지난번 파일 권한 문제 지속...)
class exam3_3 : AppCompatActivity() {

    var listViewMP3: ListView? = null
    lateinit var btnPlay: Button
    lateinit var btnStop: Button
    lateinit var tvMP3: TextView
    lateinit var pbMP3: SeekBar
    lateinit var tvTime : TextView

    lateinit var mp3List: ArrayList<String>
    lateinit var selectedMP3: String

    lateinit var mPlayer: MediaPlayer
    lateinit var mp3Path: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam3_3_main)
        title = "간단 MP3 플레이어"

        mp3Path = getExternalFilesDir(null)?.absolutePath + "/"

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                100
            )
        } else {
            // API 30 이상은 권한 없이도 앱 전용 디렉토리 접근 가능
            initPlayer()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            initPlayer()
        } else {
            Toast.makeText(this, "권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    fun initPlayer() {
        mp3List = ArrayList()

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
            try {
                if (::mPlayer.isInitialized) {
                    mPlayer.release()
                }
                mPlayer = MediaPlayer()
                mPlayer.setDataSource(mp3Path + selectedMP3)
                mPlayer.prepare()
                mPlayer.start()

                btnPlay.isClickable = false
                btnStop.isClickable = true
                tvMP3.text = "실행중인 음악 : $selectedMP3"
                pbMP3.visibility = View.VISIBLE
                object : Thread(){
                    var timeFormat = SimpleDateFormat("mm:ss")
                    override fun run() {
                        if(mPlayer == null)
                            return
                        pbMP3.max = mPlayer.duration
                        while (mPlayer.isPlaying) {
                            runOnUiThread{
                                pbMP3.progress = mPlayer.currentPosition
                                tvTime.text ="진행시간: " + timeFormat.format(mPlayer.currentPosition)
                            }
                            SystemClock.sleep(200)
                        }
                    }
                }.start()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "재생 오류: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        btnStop.setOnClickListener {
            if (::mPlayer.isInitialized && mPlayer.isPlaying) {
                mPlayer.stop()
                mPlayer.release()
            }

            btnPlay.isClickable = true
            btnStop.isClickable = false
            tvMP3.text = "실행중인 음악 : "
            pbMP3.visibility = View.INVISIBLE
        }

        btnStop.isClickable = false
    }
}
