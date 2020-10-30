package com.app.eye.widgets.videoplayer;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.eye.R;

import static com.app.eye.widgets.videoplayer.Jzvd.STATE_PAUSE;
import static com.app.eye.widgets.videoplayer.Jzvd.STATE_PLAYING;

/**
 * 监听recycleView滑动状态，
 * 自动播放可见区域内的第一个视频
 */
public class AutoPlayScrollListener extends RecyclerView.OnScrollListener {

    private int firstVisibleItem = 0;
    private int lastVisibleItem = 0;
    private int visibleCount = 0;

    /**
     * 被处理的视频状态标签
     */
    private enum VideoTagEnum {

        /**
         * 自动播放视频
         */
        TAG_AUTO_PLAY_VIDEO,

        /**
         * 暂停视频
         */
        TAG_PAUSE_VIDEO
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            autoPlayVideo(recyclerView, VideoTagEnum.TAG_AUTO_PLAY_VIDEO);
        }// 滑动时暂停视频 autoPlayVideo(recyclerView, VideoTagEnum.TAG_PAUSE_VIDEO);

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
            firstVisibleItem = linearManager.findFirstVisibleItemPosition();
            lastVisibleItem = linearManager.findLastVisibleItemPosition();
            visibleCount = lastVisibleItem - firstVisibleItem;
        }

    }

    /**
     * 循环遍历 可见区域的播放器
     * 然后通过 getLocalVisibleRect(rect)方法计算出哪个播放器完全显示出来
     * <p>
     * getLocalVisibleRect相关链接：http://www.cnblogs.com/ai-developers/p/4413585.html
     *
     * @param recyclerView
     * @param handleVideoTag 视频需要进行状态
     */
    private void autoPlayVideo(RecyclerView recyclerView, VideoTagEnum handleVideoTag) {
        for (int i = 0; i < visibleCount; i++) {
            if (recyclerView != null && recyclerView.getChildAt(i) != null && recyclerView.getChildAt(i).findViewById(R.id.jzvd) != null) {
                View childAt = recyclerView.getChildAt(i);
                View view = childAt.findViewById(R.id.jzvd);
                if (null != view && view instanceof Jzvd) {
                    JzvdStd player = (JzvdStd) view;
                    Rect rect = new Rect();
                    player.getLocalVisibleRect(rect);
                    int videoheight = player.getHeight();
                    if (rect.top == 0 && rect.bottom == videoheight) {
                        handleVideo(handleVideoTag, player);
                        // 跳出循环，只处理可见区域内的第一个播放器
                        break;
                    }
                }
            }
        }

    }

    /**
     * @param view
     * @return 当前视图可见比列
     */
    public static float getViewVisiblePercent(View view) {
        if (view == null) {
            return 0f;
        }
        float height = view.getHeight();
        Rect rect = new Rect();
        if (!view.getLocalVisibleRect(rect)) {
            return 0f;
        }
        float visibleHeight = rect.bottom - rect.top;
        return visibleHeight / height;
    }

    /**
     * 视频状态处理
     *
     * @param handleVideoTag 视频需要进行状态
     * @param pJzvdStd       JZVideoPlayer播放器
     */
    private void handleVideo(VideoTagEnum handleVideoTag, JzvdStd pJzvdStd) {
        switch (handleVideoTag) {
            case TAG_AUTO_PLAY_VIDEO:
                if ((pJzvdStd.state != STATE_PLAYING)) {
                    // 进行播放
                    pJzvdStd.startVideo();
                }
                break;
            case TAG_PAUSE_VIDEO:
                if ((pJzvdStd.state != STATE_PAUSE)) {
                    // 模拟点击,暂停视频
                    pJzvdStd.startButton.performClick();
                }
                break;
            default:
                break;
        }
    }


}