package ng.com.carekojo.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import ng.com.carekojo.R;
import ng.com.carekojo.activities.Registration;

public class LGAAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> myarray;
    String from;
    private Dialog dialog;

    public LGAAdapter(Context context,
                      ArrayList<String> myarray,
                      String from,
                      Dialog dialog){
        //Getting all the values
        this.context = context;
        this.myarray = myarray;
        this.from = from;
        this.dialog = dialog;
    }

    @Override
    public int getCount() {
        return myarray.size();
    }

    @Override
    public Object getItem(int i) {
        return myarray.get(i);
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
            TextView txtLGA = convertView.findViewById(R.id.state);
            txtLGA.setText(myarray.get(i));
            txtLGA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //set the text to the value and close the modal
                    TextView textView = ((Registration) context).findViewById(R.id.selectedLgaPatient);
                    textView.setText(myarray.get(i));
                    dialog.dismiss();
                }
            });
        }

        if (from.equals("doctor")){
            TextView txtLGA = convertView.findViewById(R.id.state);
            txtLGA.setText(myarray.get(i));
            txtLGA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //set the text to the value and close the modal
                    TextView textView = ((Registration) context).findViewById(R.id.selectedLga);
                    textView.setText(myarray.get(i));
                    dialog.dismiss();
                }
            });
        }

        if (from.equals("hospital")){
            TextView txtLGA = convertView.findViewById(R.id.state);
            txtLGA.setText(myarray.get(i));
            txtLGA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //set the text to the value and close the modal
                    TextView textView = ((Registration) context).findViewById(R.id.selectedHospitalLga);
                    textView.setText(myarray.get(i));
                    dialog.dismiss();
                }
            });
        }

        if (from.equals("pharmacy")){
            TextView txtLGA = convertView.findViewById(R.id.state);
            txtLGA.setText(myarray.get(i));
            txtLGA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //set the text to the value and close the modal
                    TextView textView = ((Registration) context).findViewById(R.id.txtPharmacySelectedLga);
                    textView.setText(myarray.get(i));
                    dialog.dismiss();
                }
            });
        }

        return convertView;
    }
}
