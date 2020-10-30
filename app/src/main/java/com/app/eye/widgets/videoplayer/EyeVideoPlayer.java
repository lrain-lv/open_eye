package com.app.eye.widgets.videoplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.app.eye.R;


public class EyeVideoPlayer extends JzvdStd {
    public EyeVideoPlayer(Context context) {
        super(context);
    }

    public EyeVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setAllControlsVisiblity(int topCon, int bottomCon, int startBtn, int loadingPro, int thumbImg, int bottomPro, int retryLayout) {
        super.setAllControlsVisiblity(topCon, bottomCon, startBtn, loadingPro, thumbImg, bottomPro, retryLayout);
        topContainer.setVisibility(GONE);
        bottomContainer.setVisibility(GONE);
        bottomProgressBar.setVisibility(GONE);
    }

    @Override
    public void onStateAutoComplete() {
        super.onStateAutoComplete();
        mRetryBtn.performClick();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int id = v.getId();
        if (id == R.id.surface_container) {
            bottomProgressBar.setVisibility(GONE);
        }
        return super.onTouch(v, event);
    }

    @Override
    public void dissmissControlView() {
        super.dissmissControlView();
        bottomProgressBar.setVisibility(View.GONE);
    }
}
