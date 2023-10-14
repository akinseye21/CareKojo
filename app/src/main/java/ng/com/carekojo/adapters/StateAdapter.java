package ng.com.carekojo.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;

import ng.com.carekojo.R;
import ng.com.carekojo.activities.Registration;

public class StateAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> states;
    String from;
    private Dialog dialog;

    public StateAdapter(Context context,
                        ArrayList<String> states,
                        String from,
                        Dialog dialog){
        //Getting all the values
        this.context = context;
        this.states = states;
        this.from = from;
        this.dialog = dialog;
    }

    @Override
    public int getCount() {
        return states.size();
    }

    @Override
    public Object getItem(int i) {
        return states.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        LayoutInflater inflaInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflaInflater.inflate(R.layout.list_item, parent, false);
        }


        if (from.equals("patient")){
            TextView txtState = convertView.findViewById(R.id.state);
            txtState.setText(states.get(i));
            txtState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //set the text to the value and close the modal
                    TextView textView = ((Registration) context).findViewById(R.id.selectedStatePatient);
                    textView.setText(states.get(i));
                    dialog.dismiss();
                }
            });
        }

        if (from.equals("doctor")){
            TextView txtState = convertView.findViewById(R.id.state);
            txtState.setText(states.get(i));
            txtState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //set the text to the value and close the modal
                    TextView textView = ((Registration) context).findViewById(R.id.selectedState);
                    textView.setText(states.get(i));
                    dialog.dismiss();
                }
            });
        }

        if (from.equals("hospital")){
            TextView txtState = convertView.findViewById(R.id.state);
            txtState.setText(states.get(i));
            txtState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //set the text to the value and close the modal
                    TextView textView = ((Registration) context).findViewById(R.id.selectedHospitalState);
                    textView.setText(states.get(i));
                    dialog.dismiss();
                }
            });
        }

        if (from.equals("pharmacy")){
            TextView txtState = convertView.findViewById(R.id.state);
            txtState.setText(states.get(i));
            txtState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //set the text to the value and close the modal
                    TextView textView = ((Registration) context).findViewById(R.id.txtPharmacySelectedState);
                    textView.setText(states.get(i));
                    dialog.dismiss();
                }
            });
        }

        return convertView;
    }
}
