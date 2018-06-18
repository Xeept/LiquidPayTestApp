package com.reuben.liquidpaytestapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.reuben.liquidpaytestapp.R;
import com.reuben.liquidpaytestapp.adapters.SimpleIntroductionPagerAdapter;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends FragmentActivity {
    private ViewPager mPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_introduction);
        SimpleIntroductionPagerAdapter mAdapter = new SimpleIntroductionPagerAdapter(getSupportFragmentManager());

        mPager = findViewById(R.id.viewpager);
        mPager.setAdapter(mAdapter);

        //Find and initialize the viewpager indicator
        CircleIndicator circleIndicator = findViewById(R.id.indicator);
        circleIndicator.setViewPager(mPager);

        //Initialize the skip and next button
        Button skipButton = findViewById(R.id.button_skip);
        skipButton.setOnClickListener(skipButtonListener);

        Button nextButton = findViewById(R.id.button_next);
        nextButton.setOnClickListener(nextButtonListener);
    }

    //Setting up the listener for the button Skip
    View.OnClickListener skipButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startHomeActivity();
        }
    };

    //Setting up the listener for the button Next
    View.OnClickListener nextButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mPager.getCurrentItem() + 1 < mPager.getAdapter().getCount()) {
                //If yet to reach the end of the item list, move to the next item.
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
            } else {
                //At the end of the item list, the developer has the option to either move onwards to the next activity screen or skip the implementation.
                startHomeActivity();
            }
        }
    };

    private void startHomeActivity() {
        //To the next activity and finish the current one
        Intent intent = new Intent(this, UserHomeActivity.class);
        startActivity(intent);
        finish();
    }
}
