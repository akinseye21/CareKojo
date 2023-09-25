package ng.com.carekojo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ng.com.carekojo.R;

public class LoginPage extends AppCompatActivity {

    ImageView back;
    TextView forgotPassword;
    LinearLayout signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPage.this, GuestPage.class));
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
        });
        forgotPassword = findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPage.this, ForgotPassword.class));
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        signup = findViewById(R.id.linsignup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPage.this, Registration.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }
}