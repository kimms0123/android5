package com.example.android3

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

/*
    2. 스레드(멀티 스레드/경량화 프로세스)
        - 여러 작업을 동시에 수행하기 위해 사용 -> 프로세스 안에서 동작하는 작은 단위
        [함수와 스레드의 차이 그림]
    [스레드의 기본 동작]
        - 함수는 하나의 작업이 끝나야 다음 작업이 진행되지만, 스레드는 하나의 작업이 끝나기 전에 다른 작업을 동시 진행 가능
        ```
        object : Thread() {
            override fun run() {
                // 이곳에 작업 코딩
            }
        }.start()
        ```

    3. UI 스레드
        - 화면의 위젯을 변경할 때 사용
    [동작]
    ```
    runOnUiThread {
        // 위젯을 변경하는 코드를 이곳에 넣음
    }
 */
class exam3:AppCompatActivity() {
    // 예제
    lateinit var pb1 : ProgressBar
    lateinit var pb2 : ProgressBar
    lateinit var button1 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam3_main)

        pb1 = findViewById(R.id.pb1)
        pb2 = findViewById(R.id.pb2)
        button1 = findViewById(R.id.button1)

        button1.setOnClickListener {
            object : Thread() { // 첫번째 프로그래스 바 스레드 생성
                override fun run() {
                    for (i in 0..99) { // 100번 반복
                        pb1.progress = pb1.progress + 2 // 첫 프로그래스 바는 +2씩 증가
                        SystemClock.sleep(100)  // 100(0.1초) 밀리초 동안 멈춤. 이 행이 없으면 진행 상황이 안 보임
                    }
                }
            }.start()

            object : Thread() { // 두번째 프로그래스 바 스레드 생성
                override fun run() {
                    for (i in pb2.progress .. 99){
                        pb2.progress = pb2.progress + 1 // 두번째 프로그래스 바는 + 1씩 증가
                        SystemClock.sleep(100)
                    }
                }
            }.start()
        }
    }

}