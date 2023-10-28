package ng.com.carekojo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import ng.com.carekojo.R;

public class SideMenuLoggedIn extends AppCompatActivity {

    TextView close, signout;
    TextView fullname, username, totalbalance;

    SharedPreferences preferences;
    String first_name, last_name, user_name;

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
        signout = findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(SideMenuLoggedIn.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.modal_logout);

                Button logout = dialog.findViewById(R.id.signout);
                logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //clear sharedpreference record
                        SharedPreferences sharedPreferences_clear = getSharedPreferences("Login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor_clear = sharedPreferences_clear.edit();
                        editor_clear.clear();
                        editor_clear.apply();
                        dialog.dismiss();
                        startActivity(new Intent(SideMenuLoggedIn.this, LoginPage.class));
                    }
                });

                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        first_name = preferences.getString("first_name", "");
        last_name = preferences.getString("last_name", "");
        user_name = preferences.getString("username", "");

        fullname = findViewById(R.id.fullname);
        fullname.setText(first_name+" "+last_name);
        username = findViewById(R.id.username);
        username.setText("@"+user_name.toLowerCase(Locale.ROOT));
        totalbalance = findViewById(R.id.totalbalance);

    }
}