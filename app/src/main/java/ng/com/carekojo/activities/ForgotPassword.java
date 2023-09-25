package ng.com.carekojo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import ng.com.carekojo.R;

public class ForgotPassword extends AppCompatActivity {

    ImageView back, back6;
    Button reset;
    RelativeLayout firstView, layoutReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        firstView = findViewById(R.id.firstView);
        layoutReset = findViewById(R.id.layoutReset);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPassword.this, LoginPage.class));
            }
        });

        reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstView.setVisibility(View.GONE);
                layoutReset.setVisibility(View.VISIBLE);

                back6 = findViewById(R.id.back6);
                back6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(ForgotPassword.this, LoginPage.class));
//                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                    }
                });
                Button login = findViewById(R.id.login);
                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(ForgotPassword.this, LoginPage.class));
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }
}