package edu.sjsu.android.coffeeprofiler;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;


public class EditDetailDialogFragment extends DialogFragment {

    private final String AUTHORITY = "edu.sjsu.android.coffeeprofiler";
    private final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private String name, roast, drink;
    private int id, coarseness, heat,  tamp, water, rating;
    public EditDetailDialogFragment(String n, String r, String d, int c, int h,  int t, int w, int ra, int i){
        super();
        name = n;
        roast = r;
        drink = d;
        coarseness = c;
        heat = h;
        tamp = t;
        water = w;
        rating = ra;
        id = i;
    }
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Cursor cursor = getContext().getContentResolver().query(CONTENT_URI, new String[]{"name", "rating"}, null, null, "rating");
        ItemFragment.names.clear();
        ItemFragment.ratings.clear();
        while(cursor.moveToNext()){
            ItemFragment.names.add(cursor.getString(cursor.getColumnIndex("name")));
            ItemFragment.ratings.add(cursor.getInt(cursor.getColumnIndex("rating")));
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
        
        nameEle.setText(this.name);

        drinkEle.setText(this.drink);
        coarsenessEle.setText(String.valueOf(this.coarseness));
        heatEle.setProgress(this.heat);
        tampEle.setProgress(this.tamp);
        waterEle.setProgress(this.water);
        ratingEle.setProgress(this.rating);


        Button add = (Button) view.findViewById(R.id.add);
        Button cancel = (Button) view.findViewById(R.id.cancel);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean missingValue = false;
                String _name = nameEle.getText().toString();
                if(_name.equals("")){
                    nameEle.setBackgroundColor(Color.RED);
                    missingValue = true;
                }else{
                    nameEle.setBackgroundColor(Color.TRANSPARENT);
                }
                String _roast = roastEle.getSelectedItem().toString();
                if(_roast.equals("Select roast")){
                    roastEle.setBackgroundColor(Color.RED);
                    missingValue = true;
                }else{
                    roastEle.setBackgroundColor(Color.TRANSPARENT);
                }
                String _drink = drinkEle.getText().toString();
                if(_drink.equals("")){
                    drinkEle.setBackgroundColor(Color.RED);
                    missingValue = true;
                }else{
                    drinkEle.setBackgroundColor(Color.TRANSPARENT);
                }
                int _coarseness =0;
                if(coarsenessEle.getText().toString().equals("")){
                    coarsenessEle.setBackgroundColor(Color.RED);
                    missingValue = true;
                }else{
                    coarsenessEle.setBackgroundColor(Color.TRANSPARENT);
                    _coarseness = Integer.parseInt(coarsenessEle.getText().toString());
                }
                int _heat = heatEle.getProgress();
                int _tamp = tampEle.getProgress();
                int _water = waterEle.getProgress();
                int _rating = ratingEle.getProgress();
                if(missingValue){
                    return;
                }else{
                    ContentValues values = new ContentValues();
                    values.put("name", _name);
                    values.put("roast", _roast);
                    values.put("drink", _drink);
                    values.put("coarseness", _coarseness);
                    values.put("heat", _heat);
                    values.put("tamp", _tamp);
                    values.put("water", _water);
                    values.put("rating", _rating);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            if(getContext().getContentResolver().update(CONTENT_URI, values, "_id=?", new String[]{String.valueOf(id)}) != 0){
                                Toast.makeText(getContext(), "Coffee updated", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }else{
                                Toast.makeText(getContext(), "Coffee update failed", Toast.LENGTH_SHORT).show();
                            }
//                        }
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
            params.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9); // Set width to 80%
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
            if(this.roast.equals("Dark")){
                spinner.setSelection(1);
                Log.d("TAG", "onCreateView: dark");
            } else if (this.roast.equals("Medium")) {
                spinner.setSelection(2);
                Log.d("TAG", "onCreateView: medium");
            } else if (this.roast.equals("Light")){
                spinner.setSelection(3);
                Log.d("TAG", "onCreateView: light");
            }
        }
    }


}