package pl.dobosz.smb01.app.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
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


public class MainActivity extends Activity {

    public static final String SHOPPING_LIST_ADD = "shopping.list.add";
    public static final String CARD_ITEM_TAG = "card.item.tag";
    private CartProvider cartProvider;
    private CartAdapter cartAdapter;

    @Bind(R.id.shopping_list)
    ListView shoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        cartProvider = new CartProvider(this);
        cartAdapter = new CartAdapter(this, -1, cartProvider.fetchCartItems());
        shoppingList.setAdapter(cartAdapter);
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver,
                new IntentFilter(SHOPPING_LIST_ADD));
    }

    @OnItemClick(R.id.shopping_list)
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (shoppingList.isItemChecked(i))
            changeMarkedState(i, true);
        else
            changeMarkedState(i, false);
    }

    private void changeMarkedState(int i, boolean marked) {
        cartProvider.updateMark(cartAdapter.getItem(i), marked);
        shoppingList.setItemChecked(i, marked);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_check_all) {
            checkAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkAll() {
        int size = cartAdapter.getCount();
        for (int i = 0; i != size; i++)
            shoppingList.setItemChecked(i, true);
    }

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            cartAdapter.add((CartItem) intent.getSerializableExtra(CARD_ITEM_TAG));
        }
    };

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);
        super.onDestroy();
    }
}
