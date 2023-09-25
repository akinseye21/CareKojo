package ng.com.carekojo.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;

import ng.com.carekojo.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    private String[] slide_text = {
            "Find Medical Practitioners Near You",
            "Find Medical Institutions Anywhere",
            "Schedule Appointments With Medical Practitioners"
    };

    @Override
    public int getCount() {
        return slide_text.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        LottieAnimationView lottieAnimationView = view.findViewById(R.id.lottie_layer);
        View view1 = view.findViewById(R.id.view1);
        View view2 = view.findViewById(R.id.view2);
        View view3 = view.findViewById(R.id.view3);
        TextView text = view.findViewById(R.id.text_description);

        // Define the new width in pixels (e.g., 200 pixels)
        int bigWidth = 100;
        int smallWidth = 40;

        if (position==0){
            lottieAnimationView.setAnimation("ic1.json");

            // Get the current LayoutParams of the view
            ViewGroup.LayoutParams layoutParams = view1.getLayoutParams();
            ViewGroup.LayoutParams layoutParams2 = view2.getLayoutParams();
            ViewGroup.LayoutParams layoutParams3 = view3.getLayoutParams();
            // Update the width of the LayoutParams
            layoutParams.width = bigWidth;
            layoutParams2.width = smallWidth;
            layoutParams3.width = smallWidth;
            // Apply the updated LayoutParams to the view
            view1.setLayoutParams(layoutParams);
            view2.setLayoutParams(layoutParams2);
            view3.setLayoutParams(layoutParams3);

        }else if (position==1){
            lottieAnimationView.setAnimation("animation2.json");

            // Get the current LayoutParams of the view
            ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
            ViewGroup.LayoutParams layoutParams2 = view1.getLayoutParams();
            ViewGroup.LayoutParams layoutParams3 = view3.getLayoutParams();
            // Update the width of the LayoutParams
            layoutParams.width = bigWidth;
            layoutParams2.width = smallWidth;
            layoutParams3.width = smallWidth;
            // Apply the updated LayoutParams to the view
            view2.setLayoutParams(layoutParams);
            view1.setLayoutParams(layoutParams2);
            view3.setLayoutParams(layoutParams3);

            view2.setBackgroundResource(R.drawable.button_orange);
            view2.setBackgroundTintList(null);
            view1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#62B72025")));
        }else if (position==2){
            lottieAnimationView.setAnimation("animation4.json");
            text.setTextColor(Color.parseColor("#E88F11"));
            view1.setBackgroundResource(R.drawable.button_yello);
            view2.setBackgroundResource(R.drawable.button_yello);
            view3.setBackgroundResource(R.drawable.button_yello);

            // Get the current LayoutParams of the view
            ViewGroup.LayoutParams layoutParams = view1.getLayoutParams();
            ViewGroup.LayoutParams layoutParams2 = view2.getLayoutParams();
            ViewGroup.LayoutParams layoutParams3 = view3.getLayoutParams();
            // Update the width of the LayoutParams
            layoutParams.width = smallWidth;
            layoutParams2.width = smallWidth;
            layoutParams3.width = bigWidth;
            // Apply the updated LayoutParams to the view
            view1.setLayoutParams(layoutParams);
            view2.setLayoutParams(layoutParams2);
            view3.setLayoutParams(layoutParams3);

            view1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#62FDA425")));
            view2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#62FDA425")));
            view3.setBackgroundTintList(null);
        }

        text.setText(slide_text[position]);
        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
