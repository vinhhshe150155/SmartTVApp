package com.fptu.smarttvapp.activity

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.fptu.smarttvapp.R
import com.fptu.smarttvapp.model.Event
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants.PlayerState
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.util.regex.Pattern

@Suppress("DEPRECATION")
class DisplayActivity : AppCompatActivity() {
    private var events = ArrayList<Event>()
    private var currentIndex = 0
    private var imgBanner: ImageView? = null
    private var cvQRCode: CardView? = null
    private var imgQRCode: ImageView? = null
    private var youTubePlayerView: YouTubePlayerView? = null
    private val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        initView()
        initData()
        showEvent()
    }

    private fun initData() {
        events.add(
            Event(
                "https://scontent.fhan2-3.fna.fbcdn.net/v/t1.6435-9/89013939_2577414925861332_2672208422551683072_n.jpg?_nc_cat=101&ccb=1-7&_nc_sid=e3f864&_nc_ohc=NYfprC2ft_QAX8Gjsmo&_nc_ht=scontent.fhan2-3.fna&oh=00_AfBEUNsnqvUNNGpWVeezLYJDmnPhmLV71HV_akRBtwPE_g&oe=642284A3",
                "https://www.youtube.com/watch?v=ZHAjHb8-R48",
                "https://boofcv.org/images/thumb/3/35/Example_rendered_qrcode.png/400px-Example_rendered_qrcode.png"
            )
        )
        events.add(
            Event(
                "https://scontent.fhan2-4.fna.fbcdn.net/v/t39.30808-6/269745683_3090619904540829_1103863699949605152_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=e3f864&_nc_ohc=oq3iNPBgUMkAX8Oej0S&_nc_ht=scontent.fhan2-4.fna&oh=00_AfDdCSKHqStsNem4M4QAo8FEhTHCvc13qsnScx2qqLZfiQ&oe=63FF879C",
                "https://www.youtube.com/watch?v=F0B7HDiY-10",
                "https://www.crossref.org/wp/labs/uploads/sample_qr_code.jpg"
            )
        )
        events.add(
            Event(
                "https://scontent.fhan2-4.fna.fbcdn.net/v/t39.30808-6/241192184_3019690608300426_3557326503630887503_n.jpg?_nc_cat=100&ccb=1-7&_nc_sid=e3f864&_nc_ohc=ANpJ678kCrUAX8oa6Bb&_nc_ht=scontent.fhan2-4.fna&oh=00_AfBv1nrGqkSO94nSz__lFUhicbX_NKzZFwBwrN_qQuqVGA&oe=63FF4424",
                "https://www.youtube.com/watch?v=dpMVLrdzhuY",
                "https://www.eslkidsgames.com/wp-content/uploads/2020/10/QR-Code-Roleplay-Card-Example.png"
            )
        )
        events.add(
            Event(
                "https://scontent.fhan2-4.fna.fbcdn.net/v/t1.6435-9/80359336_2519462658323226_1108265230686748672_n.jpg?_nc_cat=110&ccb=1-7&_nc_sid=e3f864&_nc_ohc=8XRCdwr17AoAX-lnFa2&_nc_ht=scontent.fhan2-4.fna&oh=00_AfBxJWkorXuuuRWW-YftIuaGkcU0IU8qV4hCT67WXY6FjQ&oe=6422A687",
                "https://www.youtube.com/watch?v=K-tom2b8UFo",
                "https://blog.hubspot.com/hs-fs/hubfs/Google%20Drive%20Integration/DRAFT%20how%20to%20get%20a%20qr%20code.png?width=166&height=166&name=DRAFT%20how%20to%20get%20a%20qr%20code.png"
            )
        )
    }

    private fun initView() {
        imgBanner = findViewById(R.id.imageView)
        youTubePlayerView = findViewById(R.id.youtubePlayerView)
        imgQRCode = findViewById(R.id.imgQR)
        cvQRCode = findViewById(R.id.cvQR)
        initYoutubeView()
    }

    private fun initYoutubeView() {
        val iFramePlayerOptions: IFramePlayerOptions =
            IFramePlayerOptions.Builder().controls(0).rel(0).ivLoadPolicy(3).ccLoadPolicy(1).build()
        lifecycle.addObserver(youTubePlayerView!!)
        youTubePlayerView!!.visibility = View.GONE
        youTubePlayerView!!.enableAutomaticInitialization = false
        youTubePlayerView!!.initialize(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {}
        }, true, iFramePlayerOptions)
    }

    private fun showEvent() {
        if (currentIndex >= events.size) {
            currentIndex = 0
        }
        youTubePlayerView!!.visibility = View.GONE
        val event = events[currentIndex]
        if (!TextUtils.isEmpty(event.imageUrl)) {
            // display image for 5 seconds
            Glide.with(this@DisplayActivity).load(event.qrCode).into(imgQRCode!!)
            Glide.with(this).load(event.imageUrl).listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    // if image fails to load, switch to next event immediately
                    currentIndex++
                    showEvent()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    cvQRCode!!.visibility = View.VISIBLE
                    handler.postDelayed({

                        // switch to next event
                        currentIndex++
                        showVideo(event.videoUrl!!)
                    }, IMAGE_DISPLAY_TIME.toLong())
                    return false
                }
            }).into(imgBanner!!)
        }
    }

    private fun showVideo(videoUrl: String) {
        // display video
        if (!TextUtils.isEmpty(videoUrl)) {
            cvQRCode!!.visibility = View.GONE
            youTubePlayerView!!.enterFullScreen()
            youTubePlayerView!!.visibility = View.VISIBLE
            lifecycle.addObserver(youTubePlayerView!!)
            youTubePlayerView!!.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(extractYoutubeVideoId(videoUrl), 0f)
                    youTubePlayer.play()
                    youTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                        override fun onStateChange(
                            youTubePlayer: YouTubePlayer,
                            state: PlayerState
                        ) {
                            super.onStateChange(youTubePlayer, state)
                            if (state == PlayerState.ENDED) {
                                showEvent()
                            }
                        }
                    })
                }
            })
        }
    }

    fun extractYoutubeVideoId(youtubeUrl: String?): String {
        var videoId = ""
        if (youtubeUrl != null && youtubeUrl.trim { it <= ' ' }
                .isNotEmpty() && youtubeUrl.startsWith(
                "http"
            )
        ) {
            val expression =
                "^.*((youtu.be/)|(v/)|(/u/\\w/)|(embed/)|(watch\\?))\\??v?=?([^#&?]*).*"
            val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
            val matcher = pattern.matcher(youtubeUrl)
            if (matcher.matches()) {
                val groupIndex1 = matcher.group(7)
                if (groupIndex1 != null && groupIndex1.length == 11) videoId = groupIndex1
            }
        }
        return videoId
    }

    companion object {
        private const val IMAGE_DISPLAY_TIME = 5000 //1000 = 1s
    }
}