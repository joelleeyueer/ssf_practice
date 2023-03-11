package nus.iss.ssf.Controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import nus.iss.ssf.Models.CartItems;
import nus.iss.ssf.Models.Quotation;
import nus.iss.ssf.Models.ShippingAddress;
import nus.iss.ssf.Repositories.CartRepo;
import nus.iss.ssf.Services.QuotationService;

@Controller
@RequestMapping("/")
public class ShoppingCartController {


	@Autowired
	CartRepo cartrepo;

	@Autowired
	QuotationService quotationService;
    
    @GetMapping("/")
	public String getViewOne(Model model, HttpSession session) {
		//basically your homepage

		ArrayList<CartItems> cart = (ArrayList<CartItems>) session.getAttribute("cart");

		// System.out.println(cartrepo.getShoppingcart());

		model.addAttribute("cartitems", new CartItems());
		model.addAttribute("cart", cart);
		
		return "view1test";
	}
	
	@PostMapping(path="/add")
	public String postViewOne(@Valid @ModelAttribute("cartitems") CartItems cartitems, BindingResult bresult, Model model, HttpSession session) {
		
		if(bresult.hasErrors()){
            return "view1test";
        }

		String itemName = cartitems.getItemName();
		Integer qty = cartitems.getQty();

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

	@GetMapping(path="/shipping")
	public String getShippingAddress(Model model, HttpSession session) {

		ArrayList<CartItems> cart = (ArrayList<CartItems>) session.getAttribute("cart");

		if (null == cart) {
			// If cart is null, then new session
			return "redirect:/";
		}

		quotationService.printingCart(2, cart);

		session.setAttribute("cart", cart);
		model.addAttribute("ShippingAddress", new ShippingAddress());
		return "view2";
	}

	@PostMapping(path="/checkout")
	public String postInvoice(@Valid @ModelAttribute("ShippingAddress") ShippingAddress shippingAddress, BindingResult bindingResult, Model model, HttpSession session) throws Exception {
		
		if (bindingResult.hasErrors()){
			return "view2";

		}

		//get the name, address, items and its qty
		ArrayList<CartItems> cart = (ArrayList<CartItems>) session.getAttribute("cart");
		quotationService.printingCart(3, cart);
		System.out.println(shippingAddress.getname() + " " + shippingAddress.getaddress());

		Quotation quotation = new Quotation();
		quotation = quotationService.getQuotations(quotationService.convertToListString(cart));
		Float total = quotationService.calculateCart(cart, quotation);
		String invoiceId = quotation.getQuoteId();
	
		model.addAttribute("invoiceId", invoiceId);
		model.addAttribute("shippingName", shippingAddress.getname());
		model.addAttribute("shippingAddress", shippingAddress.getaddress());
		model.addAttribute("total", total);

		session.invalidate();

		return "view3";
		// return "view3";
	}
}
