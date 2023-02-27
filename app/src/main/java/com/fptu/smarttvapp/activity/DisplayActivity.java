package com.fptu.smarttvapp.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.fptu.smarttvapp.R;
import com.fptu.smarttvapp.model.Event;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DisplayActivity extends AppCompatActivity {

    private List<Event> events;
    private int currentIndex = 0;
    private ImageView imgBanner;
    private CardView cvQRCode;
    private ImageView imgQRCode;
    private YouTubePlayerView youTubePlayerView;
    private static final int IMAGE_DISPLAY_TIME = 5000; //1000 = 1s
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        initView();
        initData();
        showEvent();
    }

    private void initData() {
        events = new ArrayList<>();
        events.add(new Event("https://scontent.fhan2-3.fna.fbcdn.net/v/t1.6435-9/89013939_2577414925861332_2672208422551683072_n.jpg?_nc_cat=101&ccb=1-7&_nc_sid=e3f864&_nc_ohc=NYfprC2ft_QAX8Gjsmo&_nc_ht=scontent.fhan2-3.fna&oh=00_AfBEUNsnqvUNNGpWVeezLYJDmnPhmLV71HV_akRBtwPE_g&oe=642284A3", "https://www.youtube.com/watch?v=ZHAjHb8-R48", "https://boofcv.org/images/thumb/3/35/Example_rendered_qrcode.png/400px-Example_rendered_qrcode.png"));
        events.add(new Event("https://scontent.fhan2-4.fna.fbcdn.net/v/t39.30808-6/269745683_3090619904540829_1103863699949605152_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=e3f864&_nc_ohc=oq3iNPBgUMkAX8Oej0S&_nc_ht=scontent.fhan2-4.fna&oh=00_AfDdCSKHqStsNem4M4QAo8FEhTHCvc13qsnScx2qqLZfiQ&oe=63FF879C", "https://www.youtube.com/watch?v=F0B7HDiY-10", "https://www.crossref.org/wp/labs/uploads/sample_qr_code.jpg"));
        events.add(new Event("https://scontent.fhan2-4.fna.fbcdn.net/v/t39.30808-6/241192184_3019690608300426_3557326503630887503_n.jpg?_nc_cat=100&ccb=1-7&_nc_sid=e3f864&_nc_ohc=ANpJ678kCrUAX8oa6Bb&_nc_ht=scontent.fhan2-4.fna&oh=00_AfBv1nrGqkSO94nSz__lFUhicbX_NKzZFwBwrN_qQuqVGA&oe=63FF4424", "https://www.youtube.com/watch?v=dpMVLrdzhuY", "https://www.eslkidsgames.com/wp-content/uploads/2020/10/QR-Code-Roleplay-Card-Example.png"));
        events.add(new Event("https://scontent.fhan2-4.fna.fbcdn.net/v/t1.6435-9/80359336_2519462658323226_1108265230686748672_n.jpg?_nc_cat=110&ccb=1-7&_nc_sid=e3f864&_nc_ohc=8XRCdwr17AoAX-lnFa2&_nc_ht=scontent.fhan2-4.fna&oh=00_AfBxJWkorXuuuRWW-YftIuaGkcU0IU8qV4hCT67WXY6FjQ&oe=6422A687", "https://www.youtube.com/watch?v=K-tom2b8UFo", "https://blog.hubspot.com/hs-fs/hubfs/Google%20Drive%20Integration/DRAFT%20how%20to%20get%20a%20qr%20code.png?width=166&height=166&name=DRAFT%20how%20to%20get%20a%20qr%20code.png"));
    }

    private void initView() {
        imgBanner = findViewById(R.id.imageView);
        youTubePlayerView = findViewById(R.id.youtubePlayerView);
        imgQRCode = findViewById(R.id.imgQR);
        cvQRCode = findViewById(R.id.cvQR);
        initYoutubeView();
    }

    private void initYoutubeView() {
        IFramePlayerOptions iFramePlayerOptions = new IFramePlayerOptions.Builder().controls(1).rel(0).ivLoadPolicy(3).ccLoadPolicy(1).build();

        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.setVisibility(View.GONE);
        youTubePlayerView.setEnableAutomaticInitialization(false);
        youTubePlayerView.initialize(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
            }
        }, true, iFramePlayerOptions);
    }


    private void showEvent() {
        if (currentIndex >= events.size()) {
            currentIndex = 0;
        }
        youTubePlayerView.setVisibility(View.GONE);
        Event event = events.get(currentIndex);
        if (!TextUtils.isEmpty(event.getImageUrl())) {
            // display image for 5 seconds
            Glide.with(DisplayActivity.this).load(event.getQrCode()).into(imgQRCode);
            Glide.with(this).load(event.getImageUrl()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    // if image fails to load, switch to next event immediately
                    currentIndex++;
                    showEvent();
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    cvQRCode.setVisibility(View.VISIBLE);
                    handler.postDelayed(() -> {
                        // switch to next event
                        currentIndex++;
                        showVideo(event.getVideoUrl());
                    }, IMAGE_DISPLAY_TIME);
                    return false;
                }
            }).into(imgBanner);
        }
    }

    private void showVideo(String videoUrl) {
        // display video
        if (!TextUtils.isEmpty(videoUrl)) {
            cvQRCode.setVisibility(View.GONE);
            youTubePlayerView.enterFullScreen();
            youTubePlayerView.setVisibility(View.VISIBLE);
            getLifecycle().addObserver(youTubePlayerView);
            youTubePlayerView.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
                @Override
                public void onYouTubePlayer(@NonNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.loadVideo(extractYoutubeVideoId(videoUrl), 0);
                    youTubePlayer.play();

                    youTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState state) {
                            super.onStateChange(youTubePlayer, state);
                            if (state == PlayerConstants.PlayerState.ENDED) {
                                showEvent();
                            }
                        }
                    });
                }
            });
        }
    }

    public String extractYoutubeVideoId(String youtubeUrl) {
        String videoId = "";
        if (youtubeUrl != null && youtubeUrl.trim().length() > 0 && youtubeUrl.startsWith("http")) {
            String expression = "^.*((youtu.be/)|(v/)|(/u/\\w/)|(embed/)|(watch\\?))\\??v?=?([^#&?]*).*";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(youtubeUrl);
            if (matcher.matches()) {
                String groupIndex1 = matcher.group(7);
                if (groupIndex1 != null && groupIndex1.length() == 11) videoId = groupIndex1;
            }
        }
        return videoId;
    }
}
