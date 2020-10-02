package com.koleychik.currencyvalue.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.koleychik.currencyvalue.R;

import java.util.ArrayList;


public class MakeSpinnerAdapter extends ArrayAdapter<SpinnerModel> {

    Context context;

    public MakeSpinnerAdapter(@NonNull Context context, ArrayList<SpinnerModel> modelList) {
        super(context, 0, modelList);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, parent);
    }

    private View initView(int position, @NonNull ViewGroup parent) {
        View convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner, parent, false);

        ImageView flag = convertView.findViewById(R.id.flag);
        TextView name = convertView.findViewById(R.id.currencyName);
        TextView abbreviation = convertView.findViewById(R.id.abbreviation);

        SpinnerModel spinnerModel = (SpinnerModel) getItem(position);

        assert spinnerModel != null;
        flag.setImageResource(spinnerModel.getImg());
        name.setText(context.getString(spinnerModel.getName()));
        abbreviation.setText(context.getString(spinnerModel.getAbbreviation()));

        return convertView;
    }
}
