package nus.iss.ssf.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.ArrayList;
import jakarta.servlet.http.HttpSession;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;




import nus.iss.ssf.Models.*;
import nus.iss.ssf.Services.*;
import nus.iss.ssf.Repositories.*;


import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(path={"/","view1test.html"})
public class ShoppingCartController {


	@Autowired
	CartRepo cartrepo;

	// @Autowired
	// ShoppingCartService ShoppingCartService;
    
    @GetMapping
	public String getViewOne(Model model, HttpSession session) {
		//basically your homepage

		ArrayList<CartItems> cart = (ArrayList<CartItems>) session.getAttribute("cart");




		// System.out.println(cartrepo.getShoppingcart());

		model.addAttribute("cartitems", new CartItems());
		model.addAttribute("cart", cart);
		
		return "view1test";
	}
	
	@PostMapping(path={"/add"})
	public String postViewOne(@RequestBody MultiValueMap<String, String> form, Model model, HttpSession session) {
		
		String itemName = form.getFirst("itemName");
		Integer qty = Integer.parseInt(form.getFirst("qty"));

		ArrayList<CartItems> cart = (ArrayList<CartItems>) session.getAttribute("cart");

		if (null == cart) {
			// If cart is null, then new session
			cart = new ArrayList<>();
			session.setAttribute("cart", cart);
		}
		CartItems item = new CartItems(itemName, qty);
		cartrepo.addToCart(cart, item);

		model.addAttribute("cartitems", new CartItems());
		model.addAttribute("cart", cart);

		return "redirect:/";

	}

	@GetMapping(path={"/shipping"})
	public String getShippingAddress(Model model, HttpSession session) {

		ArrayList<CartItems> cart = (ArrayList<CartItems>) session.getAttribute("cart");

		model.addAttribute("ShippingAddress", new ShippingAddress());
		return "view2";
	}
}
