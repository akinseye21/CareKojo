package ng.com.carekojo.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ng.com.carekojo.R;
import ng.com.carekojo.activities.GuestPage;
import ng.com.carekojo.activities.LoginPage;
import ng.com.carekojo.activities.Registration;


public class HomeFragment extends Fragment {

    RelativeLayout specialties;
    TextView specialty_type;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        specialty_type = v.findViewById(R.id.specialty_type);
        specialties = v.findViewById(R.id.specialties);
        specialties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(getContext());
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
                        specialty_type.setText(generalmedicine.getText().toString());
                        dialog.dismiss();
                    }
                });
                cardiologist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        specialty_type.setText(cardiologist.getText().toString());
                        dialog.dismiss();
                    }
                });
                dentist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        specialty_type.setText(dentist.getText().toString());
                        dialog.dismiss();
                    }
                });
                vestmedicine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        specialty_type.setText(vestmedicine.getText().toString());
                        dialog.dismiss();
                    }
                });
                dermatologist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        specialty_type.setText(dermatologist.getText().toString());
                        dialog.dismiss();
                    }
                });
                emergencymedicine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        specialty_type.setText(emergencymedicine.getText().toString());
                        dialog.dismiss();
                    }
                });
                familymedicine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        specialty_type.setText(familymedicine.getText().toString());
                        dialog.dismiss();
                    }
                });
                generalpractitioner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        specialty_type.setText(generalpractitioner.getText().toString());
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


        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Your code here
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}