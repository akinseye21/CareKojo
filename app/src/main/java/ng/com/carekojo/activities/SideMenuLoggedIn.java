package ng.com.carekojo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import ng.com.carekojo.R;

public class SideMenuLoggedIn extends AppCompatActivity {

    TextView close;

    LinearLayout blogarticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_menu_logged_in);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SideMenuLoggedIn.this, LoggedInPage.class));
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });
        blogarticles = findViewById(R.id.blogarticles);
        blogarticles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SideMenuLoggedIn.this, BlogArticles.class));
            }
        });
    }
}