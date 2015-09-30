package pl.dobosz.smb01.app.adapters;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import pl.dobosz.smb01.app.R;
import pl.dobosz.smb01.app.models.CartItem;

import java.util.List;

/**
 * Created by dobosz on 9/29/15.
 */
public class CartAdapter extends ArrayAdapter<CartItem> {

    private final LayoutInflater inflater;

    public CartAdapter(Activity activity, int resource, List<CartItem> objects) {
        super(activity, resource, objects);
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ButterKnife.bind(activity);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.cart_item, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        CartItem cartItem = getItem(i);
        holder.name.setText(cartItem.getName());
        holder.description.setText(cartItem.getDescription());
        holder.quantity.setText(Integer.toString(cartItem.getQuantity()));
        ListView shoppingList = (ListView) viewGroup;
        if(shoppingList.isItemChecked(i))
            holder.checkBox.setChecked(true);
        else
            holder.checkBox.setChecked(false);
        return view;
    }

    public static class ViewHolder {
        @Bind(R.id.cart_item_name)
        public TextView name;
        @Bind(R.id.cart_item_description)
        public TextView description;
        @Bind(R.id.cart_item_quantity)
        public TextView quantity;
        @Bind(R.id.cart_item_checker)
        public CheckBox checkBox;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

}
