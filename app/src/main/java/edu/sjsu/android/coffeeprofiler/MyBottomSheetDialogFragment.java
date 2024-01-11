package edu.sjsu.android.coffeeprofiler;


import android.content.ClipData;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Collections;
import java.util.Set;


public class MyBottomSheetDialogFragment extends DialogFragment {

    private final String AUTHORITY = "edu.sjsu.android.coffeeprofiler";
    private final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Cursor cursor = getContext().getContentResolver().query(CONTENT_URI, new String[]{"name", "rating", "drink"}, null, null, "rating DESC");
//        ItemFragment.names.clear();
//        ItemFragment.ratings.clear();
//        ItemFragment.drinks.clear();
        ItemFragment.items.clear();
        ItemFragment.adapter.notifyDataSetChanged();
        while(cursor.moveToNext()){
            ItemFragment.items.add(new Row(
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("drink")),
                    cursor.getInt(cursor.getColumnIndex("rating"))
            ));
        }
        ItemFragment.adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_bottom_sheet, container, false);

        EditText nameEle = (EditText)view.findViewById(R.id.name);
        Spinner roastEle = (Spinner)view.findViewById(R.id.spinner);
        EditText drinkEle = (EditText)view.findViewById(R.id.drink);
        EditText coarsenessEle = (EditText)view.findViewById(R.id.coarseness);
        SeekBar heatEle = (SeekBar) view.findViewById(R.id.heat);
        SeekBar tampEle = (SeekBar) view.findViewById(R.id.tamp);
        SeekBar waterEle = (SeekBar) view.findViewById(R.id.water);
        SeekBar ratingEle = (SeekBar) view.findViewById(R.id.rating);
        Button add = (Button) view.findViewById(R.id.add);
        Button cancel = (Button) view.findViewById(R.id.cancel);
        EditText noteEle = (EditText)view.findViewById(R.id.note);
        EditText weightEle = (EditText)view.findViewById(R.id.weight);
        SeekBar extractEle = (SeekBar) view.findViewById(R.id.extraction);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean missingValue = false;
                String name = nameEle.getText().toString();
                if(name.equals("")){
                    nameEle.setBackgroundColor(Color.RED);
                    missingValue = true;
                }else{
                    nameEle.setBackgroundColor(Color.TRANSPARENT);
                }
                String roast = roastEle.getSelectedItem().toString();
                if(roast.equals("Select roast")){
                    roastEle.setBackgroundColor(Color.RED);
                    missingValue = true;
                }else{
                    roastEle.setBackgroundColor(Color.TRANSPARENT);
                }
                String drink = drinkEle.getText().toString();
                if(drink.equals("")){
                    drinkEle.setBackgroundColor(Color.RED);
                    missingValue = true;
                }else{
                    drinkEle.setBackgroundColor(Color.TRANSPARENT);
                }
                int coarseness =0;
                if(coarsenessEle.getText().toString().equals("")){
                    coarsenessEle.setBackgroundColor(Color.RED);
                    missingValue = true;
                }else{
                    coarsenessEle.setBackgroundColor(Color.TRANSPARENT);
                    coarseness = Integer.parseInt(coarsenessEle.getText().toString());
                }
                int heat = heatEle.getProgress();
                int tamp = tampEle.getProgress();
                int water = waterEle.getProgress();
                int rating = ratingEle.getProgress();
                double _weight = 0.0;
                if(weightEle.getText().toString().equals("")){
                    weightEle.setBackgroundColor(Color.RED);
                    missingValue = true;
                }else{
                    weightEle.setBackgroundColor(Color.TRANSPARENT);
                    _weight = Double.parseDouble(weightEle.getText().toString());
                }
                int _extraction = extractEle.getProgress();
                String _note = noteEle.getText().toString();
                if(missingValue){
                    return;
                }else{
                    ContentValues values = new ContentValues();
                    values.put("name", name);
                    values.put("roast", roast);
                    values.put("drink", drink);
                    values.put("coarseness", coarseness);
                    values.put("heat", heat);
                    values.put("tamp", tamp);
                    values.put("water", water);
                    values.put("rating", rating);
                    values.put("note", _note);
                    values.put("extraction", _extraction);
                    values.put("weight", _weight);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            if(getContext().getContentResolver().insert(CONTENT_URI, values) != null){
                                Toast.makeText(getContext(), "Coffee added", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }else{
                                Toast.makeText(getContext(), "Coffee add failed", Toast.LENGTH_SHORT).show();
                            }
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        // Customize the dialog to show in the center of the screen and set width to 80%
        if (getDialog() != null) {
            WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
            params.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9);
            getDialog().getWindow().setAttributes(params);
        }

        handleSpinner();
    }
    private void handleSpinner() {
        View view = getView();

        if (view != null) {
            Spinner spinner = view.findViewById(R.id.spinner);

            // Populate the Spinner with data
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    requireContext(),
                    R.array.roast_array,
                    android.R.layout.simple_spinner_item
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }


}