package pl.dobosz.smb01.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.dobosz.smb01.app.R;
import pl.dobosz.smb01.app.models.CartItem;
import pl.dobosz.smb01.app.providers.CartProvider;

public class AddActivity extends Activity {

    @Bind(R.id.edit_name)
    EditText editName;

    @Bind(R.id.edit_description)
    EditText editDescription;

    @Bind(R.id.edit_quantity)
    EditText editQuantity;

    private CartProvider cartProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        cartProvider = new CartProvider(this);
    }

    @OnClick(R.id.add_item_button)
    void addButtonClick() {
        final String name = editName.getText().toString();
        final String description = editDescription.getText().toString();
        final int quantity = Integer.parseInt(editQuantity.getText().toString());
        final CartItem cartItem = new CartItem(name, description, quantity);
        final Intent intent = new Intent(MainActivity.SHOPPING_LIST_ADD);
        cartProvider.addCardItem(cartItem);
        intent.putExtra(MainActivity.CARD_ITEM_TAG, cartItem);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        finish();
    }

}
