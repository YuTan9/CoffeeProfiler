package edu.sjsu.android.coffeeprofiler;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import edu.sjsu.android.coffeeprofiler.databinding.FragmentItemBinding;

import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

//    private final List<String> names;
//    private final List<Integer> ratings;
//    private final List<String> drinks;
    private Context context;


    private final List<Row> items;
    private final String AUTHORITY = "edu.sjsu.android.coffeeprofiler";
    private final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);


//    public MyItemRecyclerViewAdapter(List<String> items, List<Integer> r, List<String> d) {
//        ratings = r;
//        names = items;
//        drinks = d;
//    }

    public MyItemRecyclerViewAdapter(List<Row> i) {
        items = i;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        String current = names.get(position);
//        holder.binding.recyclerCoffeeName.setText(current);
//        holder.binding.recyclerCoffeeDrink.setText(drinks.get(position));
        Row current = items.get(position);
        holder.binding.recyclerCoffeeDrink.setText(current.getDrink());
        holder.binding.recyclerCoffeeName.setText(current.getName());
        int rating = current.getRating();
        switch(rating){
            case 0:
                holder.binding.star1.setImageResource(R.drawable.baseline_star_off);
                holder.binding.star2.setImageResource(R.drawable.baseline_star_off);
                holder.binding.star3.setImageResource(R.drawable.baseline_star_off);
                holder.binding.star4.setImageResource(R.drawable.baseline_star_off);
                holder.binding.star5.setImageResource(R.drawable.baseline_star_off);
                break;
            case 1:
                holder.binding.star1.setImageResource(R.drawable.baseline_star_on);
                holder.binding.star2.setImageResource(R.drawable.baseline_star_off);
                holder.binding.star3.setImageResource(R.drawable.baseline_star_off);
                holder.binding.star4.setImageResource(R.drawable.baseline_star_off);
                holder.binding.star5.setImageResource(R.drawable.baseline_star_off);
                break;
            case 2:
                holder.binding.star1.setImageResource(R.drawable.baseline_star_on);
                holder.binding.star2.setImageResource(R.drawable.baseline_star_on);
                holder.binding.star3.setImageResource(R.drawable.baseline_star_off);
                holder.binding.star4.setImageResource(R.drawable.baseline_star_off);
                holder.binding.star5.setImageResource(R.drawable.baseline_star_off);
                break;
            case 3:
                holder.binding.star1.setImageResource(R.drawable.baseline_star_on);
                holder.binding.star2.setImageResource(R.drawable.baseline_star_on);
                holder.binding.star3.setImageResource(R.drawable.baseline_star_on);
                holder.binding.star4.setImageResource(R.drawable.baseline_star_off);
                holder.binding.star5.setImageResource(R.drawable.baseline_star_off);
                break;
            case 4:
                holder.binding.star1.setImageResource(R.drawable.baseline_star_on);
                holder.binding.star2.setImageResource(R.drawable.baseline_star_on);
                holder.binding.star3.setImageResource(R.drawable.baseline_star_on);
                holder.binding.star4.setImageResource(R.drawable.baseline_star_on);
                holder.binding.star5.setImageResource(R.drawable.baseline_star_off);
                break;
            case 5:
                holder.binding.star1.setImageResource(R.drawable.baseline_star_on);
                holder.binding.star2.setImageResource(R.drawable.baseline_star_on);
                holder.binding.star3.setImageResource(R.drawable.baseline_star_on);
                holder.binding.star4.setImageResource(R.drawable.baseline_star_on);
                holder.binding.star5.setImageResource(R.drawable.baseline_star_on);
                break;
            default:
                break;
        }
        holder.binding.recyclerRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, "name=? AND drink=?",
                        new String[]{holder.binding.recyclerCoffeeName.getText().toString(),
                                holder.binding.recyclerCoffeeDrink.getText().toString()} , null);

                cursor.moveToNext();
                EditDetailDialogFragment popupMenuFragment = new EditDetailDialogFragment(
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("roast")),
                        cursor.getString(cursor.getColumnIndex("drink")),
                        cursor.getInt(cursor.getColumnIndex("coarseness")),
                        cursor.getInt(cursor.getColumnIndex("heat")),
                        cursor.getInt(cursor.getColumnIndex("tamp")),
                        cursor.getInt(cursor.getColumnIndex("water")),
                        cursor.getInt(cursor.getColumnIndex("rating")),
                        cursor.getInt(cursor.getColumnIndex("_id"))
                );
                popupMenuFragment.show(((AppCompatActivity)context).getSupportFragmentManager(), popupMenuFragment.getTag());
            }
        });
    }




    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected final FragmentItemBinding binding;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}