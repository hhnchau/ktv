package com.vk2.touchsreentab.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.vk2.touchsreentab.R;

public class YtExtractor {
    public interface OnYtListener {
        void yt(Link link);
    }

    public static class Builder {
        private Context context;
        private String videoId;
        private OnYtListener listener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setVideoId(String videoId) {
            this.videoId = videoId;
            return this;
        }

        public Builder setListener(OnYtListener listener) {
            this.listener = listener;
            return this;
        }

        @SuppressLint("SetJavaScriptEnabled")
        public void build() {
            final Link link = new Link();
            final Dialog dialog = new Dialog(context, R.style.MyExtractor);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.yt_extractor);
            Window window = dialog.getWindow();
            if (window != null) {
                window.getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                dialog.show();
            }

            final WebView wv = dialog.findViewById(R.id.wv);
            WebSettings set = wv.getSettings();
            set.setJavaScriptEnabled(true);
            set.setUseWideViewPort(true);
            set.setLoadWithOverviewMode(true);
            set.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
            set.setCacheMode(WebSettings.LOAD_NO_CACHE);
            set.setPluginState(WebSettings.PluginState.ON);
            set.setPluginState(WebSettings.PluginState.ON_DEMAND);
            set.setAllowContentAccess(true);
            set.setAllowFileAccess(true);
            wv.setLayerType(View.LAYER_TYPE_NONE, null);
            wv.setWebChromeClient(new WebChromeClient());
            wv.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    long delta = 100;
                    long downTime = SystemClock.uptimeMillis();
                    float x = view.getLeft() + (view.getWidth() / 2);
                    float y = view.getTop() + (view.getHeight() / 2);

                    MotionEvent tapDownEvent = MotionEvent.obtain(downTime, downTime + delta, MotionEvent.ACTION_DOWN, x, y, 0);
                    tapDownEvent.setSource(InputDevice.SOURCE_CLASS_POINTER);
                    MotionEvent tapUpEvent = MotionEvent.obtain(downTime, downTime + delta + 2, MotionEvent.ACTION_UP, x, y, 0);
                    tapUpEvent.setSource(InputDevice.SOURCE_CLASS_POINTER);

                    view.dispatchTouchEvent(tapDownEvent);
                    view.dispatchTouchEvent(tapUpEvent);
                }

                boolean isCallback = false;

                @Nullable
                @Override
                public WebResourceResponse shouldInterceptRequest(WebView view, final String url) {
                    if (!isCallback)
                        if (url.matches(".*googlevideo.com/videoplayback.*")) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    if (url.contains("&mime=video")) {
                                        if (link.getLink() == null) {
                                            String lk = url.replaceAll("&range=[\\d-]*&", "&");
                                            link.setLink(lk);
                                            Log.d("YtLink", "Video Normal: " + lk);
                                        } else {
                                            String lk = url.replaceAll("&range=[\\d-]*&", "&");
                                            link.setLinkQuality(lk);
                                            Log.d("YtLink", "Video Quality: " + lk);
                                            if (listener != null) {
                                                listener.yt(link);
                                            }
                                            isCallback = !isCallback;
                                            wv.stopLoading();
                                            //wv.clearCache(true);
                                            //wv.clearFormData();
                                            //wv.clearHistory();
                                            //wv.clearView();
                                            wv.destroy();
                                            dialog.dismiss();
                                        }
                                    } else if (url.contains("&mime=audio") && link.getAudio() == null) {
                                        String lk = url.replaceAll("&range=[\\d-]*&", "&");
                                        link.setAudio(lk);
                                        Log.d("YtLink", "Audio: " + lk);
                                    }
                                }
                            });
                        }
                    return super.shouldInterceptRequest(view, url);
                }
            });
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    wv.loadUrl("https://www.youtube.com/watch?v=" + videoId);
                }
            });
        }
    }


    public static class Link {
        private String audio;
        private String link;
        private String linkQuality;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getLinkQuality() {
            return linkQuality;
        }

        public void setLinkQuality(String linkQuality) {
            this.linkQuality = linkQuality;
        }

        public String getAudio() {
            return audio;
        }

        public void setAudio(String audio) {
            this.audio = audio;
        }
    }
}
