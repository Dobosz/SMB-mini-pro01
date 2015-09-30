package pl.dobosz.smb01.app.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import pl.dobosz.smb01.app.R;
import pl.dobosz.smb01.app.models.CartItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dobosz on 9/29/15.
 */
public class CartAdapter implements ListAdapter {

    private final List<CartItem> cart = new ArrayList<>();
    private final LayoutInflater inflater;

    public CartAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cart.add(new CartItem("n01", "d01", 1));
        cart.add(new CartItem("n02", "d02", 10));
        cart.add(new CartItem("n03", "d03", 100));
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return cart.size();
    }

    @Override
    public Object getItem(int i) {
        return cart.get(i);
    }

    @Override
    public long getItemId(int i) {
        return cart.get(i).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
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
        CartItem cartItem = cart.get(i);
        holder.name.setText(cartItem.getName());
        holder.description.setText(cartItem.getDescription());
        holder.quantity.setText(Integer.toString(cartItem.getQuantity()));
        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return cart.isEmpty();
    }

    static class ViewHolder {
        @Bind(R.id.cart_item_name)
        TextView name;
        @Bind(R.id.cart_item_description)
        TextView description;
        @Bind(R.id.cart_item_quantity)
        TextView quantity;
        @Bind(R.id.cart_item_checker)
        CheckBox checker;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

}
