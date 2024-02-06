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
import android.view.Menu;
import android.view.MenuItem;
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
                Cursor cursor = getContentResolver().query(CONTENT_URI, new String[]{"name", "rating", "drink", "roast"}, null, null, "rating DESC");

                ItemFragment.items.clear();
                ItemFragment.adapter.notifyDataSetChanged();
                while(cursor.moveToNext()){
                    ItemFragment.items.add(new Row(
                            cursor.getString(cursor.getColumnIndex("name")),
                            cursor.getString(cursor.getColumnIndex("drink")),
                            cursor.getInt(cursor.getColumnIndex("rating")),
                            cursor.getString(cursor.getColumnIndex("roast"))
                    ));
                }

                ItemFragment.adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
// TODO: 1/11/2024
//  implement search functionality

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.my_menu, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.app_bar_search) {
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private void showPopupMenu() {
        MyBottomSheetDialogFragment popupMenuFragment = new MyBottomSheetDialogFragment();
        popupMenuFragment.show(getSupportFragmentManager(), popupMenuFragment.getTag());
    }



}