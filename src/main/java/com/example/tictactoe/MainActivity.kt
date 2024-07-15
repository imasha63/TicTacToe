package com.example.tictactoe


import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class MainActivity : AppCompatActivity() {
    private lateinit var startNewGameButton: Button


    // lateinit var toggleSoundButton: Button
    //private lateinit var mediaPlayer: MediaPlayer
    //private var isSoundOn: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startNewGameButton = findViewById(R.id.startNewGameButton)
        //toggleSoundButton = findViewById(R.id.toggleSoundButton)


        //mediaPlayer = MediaPlayer.create(this, R.raw.background_sound)
        //mediaPlayer.isLooping = true

        startNewGameButton.setOnClickListener {
            val intent = Intent(MainActivity@this, GameActivity::class.java)
            startActivity(intent)
        }

        /*toggleSoundButton.setOnClickListener {
            if (isSoundOn) {
                // Mute the sound
                mediaPlayer.pause()
                toggleSoundButton.text = "Unmute Sound"
            } else {
                // Unmute the sound
                mediaPlayer.start()
                toggleSoundButton.text = "Mute Sound"
            }
            isSoundOn = !isSoundOn
        }*/


    }

    override fun onResume() {
        super.onResume()
        // Start playing background sound when the activity resumes
        /*if (isSoundOn) {
            //mediaPlayer.start()
        }*/
    }

    override fun onPause() {
        super.onPause()
        // Pause background sound when the activity is paused
        /*if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release the media player when the activity is destroyed
        //mediaPlayer.release()
    }

}









