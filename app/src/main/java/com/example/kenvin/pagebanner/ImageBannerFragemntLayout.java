package com.example.kenvin.pagebanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by Kenvin on 2017/11/1.
 */

public class ImageBannerFragemntLayout extends FrameLayout{

    private ImageBannerViewGroup imageBannerViewGroup;

    private LinearLayout linearLayout;


    public ImageBannerFragemntLayout(@NonNull Context context) {
        super(context);
        initImageBannerViewGroup();
        initDotLinnerLayout();
    }

    public void addBitmaps(List<Bitmap> list){
        for (int i = 0; i < list.size(); i++) {
            Bitmap bitmap = list.get(i);
            addBitmapToViewGroup(bitmap);
            addDotToLinerLayout();
        }
    }

    private void addDotToLinerLayout(){

        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams linerLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(linerLayout);
        linerLayout.setMargins(5,5,5,5);
        imageView.setImageResource(R.drawable.dot_noraml);
        linearLayout.addView(imageView);
    }

    private void  addBitmapToViewGroup(Bitmap bitmap){
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,400));
        imageView.setImageBitmap(bitmap);
        imageBannerViewGroup.addView(imageView);
    }

    //初始化轮播功能核心类
    private void initImageBannerViewGroup() {

        imageBannerViewGroup = new ImageBannerViewGroup(getContext());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
        imageBannerViewGroup.setLayoutParams(lp);
        addView(imageBannerViewGroup);
    }

    //初始化底部的圆点布局
    private void  initDotLinnerLayout(){
        linearLayout = new LinearLayout(getContext());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,40);
        linearLayout.setLayoutParams(lp);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setBackgroundColor(Color.GRAY);
        addView(linearLayout);

        FrameLayout.LayoutParams  lps = (LayoutParams) linearLayout.getLayoutParams();
        lps.gravity = Gravity.BOTTOM;
        linearLayout.setLayoutParams(lps);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            linearLayout.setAlpha(0.5f);
        }else {
            linearLayout.getBackground().setAlpha(100);
        }
    }

    public ImageBannerFragemntLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initImageBannerViewGroup();
        initDotLinnerLayout();
    }

    public ImageBannerFragemntLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initImageBannerViewGroup();
        initDotLinnerLayout();
    }


}
