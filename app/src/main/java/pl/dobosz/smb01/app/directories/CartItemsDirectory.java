package pl.dobosz.smb01.app.directories;

import pl.dobosz.smb01.app.models.CartItem;

import java.util.List;

/**
 * Created by dobosz on 2/1/16.
 */
public interface CartItemsDirectory {

  void addCardItem(CartItem cartItem);

  void deleteCartItem(CartItem cartItem);

  List<CartItem> fetchAllCartItems();

  void updateMark(CartItem cartItem, boolean marked);

}
