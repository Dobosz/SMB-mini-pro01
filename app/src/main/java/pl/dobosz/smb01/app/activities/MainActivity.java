package pl.dobosz.smb01.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.*;
import butterknife.internal.ListenerClass;
import pl.dobosz.smb01.app.R;
import pl.dobosz.smb01.app.adapters.CartAdapter;
import pl.dobosz.smb01.app.models.CartItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends Activity {

    List<CartItem> cart = new ArrayList<>();

    @Bind(R.id.shopping_list)
    ListView shoppingList;

    @OnItemClick(R.id.shopping_list)
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (shoppingList.isItemChecked(i))
            shoppingList.setItemChecked(i, true);
        else
            shoppingList.setItemChecked(i, false);
    }

    @OnClick(R.id.delete_button)
    public void deleteButtonClick() {
        cart.remove(position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        cart.add(new CartItem("n01", "d01", 1));
        cart.add(new CartItem("n02", "d02", 10));
        cart.add(new CartItem("n03", "d03", 100));
        cart.add(new CartItem("n04", "d04", 200));
        cart.add(new CartItem("n05", "d05", 200));
        cart.add(new CartItem("n06", "d06", 200));
        cart.add(new CartItem("n07", "d07", 200));
        cart.add(new CartItem("n08", "d08", 200));
        cart.add(new CartItem("n09", "d09", 200));
        cart.add(new CartItem("n10", "d10", 200));
        cart.add(new CartItem("n11", "d11", 200));
        cart.add(new CartItem("n12", "d12", 200));
        shoppingList.setAdapter(new CartAdapter(this, -1, cart));
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
