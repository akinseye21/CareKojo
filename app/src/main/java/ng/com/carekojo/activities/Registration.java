package ng.com.carekojo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ng.com.carekojo.R;
import ng.com.carekojo.adapters.LGAAdapter;
import ng.com.carekojo.adapters.StateAdapter;

public class Registration extends AppCompatActivity {

    ImageView back;
    RelativeLayout userType;
    TextView user, deliveryMode;
    Button next;
    LinearLayout login;
    RelativeLayout firstView, patientRegistration, doctorRegistration, hospitalRegistration, pharmacyRegistration, verify;
    String doctorID="", firstName="", lastName="", username="", email="", phone="", facilityName="", speciality="", state="", lga="", password="", confrimPassword="";
    String patientfirstName="", patientlastName="", patientusername="", patientemail="", patientphone="", patientstate="", patientlga="", patientpassword="", patientconfrimPassword="";
    String hospitalname="", hospitalID="", hospitalusername="", hospitalemail="", hospitalphone="", hospitalstate="", hospitallga="", hospitalpassword="", hospitalconfrimPassword="";
    String pharmacyname="", pharmacyID="", pharmacyusername="", pharmacyemail="", pharmacyphone="", pharmacystate="", pharmacylga="", pharmacypassword="", pharmacyconfrimPassword="";
    Boolean conductsDelivery = false;

    RelativeLayout nextButton, registerPatient, registerHospital, registerPharmacy;
    ProgressBar progressDoctorReg, progressPatientRegistration, progressHospitalRegistration, progressPharmacyRegistration;

    ArrayList<String> arrayState = new ArrayList<>();
    ArrayList<String> arrayLGA = new ArrayList<>();
    int lgalength;

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

        getStatesandLga();

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
                showDialog();
            }
        });
        user = findViewById(R.id.user);
        deliveryMode = findViewById(R.id.txtPharmacySelectedDelivery);
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
                }else if (type.equals("Hospitals/Clinics")){
                    //show hospital view
                    hospitalReg();
                }else if (type.equals("Pharmacist")){
                    //show pharmacist view
//                    pharmacyReg();
                }
            }
        });

    }



    private void showDialog() {
        Dialog dialog = new Dialog(Registration.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.modal_user_sheet);

        TextView patient = dialog.findViewById(R.id.patient);
        TextView doctor = dialog.findViewById(R.id.doctor);
        TextView hospital = dialog.findViewById(R.id.hospital);
        TextView pharmacist = dialog.findViewById(R.id.pharmacist);

        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setText("Patient");
                dialog.dismiss();
            }
        });
        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setText("Doctor");
                dialog.dismiss();
            }
        });
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setText("Hospitals/Clinics");
                dialog.dismiss();
            }
        });
        pharmacist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setText("Pharmacist");
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
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

        TextInputEditText edtFirstName, edtLastName, edtUsername, edtEmail, edtPhone, edtPassword, edtConfirmPassword;
        RelativeLayout selectState, selectLGA;
        ProgressBar lgaloadingpatient;
        TextView txtState, txtLGA;
        ImageView checkEmail, checkPhone;
        Button regPatient;

        lgaloadingpatient = findViewById(R.id.lgaloadingpatient);
        edtFirstName = findViewById(R.id.patientFirstName);
        edtFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtFirstName.getText().toString().length()>0){
                    patientfirstName = edtFirstName.getText().toString().trim();
                }else{
                    //do nothing
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtLastName = findViewById(R.id.patientLastName);
        edtLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtLastName.getText().toString().length()>0){
                    patientlastName = edtLastName.getText().toString().trim();
                }else{
                    //do nothing
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtUsername = findViewById(R.id.patientUsername);
        edtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtUsername.getText().toString().length()>0){
                    patientusername = edtUsername.getText().toString().trim();
                }else{
                    //do nothing
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtEmail = findViewById(R.id.edtPatientEmail);
        checkEmail = findViewById(R.id.checkPatientEmail);
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString().trim()).matches()){
                    patientemail = edtEmail.getText().toString().trim();
                    checkEmail.setVisibility(View.VISIBLE);
                }else{
                    checkEmail.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtPhone = findViewById(R.id.edtPatientPhoneNumber);
        checkPhone = findViewById(R.id.checkPatientPhone);
        edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtPhone.getText().toString().length()>10){
                    patientphone = edtPhone.getText().toString().trim();
                    checkPhone.setVisibility(View.VISIBLE);
                }else{
                    checkPhone.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //select state
        selectState = findViewById(R.id.selectStatePatient);
        txtState = findViewById(R.id.selectedStatePatient);
        selectState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show the dialog
                Dialog dialog = new Dialog(Registration.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.modal_states);

                ListView listView = dialog.findViewById(R.id.listView);
                StateAdapter stateAdapter = new StateAdapter(Registration.this, arrayState, "patient", dialog);
                listView.setAdapter(stateAdapter);

                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        patientstate = txtState.getText().toString();
                    }
                });
            }
        });
        //select lga
        selectLGA = findViewById(R.id.selectLGAPatient);
        txtLGA = findViewById(R.id.selectedLgaPatient);
        selectLGA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (patientstate.equals("") || patientstate.equals("...")){
                    //no state selected
                    Toast.makeText(Registration.this, "Kindly select a state", Toast.LENGTH_SHORT).show();
                }else{
                    lgaloadingpatient.setVisibility(View.VISIBLE);
                    arrayLGA.clear();
                    getLGA(patientstate, lgaloadingpatient, "patient", txtLGA);
                }
            }
        });



        edtPassword = findViewById(R.id.edtPasswordPatient);
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtPassword.getText().length()>0){
                    patientpassword = edtPassword.getText().toString().trim();
                }else{
                    //do nothing
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtConfirmPassword = findViewById(R.id.edtConfirmPasswordPatient);
        edtConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!edtConfirmPassword.getText().toString().equals(patientpassword)){
                    edtConfirmPassword.setError("Password do not match");
                }else{
                    patientconfrimPassword = edtConfirmPassword.getText().toString().trim();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        registerPatient = findViewById(R.id.signupPatient);
        progressPatientRegistration = findViewById(R.id.progressPatientReg);
        regPatient = findViewById(R.id.nexterPatient);
        regPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!patientfirstName.equals("") && !patientlastName.equals("") && !patientusername.equals("") && !patientemail.equals("")
                        && !patientphone.equals("") && (patientpassword.equals(patientconfrimPassword))){
                    // register the Doctor
                    registersPatient();
                }else{
                    Toast.makeText(Registration.this, "Please check some fields", Toast.LENGTH_SHORT).show();
                    System.out.println(firstName+", "+lastName+", "+username+", "+email+", "+phone+", "+password);
                }
            }
        });
        registerPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!patientfirstName.equals("") && !patientlastName.equals("") && !patientusername.equals("") && !patientemail.equals("")
                        && !patientphone.equals("") && (patientpassword.equals(patientconfrimPassword))){
                    // register the Doctor
                    registersPatient();
                }else{
                    Toast.makeText(Registration.this, "Please check some fields", Toast.LENGTH_SHORT).show();
                    System.out.println(firstName+", "+lastName+", "+username+", "+email+", "+phone+", "+password);
                }

            }
        });
    }

    private void doctorReg() {
        firstView.setVisibility(View.GONE);
        doctorRegistration.setVisibility(View.VISIBLE);

        //for Doctors Registration
        ImageView back;
        TextInputEditText edtDoctorId, edtFirstName, edtLastName, edtUsername, edtEmail, edtPhone, edtMedicalFacilityName, edtPassword, edtConfirmPassword;
        RelativeLayout selectSpeciality, selectState, selectLGA;
        ProgressBar lgaloadingdoctor;
        TextView txtSpeciality, txtState, txtLGA;
        ImageView checkEmail, checkPhone;
        Button regDoc;

        back = findViewById(R.id.back3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstView.setVisibility(View.VISIBLE);
                doctorRegistration.setVisibility(View.GONE);
            }
        });

        lgaloadingdoctor = findViewById(R.id.lgaloadingdoctor);
        edtDoctorId = findViewById(R.id.edtDoctorID);
        edtDoctorId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtDoctorId.getText().toString().length()>0){
                    doctorID = edtDoctorId.getText().toString().trim();
                }else{
                    //do nothing
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtFirstName = findViewById(R.id.edtFirstName);
        edtFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtFirstName.getText().toString().length()>0){
                    firstName = edtFirstName.getText().toString().trim();
                }else{
                    //do nothing
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtLastName = findViewById(R.id.edtLastName);
        edtLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtLastName.getText().toString().length()>0){
                    lastName = edtLastName.getText().toString().trim();
                }else{
                    //do nothing
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtUsername = findViewById(R.id.edtUsername);
        edtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtUsername.getText().toString().length()>0){
                    username = edtUsername.getText().toString().trim();
                }else{
                    //do nothing
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtEmail = findViewById(R.id.edtEmail);
        checkEmail = findViewById(R.id.checkEmail);
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString().trim()).matches()){
                    email = edtEmail.getText().toString().trim();
                    checkEmail.setVisibility(View.VISIBLE);
                }else{
                    checkEmail.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtPhone = findViewById(R.id.edtPhoneNumber);
        checkPhone = findViewById(R.id.checkPhone);
        edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtPhone.getText().toString().length()>10){
                    phone = edtPhone.getText().toString().trim();
                    checkPhone.setVisibility(View.VISIBLE);
                }else{
                    checkPhone.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtMedicalFacilityName = findViewById(R.id.edtMedicalFacilityname);
        edtMedicalFacilityName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtMedicalFacilityName.getText().toString().length()>0){
                    facilityName = edtMedicalFacilityName.getText().toString().trim();
                }else{
                    //do nothing
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //select speciality
        selectSpeciality = findViewById(R.id.selectSpeciality);
        txtSpeciality = findViewById(R.id.selectedSpeciality);
        selectSpeciality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show the dialog
                Dialog dialog = new Dialog(Registration.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.modal_specialities);

                TextView generalmedicine = dialog.findViewById(R.id.generalmedicine);
                TextView cardiologist = dialog.findViewById(R.id.cardiologist);
                TextView dentist = dialog.findViewById(R.id.dentist);
                TextView vestmedicine = dialog.findViewById(R.id.vestmedicine);
                TextView dermatologist = dialog.findViewById(R.id.dermatologist);
                TextView emergencymedicine = dialog.findViewById(R.id.emergencymedicine);
                TextView familymedicine = dialog.findViewById(R.id.familymedicine);
                TextView generalpractitioner = dialog.findViewById(R.id.generalpractitioner);

                generalmedicine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtSpeciality.setText(generalmedicine.getText().toString());
                        speciality = generalmedicine.getText().toString();
                        dialog.dismiss();
                    }
                });
                cardiologist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtSpeciality.setText(cardiologist.getText().toString());
                        speciality = cardiologist.getText().toString();
                        dialog.dismiss();
                    }
                });
                dentist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtSpeciality.setText(dentist.getText().toString());
                        speciality = dentist.getText().toString();
                        dialog.dismiss();
                    }
                });
                vestmedicine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtSpeciality.setText(vestmedicine.getText().toString());
                        speciality = vestmedicine.getText().toString();
                        dialog.dismiss();
                    }
                });
                dermatologist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtSpeciality.setText(dermatologist.getText().toString());
                        speciality = dermatologist.getText().toString();
                        dialog.dismiss();
                    }
                });
                emergencymedicine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtSpeciality.setText(emergencymedicine.getText().toString());
                        speciality = emergencymedicine.getText().toString();
                        dialog.dismiss();
                    }
                });
                familymedicine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtSpeciality.setText(familymedicine.getText().toString());
                        speciality = familymedicine.getText().toString();
                        dialog.dismiss();
                    }
                });
                generalpractitioner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtSpeciality.setText(generalpractitioner.getText().toString());
                        speciality = generalpractitioner.getText().toString();
                        dialog.dismiss();
                    }
                });

                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        });
        //select state
        selectState = findViewById(R.id.selectState);
        txtState = findViewById(R.id.selectedState);
        selectState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show the dialog
                Dialog dialog = new Dialog(Registration.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.modal_states);

                ListView listView = dialog.findViewById(R.id.listView);
                StateAdapter stateAdapter = new StateAdapter(Registration.this, arrayState, "doctor", dialog);
                listView.setAdapter(stateAdapter);

                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        state = txtState.getText().toString();
                    }
                });
            }
        });
        //select lga
        selectLGA = findViewById(R.id.selectLGA);
        txtLGA = findViewById(R.id.selectedLga);
        selectLGA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(state.equals("") || state.equals("...")){
                    //no state selected
                    Toast.makeText(Registration.this, "Kindly select a state", Toast.LENGTH_SHORT).show();
                }else{
                    lgaloadingdoctor.setVisibility(View.VISIBLE);
                    arrayLGA.clear();
                    getLGA(state, lgaloadingdoctor, "doctor", txtLGA);
                }
            }
        });

        edtPassword = findViewById(R.id.edtPassword);
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtPassword.getText().length()>0){
                    password = edtPassword.getText().toString().trim();
                }else{
                    //do nothing
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        edtConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (edtConfirmPassword.getText().length()>0){
//                    confrimPassword = edtConfirmPassword.getText().toString().trim();
//                }else{
//                    //do nothing
//                }

                if (!edtConfirmPassword.getText().toString().toString().equals(password)){
                    edtConfirmPassword.setError("Password do not match");
                }else{
                    confrimPassword = edtConfirmPassword.getText().toString().trim();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        nextButton = findViewById(R.id.next2);
        regDoc = findViewById(R.id.nexter);
        progressDoctorReg = findViewById(R.id.progressDoctorReg);
        regDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!doctorID.equals("") && !firstName.equals("") && !lastName.equals("") && !username.equals("") && !email.equals("")
                        && !phone.equals("") && !facilityName.equals("") && !speciality.equals("") && (password.equals(confrimPassword))){
                    // register the Doctor
                    registerDoctor();
                }else{
                    Toast.makeText(Registration.this, "Please check some fields", Toast.LENGTH_SHORT).show();
                    System.out.println(doctorID+", "+firstName+", "+lastName+", "+username+", "+email+", "+phone+", "+facilityName+", "+speciality+", "+password);
                }
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!doctorID.equals("") && !firstName.equals("") && !lastName.equals("") && !username.equals("") && !email.equals("")
                    && !phone.equals("") && !facilityName.equals("") && !speciality.equals("") && (password.equals(confrimPassword))){
                    // register the Doctor
                    registerDoctor();
                }else{
                    Toast.makeText(Registration.this, "Please check some fields", Toast.LENGTH_SHORT).show();
                    System.out.println(doctorID+", "+firstName+", "+lastName+", "+username+", "+email+", "+phone+", "+facilityName+", "+speciality+", "+password);
                }

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

        TextInputEditText edtHospitalName, edtHospitalId, edtHospitalUsername, edtHospitalEmail, edtHospitalPhone, edtHospitalPassword, edtHospitalConfirmPassword;
        RelativeLayout selectHospitalState, selectHospitalLGA;
        ProgressBar lgaloadinghospital;
        TextView txtHospitalState, txtHospitalLGA;
        ImageView checkHospitalEmail, checkHospitalPhone;
        Button nexterHospital;

        lgaloadinghospital = findViewById(R.id.lgaloadinghospital);
        edtHospitalName = findViewById(R.id.edtHospitalFacilityName);
        edtHospitalName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtHospitalName.getText().toString().length()>0){
                    hospitalname = edtHospitalName.getText().toString().trim();
                }else{
                    //do nothing
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtHospitalId = findViewById(R.id.edtHospitalFacilityID);
        edtHospitalId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtHospitalId.getText().toString().length()>0){
                    hospitalID = edtHospitalId.getText().toString().trim();
                }else{
                    //do nothing
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtHospitalUsername = findViewById(R.id.edtHospitalUsername);
        edtHospitalUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtHospitalUsername.getText().toString().length()>0){
                    hospitalusername = edtHospitalUsername.getText().toString().trim();
                }else{
                    //do nothing
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtHospitalEmail = findViewById(R.id.edtHospitalEmail);
        checkHospitalEmail = findViewById(R.id.checkHospitalEmail);
        edtHospitalEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (Patterns.EMAIL_ADDRESS.matcher(edtHospitalEmail.getText().toString().trim()).matches()){
                    hospitalemail = edtHospitalEmail.getText().toString().trim();
                    checkHospitalEmail.setVisibility(View.VISIBLE);
                }else{
                    checkHospitalEmail.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtHospitalPhone = findViewById(R.id.edtHospitalPhoneNumber);
        checkHospitalPhone = findViewById(R.id.checkHospitalPhone);
        edtHospitalPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtHospitalPhone.getText().toString().length()>10){
                    hospitalphone = edtHospitalPhone.getText().toString().trim();
                    checkHospitalPhone.setVisibility(View.VISIBLE);
                }else{
                    checkHospitalPhone.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //select state
        selectHospitalState = findViewById(R.id.selectHospitalState);
        txtHospitalState = findViewById(R.id.selectedHospitalState);
        selectHospitalState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show the dialog
                Dialog dialog = new Dialog(Registration.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.modal_states);

                ListView listView = dialog.findViewById(R.id.listView);
                StateAdapter stateAdapter = new StateAdapter(Registration.this, arrayState, "hospital", dialog);
                listView.setAdapter(stateAdapter);

                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        hospitalstate = txtHospitalState.getText().toString();
                    }
                });
            }
        });
        //select lga
        selectHospitalLGA = findViewById(R.id.selectHospitalLGA);
        txtHospitalLGA = findViewById(R.id.selectedHospitalLga);
        selectHospitalLGA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hospitalstate.equals("") || hospitalstate.equals("...")){
                    //no state selected
                    Toast.makeText(Registration.this, "Kindly select a state", Toast.LENGTH_SHORT).show();
                }else{
                    lgaloadinghospital.setVisibility(View.VISIBLE);
                    arrayLGA.clear();
                    getLGA(hospitalstate, lgaloadinghospital, "hospital", txtHospitalLGA);
                }
            }
        });


        edtHospitalPassword = findViewById(R.id.edtHospitalPassword);
        edtHospitalPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtHospitalPassword.getText().length()>0){
                    hospitalpassword = edtHospitalPassword.getText().toString().trim();
                }else{
                    //do nothing
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtHospitalConfirmPassword = findViewById(R.id.edtHospitalConfirmPassword);
        edtHospitalConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!edtHospitalConfirmPassword.getText().toString().equals(hospitalpassword)){
                    edtHospitalConfirmPassword.setError("Password do not match");
                }else{
                    hospitalconfrimPassword = edtHospitalConfirmPassword.getText().toString().trim();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        registerHospital = findViewById(R.id.next3);
        progressHospitalRegistration = findViewById(R.id.progressHospitalReg);
        nexterHospital = findViewById(R.id.nexterHospital);
        nexterHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!hospitalname.equals("") && !hospitalID.equals("") && !hospitalusername.equals("")
                        && !hospitalemail.equals("") && !hospitalphone.equals("") && (hospitalpassword.equals(hospitalconfrimPassword))){
                    // register the Hospital
                    registersHospital();
                }else{
                    Toast.makeText(Registration.this, "Please check some fields", Toast.LENGTH_SHORT).show();
                    System.out.println(hospitalname+", "+hospitalID+", "+hospitalusername+", "+hospitalemail+", "+hospitalphone+", "+hospitalpassword);
                }
            }
        });
        registerHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!hospitalname.equals("") && !hospitalID.equals("") && !hospitalusername.equals("")
                        && !hospitalemail.equals("") && !hospitalphone.equals("") && (hospitalpassword.equals(hospitalconfrimPassword))){
                    // register the Hospital
                    registersHospital();
                }else{
                    Toast.makeText(Registration.this, "Please check some fields", Toast.LENGTH_SHORT).show();
                    System.out.println(hospitalname+", "+hospitalID+", "+hospitalusername+", "+hospitalemail+", "+hospitalphone+", "+hospitalpassword);
                }

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

        TextInputEditText edtPharmacyName, edtPharmacyId, edtPharmacyUsername, edtPharmacyEmail, edtPharmacyPhone, edtPharmacyPassword, edtPharmacyConfirmPassword;
        RelativeLayout selectPharmacyDelivery, selectPharmacyState, selectPharmacyLGA;
        TextView txtPharmacyState, txtPharmacyLGA;
        ImageView checkPharmacyEmail, checkPharmacyPhone;
        Button nexterPharmacy;

        edtPharmacyName = findViewById(R.id.edtPharmacyFacilityName);
        edtPharmacyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtPharmacyName.getText().toString().length()>0){
                    pharmacyname = edtPharmacyName.getText().toString().trim();
                }else{
                    //do nothing
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtPharmacyId = findViewById(R.id.edtPharmacyFacilityId);
        edtPharmacyId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtPharmacyId.getText().toString().length()>0){
                    pharmacyID = edtPharmacyId.getText().toString().trim();
                }else{
                    //do nothing
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtPharmacyUsername = findViewById(R.id.edtPharmacyFacilityUsername);
        edtPharmacyUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtPharmacyUsername.getText().toString().length()>0){
                    pharmacyusername = edtPharmacyUsername.getText().toString().trim();
                }else{
                    //do nothing
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtPharmacyEmail = findViewById(R.id.edtPharmacyEmail);
        checkPharmacyEmail = findViewById(R.id.checkPharmacyEmail);
        edtPharmacyEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (Patterns.EMAIL_ADDRESS.matcher(edtPharmacyEmail.getText().toString().trim()).matches()){
                    pharmacyemail = edtPharmacyEmail.getText().toString().trim();
                    checkPharmacyEmail.setVisibility(View.VISIBLE);
                }else{
                    checkPharmacyEmail.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtPharmacyPhone = findViewById(R.id.edtPharmacyPhoneNumber);
        checkPharmacyPhone = findViewById(R.id.checkPharmacyPhone);
        edtPharmacyPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtPharmacyPhone.getText().toString().length()>10){
                    pharmacyphone = edtPharmacyPhone.getText().toString().trim();
                    checkPharmacyPhone.setVisibility(View.VISIBLE);
                }else{
                    checkPharmacyPhone.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //select delivery type
        selectPharmacyDelivery = findViewById(R.id.selectDelivery);
        selectPharmacyDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show dialog
                Dialog dialog = new Dialog(Registration.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.modal_yes_no);

                TextView yes = dialog.findViewById(R.id.yes);
                TextView no = dialog.findViewById(R.id.no);

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deliveryMode.setText("Yes");
                        conductsDelivery = true;
                        dialog.dismiss();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deliveryMode.setText("No");
                        conductsDelivery = false;
                        dialog.dismiss();
                    }
                });

                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        });
        //select state
        selectPharmacyState = findViewById(R.id.selectPharmacyState);
        txtPharmacyState = findViewById(R.id.txtPharmacySelectedState);
        selectPharmacyState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show the dialog
                Dialog dialog = new Dialog(Registration.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.modal_states);

                ListView listView = dialog.findViewById(R.id.listView);
                StateAdapter stateAdapter = new StateAdapter(Registration.this, arrayState, "pharmacy", dialog);
                listView.setAdapter(stateAdapter);

                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        pharmacystate = txtPharmacyState.getText().toString();
                    }
                });
            }
        });
        //select lga
        selectPharmacyLGA = findViewById(R.id.selectPharmacyLGA);
        txtPharmacyLGA = findViewById(R.id.txtPharmacySelectedLga);
        selectPharmacyLGA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show the dialog
                Dialog dialog = new Dialog(Registration.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.modal_lga);

                ArrayList<String> myarray = new ArrayList<>();

                ListView listView = dialog.findViewById(R.id.listView);
                for (int i = 0; i < arrayState.size(); i++) {
                    if (txtPharmacyState.getText().toString().trim().equals(arrayState.get(i))){
                        lgalength = arrayLGA.get(i).length();
                        for (int j = 0; j < lgalength; j++) {
//                            try {
//                                String item = arrayLGA.get(i).getString(j);
//                                myarray.add(item);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
                        }
                    }
                }
                LGAAdapter lgaAdapter = new LGAAdapter(Registration.this, myarray, "pharmacy", dialog);
                listView.setAdapter(lgaAdapter);

                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        pharmacylga = txtPharmacyLGA.getText().toString();
                    }
                });
            }
        });


        edtPharmacyPassword = findViewById(R.id.edtPharmacyPassword);
        edtPharmacyPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtPharmacyPassword.getText().length()>0){
                    pharmacypassword = edtPharmacyPassword.getText().toString().trim();
                }else{
                    //do nothing
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtPharmacyConfirmPassword = findViewById(R.id.edtPharmacyConfirmedPassword);
        edtPharmacyConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!edtPharmacyConfirmPassword.getText().toString().equals(pharmacypassword)){
                    edtPharmacyConfirmPassword.setError("Password do not match");
                }else{
                    pharmacyconfrimPassword = edtPharmacyConfirmPassword.getText().toString().trim();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        registerPharmacy = findViewById(R.id.next4);
        progressPharmacyRegistration = findViewById(R.id.progressPharmacyReg);
        nexterPharmacy = findViewById(R.id.nexterPharmacy);
        nexterPharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!pharmacylga.equals("") && !pharmacyID.equals("") && !pharmacyusername.equals("")
                        && !pharmacyemail.equals("") && !pharmacyphone.equals("") && (pharmacypassword.equals(pharmacyconfrimPassword))){
                    // register the Hospital
                    registersPharmacy();
                }else{
                    Toast.makeText(Registration.this, "Please check some fields", Toast.LENGTH_SHORT).show();
                    System.out.println(pharmacyname+", "+pharmacyID+", "+pharmacyusername+", "+pharmacyemail+", "+pharmacyphone+", "+pharmacypassword);
                }
            }
        });
        registerPharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!pharmacylga.equals("") && !pharmacyID.equals("") && !pharmacyusername.equals("")
                        && !pharmacyemail.equals("") && !pharmacyphone.equals("") && (pharmacypassword.equals(pharmacyconfrimPassword))){
                    // register the Hospital
                    registersPharmacy();
                }else{
                    Toast.makeText(Registration.this, "Please check some fields", Toast.LENGTH_SHORT).show();
                    System.out.println(pharmacyname+", "+pharmacyID+", "+pharmacyusername+", "+pharmacyemail+", "+pharmacyphone+", "+pharmacypassword);
                }

            }
        });
    }

    private void verifyUser(String email) {
        TextView userEmail;
        userEmail = findViewById(R.id.userEmail);
        userEmail.setText(email);
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

    //register Patients
    private void registersPatient(){
        // Show progress with "Loading" text
        progressPatientRegistration.setVisibility(View.VISIBLE);

        JSONObject jsonPatient = new JSONObject();
        try {
            jsonPatient.put("username", patientusername);
            jsonPatient.put("email", patientemail);
            jsonPatient.put("first_name", patientfirstName);
            jsonPatient.put("last_name", patientlastName);
//            jsonPatient.put("role", "patient");
            jsonPatient.put("password", patientpassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject combinedJson = new JSONObject();
        try {
//            combinedJson.put("patient", jsonPatient);
            combinedJson.put("user", jsonPatient);
            combinedJson.put("phone_number", patientphone);
            combinedJson.put("state", patientstate);
            combinedJson.put("l_g_a", patientlga);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "https://api.carekojo.e4eweb.space/patient_profile/", combinedJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response
                        System.out.println("Patient Registration response = "+response);
                        progressPatientRegistration.setVisibility(View.GONE);

                        patientRegistration.setVisibility(View.GONE);
                        verify.setVisibility(View.VISIBLE);
                        verifyUser(patientemail);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Handle errors
                        System.out.println("Patient Registration Error response = "+volleyError);
                        progressPatientRegistration.setVisibility(View.GONE);

                        Toast.makeText(Registration.this, "Failed to register Patient", Toast.LENGTH_SHORT).show();
                        if (volleyError.networkResponse != null){
                            byte[] responseData = volleyError.networkResponse.data;
                            if (responseData != null) {
                                String new_response = new String(responseData);
                                System.out.println("Registration error response = "+new_response);
                            }
                        }
                        volleyError.printStackTrace();
                    }
                });
        // Add the request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(retryPolicy);
        requestQueue.add(request);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.getCache().clear();
            }
        });
    }

    //register Doctors
    private void registerDoctor(){
        // Show progress with "Loading" text
        progressDoctorReg.setVisibility(View.VISIBLE);

        JSONObject jsonDoctor = new JSONObject();
        try {
            jsonDoctor.put("username", username);
            jsonDoctor.put("email", email);
            jsonDoctor.put("first_name", firstName);
            jsonDoctor.put("last_name", lastName);
//            jsonDoctor.put("role", "doctor");
            jsonDoctor.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject combinedJson = new JSONObject();
        try {
//            combinedJson.put("doctor", jsonDoctor);
            combinedJson.put("user", jsonDoctor);
            combinedJson.put("doctor_id_number", doctorID);
            combinedJson.put("phone_number", phone);
//            combinedJson.put("medical_facility", facilityName);
            combinedJson.put("specialty", speciality);
            combinedJson.put("state", state);
            combinedJson.put("l_g_a", lga);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "https://api.carekojo.e4eweb.space/doctor_profile/", combinedJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response
                        System.out.println("Doctor Registration response = "+response);
                        progressDoctorReg.setVisibility(View.GONE);

                        doctorRegistration.setVisibility(View.GONE);
                        verify.setVisibility(View.VISIBLE);
                        verifyUser(email);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Handle errors
                        System.out.println("Doctor Registration Error response = "+volleyError);
                        progressDoctorReg.setVisibility(View.GONE);

                        Toast.makeText(Registration.this, "Failed to register User", Toast.LENGTH_SHORT).show();
                        if (volleyError.networkResponse != null){
                            byte[] responseData = volleyError.networkResponse.data;
                            if (responseData != null) {
                                String new_response = new String(responseData);
                                System.out.println("Registration error response = "+new_response);
                            }
                        }
                        volleyError.printStackTrace();
                    }
                });
        // Add the request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(retryPolicy);
        requestQueue.add(request);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.getCache().clear();
            }
        });

    }

    //register Patients
    private void registersHospital(){
        // Show progress with "Loading" text
        progressHospitalRegistration.setVisibility(View.VISIBLE);

        JSONObject jsonPatient = new JSONObject();
        try {
            jsonPatient.put("username", hospitalusername);
            jsonPatient.put("email", hospitalemail);
            jsonPatient.put("first_name", hospitalname);
            jsonPatient.put("last_name", "");
//            jsonPatient.put("role", "hospital");
            jsonPatient.put("password", hospitalphone);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject combinedJson = new JSONObject();
        try {
            combinedJson.put("hospital", jsonPatient);
            combinedJson.put("phone_number", hospitalphone);
            combinedJson.put("id_number", hospitalID);
            combinedJson.put("state", hospitalstate);
            combinedJson.put("l_g_a", hospitallga);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "https://api.carekojo.e4eweb.space/hospital_profile/", combinedJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response
                        System.out.println("Hospital Registration response = "+response);
                        progressHospitalRegistration.setVisibility(View.GONE);

                        hospitalRegistration.setVisibility(View.GONE);
                        verify.setVisibility(View.VISIBLE);
                        verifyUser(hospitalemail);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Handle errors
                        System.out.println("Hospital Registration Error response = "+volleyError);
                        progressHospitalRegistration.setVisibility(View.GONE);

                        Toast.makeText(Registration.this, "Failed to register Hospital, try again.", Toast.LENGTH_SHORT).show();
                        if (volleyError.networkResponse != null){
                            byte[] responseData = volleyError.networkResponse.data;
                            if (responseData != null) {
                                String new_response = new String(responseData);
                                System.out.println("Registration error response = "+new_response);
                            }
                        }
                        volleyError.printStackTrace();
                    }
                });
        // Add the request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(retryPolicy);
        requestQueue.add(request);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.getCache().clear();
            }
        });
    }

    //register Pharmacy
    private void registersPharmacy(){
        // Show progress with "Loading" text
        progressPharmacyRegistration.setVisibility(View.VISIBLE);

        JSONObject jsonPharmacy = new JSONObject();
        try {
            jsonPharmacy.put("username", pharmacyusername);
            jsonPharmacy.put("email", pharmacyemail);
            jsonPharmacy.put("first_name", pharmacyname);
            jsonPharmacy.put("last_name", "");
//            jsonPharmacy.put("role","pharm");
            jsonPharmacy.put("password", pharmacypassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject combinedJson = new JSONObject();
        try {
//            combinedJson.put("pharmacy", jsonPharmacy);
            combinedJson.put("user", jsonPharmacy);
            combinedJson.put("phone_number", pharmacyphone);
            combinedJson.put("id_number", pharmacyID);
            combinedJson.put("state", pharmacystate);
            combinedJson.put("l_g_a", pharmacylga);
            combinedJson.put("conducts_delivery", conductsDelivery);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "https://api.carekojo.e4eweb.space/pharmacy_profile/", combinedJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response
                        System.out.println("Pharmacy Registration response = "+response);
                        progressPharmacyRegistration.setVisibility(View.GONE);

                        pharmacyRegistration.setVisibility(View.GONE);
                        verify.setVisibility(View.VISIBLE);
                        verifyUser(pharmacyemail);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Handle errors
                        System.out.println("Pharmacy Registration Error response = "+volleyError);
                        progressPharmacyRegistration.setVisibility(View.GONE);

                        Toast.makeText(Registration.this, "Failed to register Pharmacy, try again.", Toast.LENGTH_SHORT).show();
                        if (volleyError.networkResponse != null){
                            byte[] responseData = volleyError.networkResponse.data;
                            if (responseData != null) {
                                String new_response = new String(responseData);
                                System.out.println("Registration error response = "+new_response);
                            }
                        }
                        volleyError.printStackTrace();
                    }
                });
        // Add the request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(retryPolicy);
        requestQueue.add(request);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.getCache().clear();
            }
        });
    }

    //get states and lgas
    private void getStatesandLga() {
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://api.carekojo.e4eweb.space/states/",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        System.out.println("Notification response states = "+response);
//                        try {
//                            JSONArray jsonArray = new JSONArray(response);
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                String state = jsonObject.getString("name");
//                                String lga = jsonObject.getString("lgas");
//                                //add to the arraylist
//                                arrayState.add(state);
//                                arrayLGA.add(lga);
//
////                                JSONArray arrayLga = new JSONArray(lga);
////
////                                arrayLGA.add(arrayLga);
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        volleyError.printStackTrace();
//                    }
//                }){
//            @Override
//            protected Map<String, String> getParams(){
//                Map<String, String> params = new HashMap<>();
//                params.put("Content-Type", "application/json");
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        stringRequest.setRetryPolicy(retryPolicy);
//        requestQueue.add(stringRequest);
//        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
//            @Override
//            public void onRequestFinished(Request<Object> request) {
//                requestQueue.getCache().clear();
//            }
//        });

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://nga-states-lga.onrender.com/fetch",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("State response states = "+response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String state = jsonArray.getString(i);
                                arrayState.add(state);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        volleyError.printStackTrace();
                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
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

    private void getLGA(String state, ProgressBar lgaloading, String role, TextView textType){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://nga-states-lga.onrender.com/?state="+state,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("LGA response states = "+response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String lga = jsonArray.getString(i);
                                arrayLGA.add(lga);
                            }

                            lgaloading.setVisibility(View.GONE);
                            //show the dialog
                            Dialog dialog = new Dialog(Registration.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.modal_lga);

                            ListView listView = dialog.findViewById(R.id.listView);
                            LGAAdapter lgaAdapter = new LGAAdapter(Registration.this, arrayLGA, role, dialog);
                            listView.setAdapter(lgaAdapter);

                            dialog.show();
                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                            dialog.getWindow().setGravity(Gravity.BOTTOM);
                            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    if (role.equals("patient")){
                                        patientlga = textType.getText().toString();
                                    }
                                    else if (role.equals("doctor")) {
                                         lga = textType.getText().toString();
                                    }
                                    else if (role.equals("hospital")) {
                                        hospitallga = textType.getText().toString();
                                    }
                                    else if (role.equals("pharmacy")) {
                                        pharmacylga = textType.getText().toString();
                                    }

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        volleyError.printStackTrace();
                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
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

}