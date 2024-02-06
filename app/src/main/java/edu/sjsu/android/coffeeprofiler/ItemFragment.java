package edu.sjsu.android.coffeeprofiler;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A fragment representing a list of Items.
 */
public class ItemFragment extends Fragment {

    private final String AUTHORITY = "edu.sjsu.android.coffeeprofiler";
    private final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
//    static ArrayList<String> names = new ArrayList<>();
//    static ArrayList<String> drinks = new ArrayList<>();
//    static ArrayList<Integer> ratings = new ArrayList<>();

    static ArrayList<Row> items = new ArrayList<>();
    static MyItemRecyclerViewAdapter adapter;
    static RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void load(){
        Cursor cursor = getContext().getContentResolver().query(CONTENT_URI, new String[]{"name", "rating", "drink", "roast"}, null, null, "rating DESC");

        while(cursor.moveToNext()){
            items.add(new Row(
                            cursor.getString(cursor.getColumnIndex("name")),
                            cursor.getString(cursor.getColumnIndex("drink")),
                            cursor.getInt(cursor.getColumnIndex("rating")),
                            cursor.getString(cursor.getColumnIndex("roast"))
            ));
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        load();
//        adapter = new MyItemRecyclerViewAdapter(names, ratings, drinks);
        adapter = new MyItemRecyclerViewAdapter(items);
        recyclerView = (RecyclerView) view;
        recyclerView.setAdapter(adapter);
        swipeToDelete();
        return view;
    }

    public void swipeToDelete() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getLayoutPosition();
//                String name = names.get(pos);
//                int rating = ratings.get(pos);
//                String drink = drinks.get(pos);
                Row current = items.get(pos);
                getContext().getContentResolver().delete(CONTENT_URI, "name=? AND rating=? AND drink=?",
                        new String[]{current.getName(), String.valueOf(current.getRating()), current.getDrink()});
//                names.remove(pos);
//                ratings.remove(pos);
//                drinks.remove(pos);
                items.remove(pos);
                adapter.notifyItemRemoved(pos);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}