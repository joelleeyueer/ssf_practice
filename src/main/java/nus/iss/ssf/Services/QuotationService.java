package nus.iss.ssf.Services;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import nus.iss.ssf.Models.CartItems;
import nus.iss.ssf.Models.Quotation;

@Service
public class QuotationService {

    public static final String chukAPI = "https://quotation.chuklee.com/quotation";

	public List<String> convertToListString(List<CartItems> cart){
		List<String> itemNameList = new ArrayList<>();

		for (int i = 0; i < cart.size(); i++){
			itemNameList.add(cart.get(i).getItemName());
		}

		return itemNameList;
	}

	public Quotation getQuotations(List<String> items) throws Exception {


		//create url
		String url = UriComponentsBuilder
				.fromUriString(chukAPI)
				.toUriString();

		//build the jsonArray
		JsonArrayBuilder arrayFactory = Json.createArrayBuilder(items);
		for (int i = 0; i<items.size(); i++){
			arrayFactory.add(items.get(i));
		}

		JsonArray jsonArrayPayload = arrayFactory.build();


		//fill up header info
		RequestEntity<String> req = RequestEntity.post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(jsonArrayPayload.toString());

		RestTemplate template = new RestTemplate();
		ResponseEntity<String> resp = template.exchange(req, String.class);

		String body = resp.getBody();
		JsonReader reader = Json.createReader(new StringReader(body));
		JsonObject obj = reader.readObject();
		JsonArray incomingArr = obj.getJsonArray("quotations");
        String incomingQuotation = obj.getString("quoteId");

		Quotation quotation = new Quotation();
		System.out.println(incomingQuotation); //to test
		quotation.setQuoteId(incomingQuotation);

		//iterate through jsonarray

		for (int i = 0; i < incomingArr.size(); i++) {
			JsonObject incomingItem = incomingArr.getJsonObject(i);

			quotation.addQuotation(incomingItem.getString("item"), (float)incomingItem.getJsonNumber("unitPrice").doubleValue());
		}

		System.out.println(quotation.getQuotations()); //to test

		return quotation;
		        
	}
	public void printingCart(int viewNo, List<CartItems> cart){
		System.out.println("Printing cart in view " + viewNo + ":");
		int i = 0;
		for (CartItems cartIndex : cart){
			System.out.println(++i + ". " + cartIndex.getItemName() + " " + cartIndex.getQty());
		}
	}

	public Float calculateCart(List<CartItems> cart, Quotation quotation){
		double tally = 0.0;


		for (int i = 0; i < cart.size(); i++){
			double qty = cart.get(i).getQty();
			String currentItem = cart.get(i).getItemName();
			double itemCost = (double) quotation.getQuotation(currentItem);
			double total = qty * itemCost;
			tally += total;
			System.out.println("Finding the cost of " + currentItem+ "\n" + qty + " * " + itemCost + " = " + total);
		}
		System.out.println("Tally=" + tally);

		return (float)tally;
	}
	
    
}
