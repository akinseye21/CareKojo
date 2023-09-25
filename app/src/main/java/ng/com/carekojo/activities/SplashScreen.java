package ng.com.carekojo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import ng.com.carekojo.R;

public class SplashScreen extends AppCompatActivity {

    RelativeLayout myrel;
    ImageView logo;

    protected static final int Timer_Runtime=3000;
    protected boolean mbActive;
    protected ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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

    public void onContinue(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_in);
    }

    @Override
    protected void onStart() {
        super.onStart();

        logo = findViewById(R.id.logo);
        myrel = findViewById(R.id.myrel);

        YoYo.with(Techniques.FadeIn)
                .duration(2000)
                .repeat(0)
                .playOn(myrel);
        YoYo.with(Techniques.SlideInLeft)
                .duration(2000)
                .repeat(0)
                .playOn(logo);

    }
}