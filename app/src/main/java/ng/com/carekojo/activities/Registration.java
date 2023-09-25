package ng.com.carekojo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import ng.com.carekojo.R;
import ng.com.carekojo.sheets.UserType;

public class Registration extends AppCompatActivity implements UserType.BottomSheetListener{

    ImageView back;
    RelativeLayout userType;
    TextView user;
    Button next;
    LinearLayout login;

    RelativeLayout firstView, patientRegistration, doctorRegistration, hospitalRegistration, pharmacyRegistration, verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        firstView = findViewById(R.id.firstView);
        patientRegistration = findViewById(R.id.patientRegistration);
        doctorRegistration = findViewById(R.id.doctorRegistration);
        hospitalRegistration = findViewById(R.id.hospitalRegistration);
        pharmacyRegistration = findViewById(R.id.pharmacyRegistration);
        verify = findViewById(R.id.verify);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        userType = findViewById(R.id.userType);
        userType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserType bottomSheet = new UserType();
                bottomSheet.show(getSupportFragmentManager(), "UserTypeBottomSheet");
            }
        });
        user = findViewById(R.id.user);
        next = findViewById(R.id.next);
        login = findViewById(R.id.signin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registration.this, LoginPage.class));
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the string in the usertype text
                String type = user.getText().toString();
                //show view based on the selection type
                if (type.equals("...")){
                    Toast.makeText(Registration.this, "You have not selected a user type", Toast.LENGTH_SHORT).show();
                }else if (type.equals("Patient")){
                    //show patient view
                    patientReg();
                }else if (type.equals("Doctor")){
                    //show doctors view
                    doctorReg();
                }else if (type.equals("Hospital")){
                    //show hospital view
                    hospitalReg();
                }else if (type.equals("Pharmacist")){
                    //show pharmacist view
                    pharmacyReg();
                }
            }
        });
    }

    @Override
    public void onButtonClicked(String text) {
        user.setText(text);
    }


    private void patientReg() {
        firstView.setVisibility(View.GONE);
        patientRegistration.setVisibility(View.VISIBLE);

        ImageView back = findViewById(R.id.back2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstView.setVisibility(View.VISIBLE);
                patientRegistration.setVisibility(View.GONE);
            }
        });
        Button next = findViewById(R.id.next1);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                patientRegistration.setVisibility(View.GONE);
                verify.setVisibility(View.VISIBLE);
                verifyUser();
            }
        });
    }

    private void doctorReg() {
        firstView.setVisibility(View.GONE);
        doctorRegistration.setVisibility(View.VISIBLE);

        ImageView back = findViewById(R.id.back3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstView.setVisibility(View.VISIBLE);
                doctorRegistration.setVisibility(View.GONE);
            }
        });
        Button next = findViewById(R.id.next2);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doctorRegistration.setVisibility(View.GONE);
                verify.setVisibility(View.VISIBLE);
                verifyUser();
            }
        });
    }

    private void hospitalReg() {
        firstView.setVisibility(View.GONE);
        hospitalRegistration.setVisibility(View.VISIBLE);

        ImageView back = findViewById(R.id.back4);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstView.setVisibility(View.VISIBLE);
                hospitalRegistration.setVisibility(View.GONE);
            }
        });
    }

    private void pharmacyReg() {
        firstView.setVisibility(View.GONE);
        pharmacyRegistration.setVisibility(View.VISIBLE);

        ImageView back = findViewById(R.id.back5);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstView.setVisibility(View.VISIBLE);
                pharmacyRegistration.setVisibility(View.GONE);
            }
        });
    }

    private void verifyUser() {
        ImageView back = findViewById(R.id.back6);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registration.this, LoginPage.class));
            }
        });
        Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registration.this, LoginPage.class));
            }
        });
    }

}