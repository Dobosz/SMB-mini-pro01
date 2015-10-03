package pl.dobosz.smb01.app.directories;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import pl.dobosz.smb01.app.helpers.CartDbHelper;
import pl.dobosz.smb01.app.models.CartItem;
import pl.dobosz.smb01.app.providers.CartProvider;

import java.util.ArrayList;
import java.util.List;

public class CartItemsDirectory {

    public static final Uri PROVIDER_URL = Uri.parse("content://pl.dobosz.smb01.app.providers.CartProvider/cart");
    final ContentResolver resolver;

    public CartItemsDirectory(Context context) {
        resolver = context.getContentResolver();
    }

    public void addCardItem(CartItem cartItem) {
        ContentValues values = new ContentValues();
        values.put(CartDbHelper.CartEntry.COLUMN_NAME_NAME, cartItem.getName());
        values.put(CartDbHelper.CartEntry.COLUMN_NAME_DESCRIPTION, cartItem.getDescription());
        values.put(CartDbHelper.CartEntry.COLUMN_NAME_QUANTITY, cartItem.getQuantity());
        values.put(CartDbHelper.CartEntry.COLUMN_NAME_MARKED, false);
        Uri resultUri = resolver.insert(Uri.parse(CartProvider.URL), values);
        cartItem.setId(ContentUris.parseId(resultUri));
    }

    public void deleteCartItem(CartItem cartItem) {
        String selection = CartDbHelper.CartEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(cartItem.getId())};
        resolver.delete(Uri.parse(CartProvider.URL), selection, selectionArgs);
    }

    public List<CartItem> fetchAllCartItems() {
        String[] projection = {
                CartDbHelper.CartEntry._ID,
                CartDbHelper.CartEntry.COLUMN_NAME_NAME,
                CartDbHelper.CartEntry.COLUMN_NAME_DESCRIPTION,
                CartDbHelper.CartEntry.COLUMN_NAME_QUANTITY,
                CartDbHelper.CartEntry.COLUMN_NAME_MARKED
        };
        Cursor cursor = resolver.query(PROVIDER_URL, projection, null, null, null);
        cursor.moveToFirst();
        return packCartItems(cursor);
    }

    private List<CartItem> packCartItems(Cursor cursor) {
        List<CartItem> cartItems = new ArrayList<>();
        while (cursor.moveToNext()) {
            CartItem cartItem = new CartItem();
            cartItem.setId(cursor.getLong(cursor.getColumnIndexOrThrow(CartDbHelper.CartEntry._ID)));
            cartItem.setName(cursor.getString(cursor.getColumnIndexOrThrow(CartDbHelper.CartEntry.COLUMN_NAME_NAME)));
            cartItem.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(CartDbHelper.CartEntry.COLUMN_NAME_DESCRIPTION)));
            cartItem.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(CartDbHelper.CartEntry.COLUMN_NAME_QUANTITY)));
            cartItem.setMarked(cursor.getInt(cursor.getColumnIndexOrThrow(CartDbHelper.CartEntry.COLUMN_NAME_MARKED)) != 0);
            cartItems.add(cartItem);
        }
        return cartItems;
    }

    public void updateMark(CartItem cartItem, boolean marked) {
        ContentValues values = new ContentValues();
        values.put(CartDbHelper.CartEntry.COLUMN_NAME_MARKED, marked ? 1 : 0);
        String selection = CartDbHelper.CartEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(cartItem.getId())};
        resolver.update(Uri.parse(CartProvider.URL), values, selection, selectionArgs);
        cartItem.setMarked(marked);
    }

}
