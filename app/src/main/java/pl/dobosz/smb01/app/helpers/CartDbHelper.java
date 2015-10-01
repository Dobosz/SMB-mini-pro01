package pl.dobosz.smb01.app.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by dobosz on 10/1/15.
 */
public class CartDbHelper extends SQLiteOpenHelper {

    public static abstract class CartEntry implements BaseColumns {

        public static final String TABLE_NAME = "cart";
        public static final String COLUMN_NAME_NAME= "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
    }
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Cart.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CartEntry.TABLE_NAME + " (" +
                    CartEntry._ID + " INTEGER PRIMARY KEY," +
                    CartEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    CartEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    CartEntry.COLUMN_NAME_QUANTITY + INTEGER_TYPE +
            " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CartEntry.TABLE_NAME;

    public CartDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
