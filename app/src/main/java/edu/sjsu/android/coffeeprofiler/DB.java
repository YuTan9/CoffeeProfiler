package edu.sjsu.android.coffeeprofiler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "CoffeeDb";
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "coffee";
    public static final String NAME = "name";
    public static final String ID = "_id";
    public static final String ROAST = "roast";
    public static final String DRINK = "drink";
    public static final String COARSENESS = "coarseness";
    public static final String HEAT = "heat";
    public static final String TAMP = "tamp";
    public static final String WATER = "water";
    public static final String RATING = "rating";

    public static final String WEIGHT = "weight";

    public static final String EXTRACTION = "extraction";

    public static final String NOTE = "note";
    public static final String WATER_WEIGHT = "water_weight";
    static final String CREATE_TABLE =
            " CREATE TABLE " +
                    TABLE_NAME +
                        "(" +
                            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            NAME + " TEXT NOT NULL, " +
                            ROAST + " TEXT NOT NULL, " +
                            DRINK + " TEXT NOT NULL, " +
                            COARSENESS + " INT NOT NULL, " +
                            HEAT + " INT NOT NULL, " +
                            TAMP + " INT NOT NULL, " +
                            WATER + " INT NOT NULL, " +
                            RATING + " INT NOT NULL, " +
                            NOTE + " TEXT, " +
                            WEIGHT + " DOUBLE, " +
                            EXTRACTION + " INT, " +
                            WATER_WEIGHT + " INT" +
                        ");";
    public DB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldV, int newV) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insert(ContentValues contentValues) {
        SQLiteDatabase database = getWritableDatabase();
        return database.insert(TABLE_NAME, null, contentValues);
    }

//    public Cursor getAll(String orderBy) {
//        SQLiteDatabase database = getWritableDatabase();
//        return database.query(TABLE_NAME,
//                new String[]{NAME, ROAST, DRINK, COARSENESS, HEAT, TAMP, WATER, RATING},
//                null, null, null, null, orderBy);
//    }

    public Cursor query(@Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = getWritableDatabase();

        return database.query(TABLE_NAME,
                projection,
                selection, selectionArgs, null, null, sortOrder);
    }

    public int update(@Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs){
        SQLiteDatabase database = getWritableDatabase();
        return database.update(TABLE_NAME, values, selection, selectionArgs);
    }
    public int delete(@Nullable String selection, @Nullable String[] selectionArgs){
        SQLiteDatabase database = getWritableDatabase();
        return database.delete(TABLE_NAME, selection, selectionArgs);
    }

}
