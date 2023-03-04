package nus.iss.ssf.Repositories;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import nus.iss.ssf.Models.CartItems;

@Repository
public class CartRepo {


    public ArrayList<CartItems> updateCartValue(ArrayList<CartItems> incomingCart, Integer index, String cartItem, Integer updateQty) {
                Integer newQty = incomingCart.get(index).getQty() + updateQty;
                CartItems newCart = new CartItems(cartItem,newQty);
                incomingCart.set(index,newCart);
        
                return incomingCart;
            }

    public ArrayList<CartItems> addToCart(ArrayList<CartItems> incomingCart, CartItems incomingItem) {
                //check if item exists
                //if yes, add to it
                //if no, add new item
                String incomingItemName = incomingItem.getItemName();
                Integer incomingQty = incomingItem.getQty();
                Integer currentQty = 0;
                Boolean isItemExist = false;
                Integer itemIndex = 0;
        
                for (CartItems index:incomingCart){
                    if (index.getItemName().equalsIgnoreCase(incomingItemName)){
                        isItemExist = true;
                    }
                }
        
                for (int i = 0; i<incomingCart.size(); i++){
                    if (incomingCart.get(i).getItemName().equalsIgnoreCase(incomingItemName)){
                        isItemExist = true;
                    }
                }
                //item exists, updating the qty
                if (isItemExist){
                    incomingCart = updateCartValue(incomingCart, itemIndex, incomingItemName,incomingQty);
                    return incomingCart;

                } else { //item does not exist, just add
                    incomingCart.add(incomingItem);
                    return incomingCart;
                    
                }

            }
}
