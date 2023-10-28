package ng.com.carekojo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ng.com.carekojo.R;

public class LoginPage extends AppCompatActivity {

    ImageView back;
    TextView forgotPassword;
    LinearLayout signup;
    RelativeLayout rellayoutlogin;
    Button login;
    ProgressBar progresslogin;
    TextInputEditText email, password;
    Boolean emailBool = false, passwordBool = false;

    SharedPreferences preferences;
    SharedPreferences.Editor myEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        myEdit = preferences.edit();

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
        rellayoutlogin = findViewById(R.id.relbtnlogin);
        rellayoutlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });
        progresslogin = findViewById(R.id.progressLogin);
    }

    private void check() {
        if (emailBool && passwordBool){
            loginFunction();
        }else{
            Toast.makeText(LoginPage.this, "Invalid entries", Toast.LENGTH_SHORT).show();
            email.setError("Wrong email address");
            password.setError("Wrong password");
        }
    }

    private void loginFunction() {
        progresslogin.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.carekojo.e4eweb.space/log_in/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String refresh = jsonObject.getString("refresh");
                            String access = jsonObject.getString("access");
                            String id = jsonObject.getString("id");
                            String email = jsonObject.getString("email");
                            String first_name = jsonObject.getString("first_name");
                            String last_name = jsonObject.getString("last_name");
                            String username = jsonObject.getString("username");
                            String picture = jsonObject.getString("picture");

                            myEdit.putString("refresh", refresh);
                            myEdit.putString("access", access);
                            myEdit.putString("id", id);
                            myEdit.putString("email", email);
                            myEdit.putString("first_name", first_name);
                            myEdit.putString("last_name", last_name);
                            myEdit.putString("username", username);
                            myEdit.putString("picture", picture);
                            myEdit.commit();

                            Intent intent = new Intent(LoginPage.this, LoggedInPage.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            progresslogin.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Login failed. Please try again", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        if (volleyError.networkResponse != null){
                            byte[] responseData = volleyError.networkResponse.data;
                            try {
                                JSONObject jsonObject = new JSONObject(new String(responseData));
                                String detail = jsonObject.getString("detail");
                                System.out.println("Login error = "+detail);
                                Toast.makeText(LoginPage.this, detail, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        volleyError.printStackTrace();
                        progresslogin.setVisibility(View.GONE);
                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("username", email.getText().toString());
                params.put("password", password.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.getCache().clear();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }
}