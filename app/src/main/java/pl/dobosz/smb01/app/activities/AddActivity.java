package pl.dobosz.smb01.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.dobosz.smb01.app.R;
import pl.dobosz.smb01.app.directories.CartItemsDirectory;
import pl.dobosz.smb01.app.models.CartItem;
import pl.dobosz.smb01.app.providers.CartProvider;

public class AddActivity extends Activity {

    @Bind(R.id.edit_name)
    EditText editName;

    @Bind(R.id.edit_description)
    EditText editDescription;

    @Bind(R.id.edit_quantity)
    EditText editQuantity;

    private CartItemsDirectory cartItemsDirectory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        cartItemsDirectory = new CartItemsDirectory(this);
    }

    @OnClick(R.id.add_item_button)
    void addButtonClick() {
        final String name = editName.getText().toString();
        final String description = editDescription.getText().toString();
        final String quantity = editQuantity.getText().toString();
        if (name.isEmpty() || description.isEmpty() || quantity.isEmpty()) {
            Toast.makeText(this, "Please fill up all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        final CartItem cartItem = new CartItem(name, description, Integer.parseInt(quantity));
        final Intent intent = new Intent(MainActivity.SHOPPING_LIST_ADD);
        cartItemsDirectory.addCardItem(cartItem);
        intent.putExtra(MainActivity.CARD_ITEM_TAG, cartItem);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        finish();
    }

}
