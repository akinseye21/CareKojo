package ng.com.carekojo.sheets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.w3c.dom.Text;

import ng.com.carekojo.R;

public class UserType extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;
//    private BottomSheetCallback callback;

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_user_sheet, container, false);

        LinearLayout linearBG = v.findViewById(R.id.linearbg);
        linearBG.setBackgroundColor(Color.parseColor("#98000000"));
        LinearLayout linearBG2 = v.findViewById(R.id.linearbg2);
        linearBG2.setBackgroundResource(R.drawable.corner_popup);

        TextView patient = v.findViewById(R.id.patient);
        TextView doctor = v.findViewById(R.id.doctor);
        TextView hospital = v.findViewById(R.id.hospital);
        TextView pharmacist = v.findViewById(R.id.pharmacist);

        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("Patient");
                dismiss();
            }
        });
        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("Doctor");
                dismiss();
            }
        });
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("Hospital");
                dismiss();
            }
        });
        pharmacist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("Pharmacist");
                dismiss();
            }
        });

        return v;
    }

    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }


//    public interface BottomSheetCallback {
//        void onTextSelected(String text);
//    }
//
//    private void onTextSelected(String selectedText) {
//        // Call the callback to pass the selected text to the activity
//        callback.onTextSelected(selectedText);
//    }
}
