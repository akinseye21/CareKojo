package ng.com.carekojo.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ng.com.carekojo.R;

public class DashboardFragmentLoggedIn extends Fragment {

    TextView txtUsername;
    SharedPreferences preferences;
    String first_name, last_name, user_name;

    public DashboardFragmentLoggedIn() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dashboard_logged_in, container, false);

        preferences = getContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
        first_name = preferences.getString("first_name", "");
        last_name = preferences.getString("last_name", "");
        user_name = preferences.getString("username", "");
        txtUsername = v.findViewById(R.id.username);
        txtUsername.setText("Hello "+user_name);

        return v;
    }
}