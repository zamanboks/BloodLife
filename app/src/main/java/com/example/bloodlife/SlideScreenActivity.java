package com.example.bloodlife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SlideScreenActivity extends AppCompatActivity {

    private ViewPager slideViewPager;
    private LinearLayout dotsLayout;
    private SlideAdapter slideAdapter;
    private TextView[] mDots;
    private int mCurrentPage;
    private ImageView mStart;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_screen);

        //Check if applicatio is opened for first time
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        if (firstStart){

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstStart", false);
            editor.apply();

            slideViewPager = findViewById(R.id.slideViewPager);
            dotsLayout = findViewById(R.id.dotsLayout);
            mStart = findViewById(R.id.mStart);
            slideAdapter = new SlideAdapter(this);
            slideViewPager.setAdapter(slideAdapter);
            startBtn();
            addDotIndicator(0);
            slideViewPager.addOnPageChangeListener(viewListener);
        }
        else{
            Intent intent = new Intent(SlideScreenActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }
    private void startBtn() {
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SlideScreenActivity.this,MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    public void addDotIndicator(int position){
        mDots = new TextView[3];
        dotsLayout.removeAllViews();

        for (int i=0; i< mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(40);
            mDots[i].setTextColor(getResources().getColor(R.color.colorMy));

            dotsLayout.addView(mDots[i]);
        }
        if (mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorSelectd));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotIndicator(i);
            mCurrentPage = i;


            if (i == 2){
                mStart.setEnabled(true);
                mStart.setVisibility(View.VISIBLE);
            }
            else{

                mStart.setEnabled(false);
                mStart.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };


}