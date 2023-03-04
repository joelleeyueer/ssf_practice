package nus.iss.ssf.Services;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonArray;

import nus.iss.ssf.Models.*;

public class QuotationService {

    public static final String chukAPI = "https://quotation.chuklee.com";

	private Quotation quotation;


    public void getQuotation() {

		String url = UriComponentsBuilder
				.fromUriString(chukAPI)
				.queryParam("pageSize", 7)
				.toUriString();

		RequestEntity<Void> req = RequestEntity.get(url)
				.header(chukAPI)
				.accept(MediaType.APPLICATION_JSON)
				.build();

		RestTemplate template = new RestTemplate();
		ResponseEntity<String> resp = template.exchange(req, String.class);

		String body = resp.getBody();
		JsonReader reader = Json.createReader(new StringReader(body));
		JsonObject obj = reader.readObject();
		JsonArray arr = obj.getJsonArray("quotations");
        JsonObject quotation = obj.getJsonObject("quoteId");


		//put into quoteID
		// quotation.setQuoteId(quotation.toString());

		// //put into Quotations
		// try {
		// 	for (int i = 0; i < arr.size(); i++) {            
		// 		String name = arr.getJsonObject(i).getString("quotations");
		// 		Float quantity = arr.getJsonObject(i).getFloat("quantity");
		// 		quotation.setQuotations(name,quantity);
		// 	}
		// }
		// catch (Exception e){
		// 	System.err.print(e);
		// }
        
	}
    
}
