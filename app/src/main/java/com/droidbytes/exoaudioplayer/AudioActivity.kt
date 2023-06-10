package com.droidbytes.exoaudioplayer

import com.droidbytes.exoaudioplayer.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.SeekBar
import com.droidbytes.exoaudioplayer.databinding.ActivityAudioBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer

class AudioActivity : AppCompatActivity() {
    lateinit var binding: ActivityAudioBinding
    lateinit var player: SimpleExoPlayer
    var duartion : Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        player = SimpleExoPlayer.Builder(this@AudioActivity).build()
        player.addListener(object : Player.Listener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_READY && player.playWhenReady) {
                    binding.playPauseButton.setImageDrawable(resources.getDrawable(R.drawable.pause))
                } else {
                    binding.playPauseButton.setImageDrawable(resources.getDrawable(R.drawable.play))
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                duartion = player.duration.toInt() / 1000
                binding.seekbar.max = duartion
                binding.time.text = "0:00 / " + gettimeString(duartion)
            }

            override fun onPositionDiscontinuity(
                oldPosition: Player.PositionInfo,
                newPosition: Player.PositionInfo,
                reason: Int
            ) {
                var currentposition = player.currentPosition.toInt()/1000
                binding.seekbar.progress=currentposition
                binding.time.setText(gettimeString(currentposition) + "/" + gettimeString(player.duration.toInt()/1000)) }

        })

        var mediaItem=MediaItem.fromUri("https://firebasestorage.googleapis.com/v0/b/anti-corruption-2104.appspot.com/o/audios%2F-NXJmyWz4O2com2tbzRT?alt=media&token=691a828a-64eb-4773-90ab-433ecaecbaa0&_gl=1*150e5xn*_ga*ODgzNDQ0MTAuMTY4MzkwMzc1Mw..*_ga_CW55HF8NVT*MTY4NjEyMzU5OC43LjEuMTY4NjEyMzY2Ny4wLjAuMA..")
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()

        binding.playPauseButton.setOnClickListener {
            player.playWhenReady=!player.playWhenReady
        }
            binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if(fromUser){
                        player.seekTo(progress.toLong() *1000)
                        binding.time.text=gettimeString(progress)+"/" + gettimeString(duartion)
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }

            })

        var handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable{
            override fun run() {
                var currentposition = player.currentPosition.toInt()/1000
                binding.seekbar.progress=currentposition
                binding.time.setText(gettimeString(currentposition) + "/" + gettimeString(duartion))
                handler.postDelayed(this,1000)
            }

        })

        player.addListener(object : Player.Listener{
            override fun onPlaybackStateChanged(playbackState: Int) {
                if(Player.STATE_BUFFERING==playbackState){
                    binding.progressBar.visibility=View.VISIBLE
                }
                else {
                    binding.progressBar.visibility=View.GONE
                }
            }
        })

    }

    fun gettimeString(duration: Int): String {
        var min=duration/60
        var sec=duration%60
        var time = String.format("%02d:%02d",min,sec)
        return time
    }

}