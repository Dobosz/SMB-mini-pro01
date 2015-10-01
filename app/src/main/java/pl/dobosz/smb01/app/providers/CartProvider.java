package pl.dobosz.smb01.app.providers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import pl.dobosz.smb01.app.helpers.CartDbHelper;
import pl.dobosz.smb01.app.models.CartItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dobosz on 10/1/15.
 */
public class CartProvider {

    private CartDbHelper cartDbHelper;

    private String[] projection = {
            CartDbHelper.CartEntry._ID,
            CartDbHelper.CartEntry.COLUMN_NAME_NAME,
            CartDbHelper.CartEntry.COLUMN_NAME_DESCRIPTION,
            CartDbHelper.CartEntry.COLUMN_NAME_QUANTITY
    };

    public CartProvider(Context context) {
        cartDbHelper = new CartDbHelper(context);
    }

    public void addCardItem(CartItem cartItem) {
        SQLiteDatabase db = cartDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CartDbHelper.CartEntry.COLUMN_NAME_NAME, cartItem.getName());
        values.put(CartDbHelper.CartEntry.COLUMN_NAME_DESCRIPTION, cartItem.getDescription());
        values.put(CartDbHelper.CartEntry.COLUMN_NAME_QUANTITY, cartItem.getQuantity());
        long newId = db.insert(
                CartDbHelper.CartEntry.TABLE_NAME,
                null,
                values);
        cartItem.setId(newId);
    }

    public void deleteCartItem(CartItem cartItem) {
        SQLiteDatabase db = cartDbHelper.getWritableDatabase();
        String selection = CartDbHelper.CartEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(cartItem.getId()) };
        db.delete(CartDbHelper.CartEntry.TABLE_NAME, selection, selectionArgs);
    }

    public List<CartItem> fetchCartItems() {
        SQLiteDatabase db = cartDbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                CartDbHelper.CartEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        return getCartItems(cursor);
    }

    private List<CartItem> getCartItems(Cursor cursor) {
        List<CartItem> cartItems = new ArrayList<>();
        while(cursor.moveToNext()) {
            CartItem cartItem = new CartItem();
            cartItem.setId(cursor.getLong(cursor.getColumnIndexOrThrow(CartDbHelper.CartEntry._ID)));
            cartItem.setName(cursor.getString(cursor.getColumnIndexOrThrow(CartDbHelper.CartEntry.COLUMN_NAME_NAME)));
            cartItem.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(CartDbHelper.CartEntry.COLUMN_NAME_DESCRIPTION)));
            cartItem.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(CartDbHelper.CartEntry.COLUMN_NAME_QUANTITY)));
            cartItems.add(cartItem);
        }
        return cartItems;
    }

}
