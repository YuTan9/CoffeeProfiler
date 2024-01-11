package edu.sjsu.android.coffeeprofiler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.Set;
import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity {


    private final String AUTHORITY = "edu.sjsu.android.coffeeprofiler";
    private final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.recycler_refresh);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the pop-up menu
                showPopupMenu();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Cursor cursor = getContentResolver().query(CONTENT_URI, new String[]{"name", "rating", "drink"}, null, null, "rating DESC");

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
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void showPopupMenu() {
        MyBottomSheetDialogFragment popupMenuFragment = new MyBottomSheetDialogFragment();
        popupMenuFragment.show(getSupportFragmentManager(), popupMenuFragment.getTag());
    }



}