package ng.com.carekojo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import ng.com.carekojo.R;

public class LoginPage extends AppCompatActivity {

    ImageView back;
    TextView forgotPassword;
    LinearLayout signup;
    Button login;
    TextInputEditText email, password;
    Boolean emailBool = false, passwordBool = false;

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
        email = findViewById(R.id.email);
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()){
                    emailBool = true;
                }else{
                    email.setError("Wrong input");
                    emailBool = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        password = findViewById(R.id.password);
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (password.getText().toString().length() > 0) {
                    passwordBool = true;
                }
                else{
                    passwordBool = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
        login = findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }

        });
    }

    private void check() {
        if (emailBool && passwordBool){
            Intent intent = new Intent(LoginPage.this, LoggedInPage.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }else{
            Toast.makeText(LoginPage.this, "Invalid entries", Toast.LENGTH_SHORT).show();
            email.setError("Wrong email address");
            password.setError("Wrong password");
        }
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }
}