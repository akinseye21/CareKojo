package ng.com.carekojo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import ng.com.carekojo.R;

public class SplashScreen extends AppCompatActivity {

    RelativeLayout myrel;
    ImageView logo, carekojoround;
    LottieAnimationView myLottie;

    protected static final int Timer_Runtime=14000;
    protected boolean mbActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        myLottie = findViewById(R.id.myLottie);
        logo = findViewById(R.id.logo);
        carekojoround = findViewById(R.id.carekojoround);
        myrel = findViewById(R.id.myrel);
        // Start the reveal animation
        startRevealAnimation(); //3 sec

        final Thread timerThread = new Thread(){
            @Override
            public void run(){
                mbActive = true;
                try{
                    int waited = 0;
                    while(mbActive && (waited < Timer_Runtime)){
                        sleep(200);
                        if (mbActive){
                            waited += 200;
//                            updateProgress(waited);
                        }
                    }
                }catch(InterruptedException e){
                    //error
                }finally{
                    onContinue();
                }
            }
        };
        timerThread.start();
    }

    private void startRevealAnimation() {
        Animation revealAnimation = AnimationUtils.loadAnimation(this, R.anim.reveal_animation);
        MyAnimationListener animationListener = new MyAnimationListener();
        // Attach the animation listener to the animation
        revealAnimation.setAnimationListener(animationListener);
        logo.startAnimation(revealAnimation);

    }

    public class MyAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            // Animation started
            myrel.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            // Animation ended
            // Put your code here to execute when the animation is finished
            myrel.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInUp)
                    .duration(5000)
                    .repeat(0)
                    .playOn(myrel);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //show the lottie file
                    myLottie.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            myLottie.setVisibility(View.VISIBLE);
                            YoYo.with(Techniques.SlideOutDown)
                                    .duration(1500)
                                    .repeat(0)
                                    .playOn(myrel);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Animation revealAnimation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.zoom_in);
                                    MyAnimationListener animationListener = new MyAnimationListener();
                                    revealAnimation.setAnimationListener(animationListener);
                                    logo.startAnimation(revealAnimation);

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Animation revealAnimation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.zoom_out);
                                            MyAnimationListener animationListener = new MyAnimationListener();
                                            revealAnimation.setAnimationListener(animationListener);
                                            carekojoround.startAnimation(revealAnimation);
                                        }
                                    }, 500);

                                }
                            }, 2000);

                        }
                    }, 1000);

                }
            }, 5000);


        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // Animation repeated (if it's set to repeat)
        }
    }

    public void onContinue(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_in);
    }

}