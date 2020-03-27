package com.example.finaltask;

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

import androidx.annotation.NonNull;

public class ImageBarnnerViewGroup extends ViewGroup {

    private int children;
    private int childwidth;
    private int childheight;

    private int x;
    private int index = 0;

    private Scroller scroller;

    private boolean isClick;

    private ImageBarnnerListener listener;

    public ImageBarnnerListener getListener() {
        return listener;
    }

    public void setListener(ImageBarnnerListener listener) {
        this.listener = listener;
    }

    public interface ImageBarnnerListener{
        void clickImageIndex(int pos);
    }

    private boolean isAuto = true;
    private Timer timer = new Timer();
    private TimerTask task;
    private Handler autoHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:

                    if (++index >= children){
                        index = 0;
                    }

                    scrollTo(childwidth * index,0);

                    break;
            }
        }
    };

    private void startAuto(){
        isAuto = true;
    }

    private void stopAuto(){
        isAuto = false;
    }

    public ImageBarnnerViewGroup(Context context) {
        super(context);
        initObj();
    }

    public ImageBarnnerViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initObj();
    }

    public ImageBarnnerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initObj();
    }

    private void initObj(){
        scroller = new Scroller(getContext());

        task = new TimerTask() {
            @Override
            public void run() {
                if (isAuto){
                    autoHandler.sendEmptyMessage(0);
                }
            }
        };

        timer.schedule(task,100,3000);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),0);
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        children = getChildCount();
        if (children == 0){
            setMeasuredDimension(0,0);
        }
        else {
            measureChildren(widthMeasureSpec,heightMeasureSpec);

            View view = getChildAt(0);
            childwidth = view.getMeasuredWidth();
            childheight = view.getMeasuredHeight();
            int width = view.getMeasuredWidth() * children;

            setMeasuredDimension(width,childheight);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                stopAuto();
                if (!scroller.isFinished()){
                    scroller.abortAnimation();
                }
                isClick = true;
                x = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getX();
                int distance = moveX - x;
                scrollBy(-distance,0);
                x = moveX;
                isClick = false;
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                index = (scrollX + childwidth / 2) / childwidth;
                if (index < 0){
                    index = 0;
                }
                else if (index > children - 1){
                    index = children -1;
                }

                if (isClick){
                    listener.clickImageIndex(index);
                }
                else {
                    int dx = index * childwidth - scrollX;

                    scroller.startScroll(scrollX,0,dx,0);
                    postInvalidate();
                }
                startAuto();
                //scrollTo(index * childwidth,0);
                break;
            default:

                break;
        }

        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed){

            int leftMargin = 0;

            for (int i = 0; i < children; i++) {
                View view = getChildAt(i);
                view.layout(leftMargin,0,leftMargin + childwidth,childheight);
                leftMargin += childwidth;
            }
        }
    }
}
