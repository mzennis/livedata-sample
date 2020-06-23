package com.tokopedia.play.view.activity

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tokopedia.play.R
import java.util.concurrent.TimeUnit


/**
 * Created by mzennis on 23/06/20.
 */
class TimerActivity : AppCompatActivity() {

    private var countDownTimer: CountDownTimer? = null

    private lateinit var textTimer: TextView
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        textTimer = findViewById(R.id.txt_timer)
        btnStart = findViewById(R.id.btn_start)
        btnStop = findViewById(R.id.btn_stop)

        btnStart.setOnClickListener { startTimer() }
        btnStop.setOnClickListener { cancelTimer() }
    }

    private fun cancelTimer() {
        countDownTimer?.cancel()
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer((30*60)*1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateUi(millisUntilFinished)
            }

            override fun onFinish() { }
        }
        countDownTimer?.start()
    }

    private fun updateUi(millisUntilFinished: Long) {
        textTimer.text = millisToMinuteSecond(millisUntilFinished)
    }

    /**
     * Cancel Timer when activity destroyed
     * to avoid memory leak
     * */
    override fun onDestroy() {
        super.onDestroy()
        cancelTimer()
    }

    private fun millisToMinuteSecond(millis: Long) = String.format("%02d:%02d",
        millisToMinute(millis),
        millisToSecond(millis)
    )

    private fun millisToMinute(millis: Long): Long = TimeUnit.MILLISECONDS.toMinutes(millis)
    private fun millisToSecond(millis: Long): Long = TimeUnit.MILLISECONDS.toSeconds(millis) -
            TimeUnit.MINUTES.toSeconds(millisToMinute(millis))
}