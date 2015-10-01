package pl.dobosz.smb01.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.*;
import pl.dobosz.smb01.app.R;
import pl.dobosz.smb01.app.adapters.CartAdapter;
import pl.dobosz.smb01.app.models.CartItem;
import pl.dobosz.smb01.app.providers.CartProvider;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private CartProvider cartProvider;
    private CartAdapter cartAdapter;

    @Bind(R.id.shopping_list)
    ListView shoppingList;

    @OnItemClick(R.id.shopping_list)
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (shoppingList.isItemChecked(i))
            shoppingList.setItemChecked(i, true);
        else
            shoppingList.setItemChecked(i, false);
    }

    @OnClick(R.id.add_button)
    public void addButtonClick() {
        startActivity(new Intent(this, AddActivity.class));
    }

    @OnClick(R.id.delete_button)
    public void deleteButtonClick() {
        SparseBooleanArray checkedItemPositions = shoppingList.getCheckedItemPositions().clone();
        int size = cartAdapter.getCount();
        for (int i = 0; i != size; i++)
            shoppingList.setItemChecked(i, false);
        int deletedOffset = 0;
        for (int i = 0; i != size; i++) {
            if (checkedItemPositions.get(i)) {
                CartItem cartItem = cartAdapter.getItem(i - deletedOffset++);
                cartProvider.deleteCartItem(cartItem);
                cartAdapter.remove(cartItem);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        cartProvider = new CartProvider(this);
        cartProvider.addCardItem(new CartItem("n01", "d01", 1));
        cartAdapter = new CartAdapter(this, -1, cartProvider.fetchCartItems());
        shoppingList.setAdapter(cartAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
