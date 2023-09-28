package ng.com.carekojo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import ng.com.carekojo.R;

public class SideMenu extends AppCompatActivity {

    TextView close;
    LinearLayout login;
    LinearLayout signup;
    LinearLayout aboutus;
    LinearLayout blog;
    LinearLayout support;
    LinearLayout faq;
    LinearLayout terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_menu);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        close = findViewById(R.id.close);
        login = findViewById(R.id.linlogin);
        signup = findViewById(R.id.linsignup);
        aboutus = findViewById(R.id.linaboutus);
        blog = findViewById(R.id.linblog);
        support = findViewById(R.id.linsupport);
        faq = findViewById(R.id.linfaq);
        terms = findViewById(R.id.linterms);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SideMenu.this, GuestPage.class));
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SideMenu.this, LoginPage.class));
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SideMenu.this, Registration.class));
            }
        });
    }
}