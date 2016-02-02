package pl.dobosz.smb01.app.directories;

import android.os.AsyncTask;
import com.appspot.shoppingapi_1208.cart.Cart;
import com.appspot.shoppingapi_1208.cart.model.CartItemCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import pl.dobosz.smb01.app.adapters.CartAdapter;
import pl.dobosz.smb01.app.models.CartItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by dobosz on 2/1/16.
 */
public class OnlineCartItemDirectoryImp implements CartItemsDirectory {

  private final Cart cart;
  public List<CartItem> cartItems = new ArrayList<>();
  private Random rand = new Random();
  private CartAdapter cartAdapter;

  public OnlineCartItemDirectoryImp() {
    Cart.Builder cartItemBuilder = new Cart.Builder(AndroidHttp.newCompatibleTransport(),
            new AndroidJsonFactory(), null);
    cart = cartItemBuilder.build();

  }

  public void setAdapter(CartAdapter cartAdapter) {
    this.cartAdapter = cartAdapter;
  }

  @Override
  public void addCardItem(CartItem cartItem) {
    cartItem.setId(rand.nextLong());
    new AsyncTask<com.appspot.shoppingapi_1208.cart.model.CartItem, Void, Void>() {
      @Override
      protected Void doInBackground(com.appspot.shoppingapi_1208.cart.model.CartItem... params) {
        try {
          cart.itemSave(params[0]).execute();
        } catch (IOException e) {
          e.printStackTrace();
        }
        return null;
      }
    }.execute(modelToDto(cartItem));
  }

  @Override
  public void deleteCartItem(CartItem cartItem) {
    new AsyncTask<Long, Void, Void>() {
      @Override
      protected Void doInBackground(Long... params) {
        try {
          cart.itemDelete(params[0]).execute();
        } catch (IOException e) {
          e.printStackTrace();
        }
        return null;
      }
    }.execute(cartItem.getId());
  }

  @Override
  public List<CartItem> fetchAllCartItems() {
    new AsyncTask<Void, Void, CartItemCollection>() {
      @Override
      protected CartItemCollection doInBackground(Void... params) {
        try {
          return cart.itemFetch().execute();
        } catch (IOException e) {
          e.printStackTrace();
        }
        return null;
      }

      @Override
      protected void onPostExecute(CartItemCollection cartItemCollection) {
        for (com.appspot.shoppingapi_1208.cart.model.CartItem dto : cartItemCollection.getItems())
          cartItems.add(dtoToModel(dto));
        cartAdapter.notifyDataSetChanged();
      }
    }.execute();
    return cartItems;
  }

  @Override
  public void updateMark(CartItem cartItem, boolean marked) {
    new AsyncTask<Long, Void, Void>() {
      @Override
      protected Void doInBackground(Long... params) {
        try {
          cart.itemMark(params[0], marked).execute();
        } catch (IOException e) {
          e.printStackTrace();
        }
        return null;
      }
    }.execute(cartItem.getId());
  }

  private com.appspot.shoppingapi_1208.cart.model.CartItem modelToDto(CartItem cartItem) {
    com.appspot.shoppingapi_1208.cart.model.CartItem dto = new com.appspot.shoppingapi_1208.cart.model.CartItem();
    dto.setId(cartItem.getId());
    dto.setName(cartItem.getName());
    dto.setDescription(cartItem.getDescription());
    dto.setMarked(cartItem.isMarked());
    dto.setQuantity(cartItem.getQuantity());
    return dto;
  }

  private CartItem dtoToModel(com.appspot.shoppingapi_1208.cart.model.CartItem dto) {
    CartItem cartItem = new CartItem();
    cartItem.setId(dto.getId());
    cartItem.setName(dto.getName());
    cartItem.setDescription(dto.getDescription());
    cartItem.setMarked(dto.getMarked());
    cartItem.setQuantity(dto.getQuantity());
    return cartItem;
  }

}
