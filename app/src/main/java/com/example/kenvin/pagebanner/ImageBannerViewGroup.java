package com.example.kenvin.pagebanner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kenvin on 2017/11/1.
 */

public class ImageBannerViewGroup extends ViewGroup {


    private int  childrenSize;

    private int childSizeWidth;

    private int  x;

    private int  index;

    private Scroller scroller;

    private boolean isAuto = true;

    private Timer timer = new Timer();

    private TimerTask timerTask;

    private boolean isClickOperation;

    public interface ImageBannerListener{
        void imageClick(int position);
    }

    public ImageBannerListener imageBannerListener;


    public ImageBannerListener getImageBannerListener() {
        return imageBannerListener;
    }

    public void setImageBannerListener(ImageBannerListener imageBannerListener) {
        this.imageBannerListener = imageBannerListener;
    }

    private Handler autoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    if (++index>= childrenSize){
                        index = 0;
                    }
                    scrollTo(childSizeWidth*index,0);
                    break;
            }
        }
    };


    private void startAutoCycleImage(){
        isAuto = true;

    }

    private void stopAutoCycleImage(){
        isAuto = false;

    }

    public ImageBannerViewGroup(Context context) {
        super(context);
        initScroller();
    }

    public ImageBannerViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initScroller();
    }

    public ImageBannerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initScroller();
    }

    public void  initScroller(){

        scroller = new Scroller(getContext());

        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (isAuto){
                    autoHandler.sendEmptyMessage(0);
                }
            }
        };

        timer.schedule(timerTask,100,3000);

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),0);
            invalidate();
        }
    }

    //测量方法

    /**
     *  由于需要测量ViewGroup的宽度和高度，就必须测量子视图的宽度和高度
     * 1: 求出子视图的个数
     * 2: 测量子视图的宽度和高度
     * 3：根绝子视图的宽度和高度，求出ViewGroup的宽度和高度
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        childrenSize = getChildCount();

        if (0 == childrenSize){

            setMeasuredDimension(0,0);
        }else {

            measureChildren(widthMeasureSpec,heightMeasureSpec);

            View view = getChildAt(0);
            childSizeWidth = view.getMeasuredWidth();
            int width  = view.getMeasuredWidth()*childrenSize;
            int height = view.getMeasuredHeight();

            setMeasuredDimension(width,height);
        }
    }

    /**
     *
     * @param changed    当ViewGroup的布局发生改变的时候为true,没有发生改变为false
     * @param l   左
     * @param t   上
     * @param r   右
     * @param b   下
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed){

            int leftMargin = 0;

            for (int i = 0; i < childrenSize; i++) {
                View view = getChildAt(i);
                view.layout(leftMargin,0,leftMargin+view.getMeasuredWidth(),view.getMeasuredHeight());
                leftMargin += view.getMeasuredWidth();
            }
        }
    }


    /**
     * 事件的传递过程 触摸事件拦截,真正处理的是onTouchEvent方法
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                stopAutoCycleImage();
                if (!scroller.isFinished()){
                    scroller.abortAnimation();
                }
                isClickOperation = true;
                x = (int) event.getX();
            }
            break;
            case MotionEvent.ACTION_MOVE:{
                int moveX =  (int) event.getX();
                int distance = moveX - x;
                scrollBy(-distance,0);
                x = moveX;
                isClickOperation = false;
            }
            break;
            case MotionEvent.ACTION_UP:{

                if (isClickOperation){
                    getImageBannerListener().imageClick(index);
                }else {
                    int scrollX = getScrollX();
                    index = (scrollX+ childSizeWidth/2)/childSizeWidth;
                    if (index < 0){ //已经滑动到第一张
                        index = 0 ;
                    }else if (index > childrenSize -1){
                        index = childrenSize-1;
                    }

//                scrollTo(index*childSizeWidth,0);

                    int dx  = index*childSizeWidth - scrollX;
                    scroller.startScroll(scrollX,0,dx,0);
                    postInvalidate();
                }
                startAutoCycleImage();
            }
            break;
        }
        return true;//已经处理好了事件
    }
}
