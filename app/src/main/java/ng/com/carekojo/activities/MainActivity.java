package ng.com.carekojo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import ng.com.carekojo.R;
import ng.com.carekojo.adapters.SliderAdapter;

public class MainActivity extends AppCompatActivity {
    private ViewPager mSlideViewPager;
    private SliderAdapter sliderAdapter;
    Timer timer;
    int Counter = 0;
    private ImageView[] mDots;

    TextView skip;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sliderAdapter = new SliderAdapter(MainActivity.this);

        mSlideViewPager = findViewById(R.id.slider);
        mSlideViewPager.setAdapter(sliderAdapter);

        next = findViewById(R.id.next);
        skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGuestPage();
            }
        });

        //adding timer for the images
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                mSlideViewPager.post(new Runnable() {
                    @Override
                    public void run() {
                        mSlideViewPager.setCurrentItem((mSlideViewPager.getCurrentItem()+1)%4);
                        Counter = Counter + 1;
                        if (Counter == 4){
                            Counter = 0;
                        }
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 3000, 3000);

        addDotIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);
    }

    public void addDotIndicator(int position){
        if (position==2){
            skip.setVisibility(View.INVISIBLE);
            next.setBackgroundResource(R.drawable.button_yello);
            next.setText("Get Started");
            next.setTextColor(Color.parseColor("#B72025"));
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToGuestPage();
                }
            });
        }else{
            skip.setVisibility(View.VISIBLE);
            next.setBackgroundResource(R.drawable.button_orange);
            next.setText("Next");
            next.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void goToGuestPage() {
        Intent i = new Intent(this, GuestPage.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //do nothing
    }
}