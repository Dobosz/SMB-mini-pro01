package pl.dobosz.smb01.app.providers;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import pl.dobosz.smb01.app.helpers.CartDbHelper;

public class CartProvider extends ContentProvider {

    public static final String PROVIDER_NAME = "pl.dobosz.smb01.app.providers.CartProvider";
    public static final String URL = "content://" + PROVIDER_NAME + "/cart";
    static final Uri CONTENT_URI = Uri.parse(URL);

    final static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    final static int ALL_CART_KEY = 1;
    final static int ID_CART_KEY = 2;
    static {
        uriMatcher.addURI(PROVIDER_NAME, "cart", ALL_CART_KEY);
        uriMatcher.addURI(PROVIDER_NAME, "cart/#", ID_CART_KEY);
    }

    private CartDbHelper cartDbHelper;

    public CartProvider() {
    }

    @Override
    public boolean onCreate() {
        Log.i(this.getClass().getSimpleName(), "Provider started");
        cartDbHelper = new CartDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = cartDbHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case ALL_CART_KEY:
                return db.query(
                        CartDbHelper.CartEntry.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        null
                );
            case ID_CART_KEY:
                selection = selection + "_ID = " + uri.getLastPathSegment();
                return db.query(
                        CartDbHelper.CartEntry.TABLE_NAME,
                        projection,
                        selection,
                        null,
                        null,
                        null,
                        null
                );
            default:
                return null;
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = cartDbHelper.getWritableDatabase();
        long id = db.insert(
                CartDbHelper.CartEntry.TABLE_NAME,
                null,
                contentValues);
        if (id > 0) {
            Uri resourceUri = ContentUris.withAppendedId(CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(resourceUri, null);
            return resourceUri;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = cartDbHelper.getWritableDatabase();
        return db.delete(CartDbHelper.CartEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = cartDbHelper.getReadableDatabase();
        return db.update(CartDbHelper.CartEntry.TABLE_NAME, values, selection, selectionArgs);
    }

}
