package com.example.radiustask.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.radiustask.DI.Models.Option;
import com.example.radiustask.R;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<Option> {
private List<Option> objects;

public SpinnerAdapter(Context context, int textViewResourceId, List<Option> objects) {
    super(context, textViewResourceId, objects);
    this.objects=objects;
}

@Override
public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
    return getCustomView(position, convertView, parent);
}

@NonNull
@Override
public View getView(int position, View convertView, @NonNull ViewGroup parent) {
    return getCustomView(position, convertView, parent);
}

private View getCustomView(final int position, View convertView, ViewGroup parent) {
    View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.customspinner, parent, false);
    final TextView optiontext=(TextView)row.findViewById(R.id.optiontext);
    final ImageView optionImage  = row.findViewById(R.id.optionimage);
    optiontext.setText(objects.get(position).getName());
    int optionDrawable = R.drawable.ic_arrow_drop_down_black_24dp ;
    if(objects.get(position).getIcon() != null){
        switch (objects.get(position).getIcon()){
            case "apartment": optionDrawable = R.drawable.apartment;
                break;

            case "boat": optionDrawable = R.drawable.boat;
                break;

            case "condo": optionDrawable = R.drawable.condo;
                break;

            case "garage": optionDrawable = R.drawable.garage;
                break;

            case "garden": optionDrawable = R.drawable.garden;
                break;

            case "land": optionDrawable = R.drawable.land;
                break;

            case "no-room": optionDrawable = R.drawable.noroom;
                break;

            case "rooms": optionDrawable = R.drawable.rooms;
                break;

            case "swimming": optionDrawable = R.drawable.swimming;
                break;

        }

    }
    optionImage.setImageDrawable(ContextCompat.getDrawable(parent.getContext(),optionDrawable));
    return row;
}
}