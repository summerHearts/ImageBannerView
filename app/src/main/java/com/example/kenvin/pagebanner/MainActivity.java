package com.example.kenvin.pagebanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ImageWriter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ImageBannerViewGroup.ImageBannerListener  {


    private ImageBannerViewGroup imageBannerViewGroup;

    private ImageBannerFragemntLayout imageBannerFragemntLayout;

    private int [] bannerNames = new int []{
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3,
            R.drawable.banner4,
            R.drawable.banner5,
            R.drawable.banner6,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        Constant.Screen_Width =  width;

        hasDotBannerView();
    }

    public void hasDotBannerView(){
        imageBannerFragemntLayout = (ImageBannerFragemntLayout)findViewById(R.id.imageBannerFragemntLayout);

        List<Bitmap> imagesArray = new ArrayList<>();
        for (int i = 0; i < bannerNames.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),bannerNames[i]);
            imagesArray.add(bitmap);
        }

        imageBannerFragemntLayout.addBitmaps(imagesArray);
    }

    public void  noDotBannerView(){

        //        imageBannerViewGroup = (ImageBannerViewGroup)findViewById(R.id.bannerGroup);

        imageBannerViewGroup.setImageBannerListener(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        for (int i = 0; i < bannerNames.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(width,400));
            imageView.setImageResource(bannerNames[i]);
            imageBannerFragemntLayout.addView(imageView);
        }
    }

    @Override
    public void imageClick(int position) {
        Toast.makeText(this,"点击了 " + position,Toast.LENGTH_SHORT).show();
    }
}
