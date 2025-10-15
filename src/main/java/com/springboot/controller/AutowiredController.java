package com.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.service.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import com.springboot.service.Animal;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class AutowiredController {


	List<OfferRequest> allOffers = new ArrayList<>();

	@PostMapping(path = "/api/v1/offer")
	public ApiResponse postOperation(@RequestBody OfferRequest offerRequest) {
		System.out.println(offerRequest);
		allOffers.add(offerRequest);
		return new ApiResponse("success");
	}

	@PostMapping(path = "/api/v1/cart/apply_offer")
	public ApplyOfferResponse applyOffer(@RequestBody ApplyOfferRequest applyOfferRequest) throws Exception {
		System.out.println("=== CART OFFER DEBUG ===");
		System.out.println("Request: " + applyOfferRequest);
		System.out.println("Total offers in system: " + allOffers.size());
		for (int i = 0; i < allOffers.size(); i++) {
			System.out.println("Offer " + i + ": " + allOffers.get(i));
		}
		
		int cartVal = applyOfferRequest.getCart_value();
		SegmentResponse segmentResponse = getSegmentResponse(applyOfferRequest.getUser_id());
		System.out.println("User segment: " + segmentResponse.getSegment());
		
		Optional<OfferRequest> matchRequest = allOffers.stream().filter(x->x.getRestaurant_id()==applyOfferRequest.getRestaurant_id())
				.filter(x->x.getCustomer_segment().contains(segmentResponse.getSegment()))
				.findFirst();

		if(matchRequest.isPresent()){
			System.out.println("got a match: " + matchRequest.get());
			OfferRequest gotOffer = matchRequest.get();

			if(gotOffer.getOffer_type().equals("FLATX")) {
				cartVal = cartVal - gotOffer.getOffer_value();
				System.out.println("Applied FLATX discount: " + gotOffer.getOffer_value() + ", new cart value: " + cartVal);
			} else {
				cartVal = (int) (cartVal - cartVal * gotOffer.getOffer_value()*(0.01));
				System.out.println("Applied PERCENT discount: " + gotOffer.getOffer_value() + "%, new cart value: " + cartVal);
			}
		} else {
			System.out.println("No matching offer found");
		}
		System.out.println("=== END DEBUG ===");
		return new ApplyOfferResponse(cartVal);
	}

	private SegmentResponse getSegmentResponse(int userid)
	{
		SegmentResponse segmentResponse = new SegmentResponse();
		try {
			String urlString = "http://localhost:1080/api/v1/user_segment?" + "user_id=" + userid;
			URL url = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();


			connection.setRequestProperty("accept", "application/json");

			// This line makes the request
			InputStream responseStream = connection.getInputStream();

			// Manually converting the response body InputStream to APOD using Jackson
			ObjectMapper mapper = new ObjectMapper();
			 segmentResponse = mapper.readValue(responseStream,SegmentResponse.class);
			System.out.println("got segment response" + segmentResponse);


		} catch (Exception e) {
			System.out.println("Mock server not available, using hardcoded segments for testing");
			// For testing purposes, return hardcoded segments based on user_id
			if (userid == 1) {
				segmentResponse.setSegment("p1");
			} else if (userid == 2) {
				segmentResponse.setSegment("p2");
			} else if (userid == 3) {
				segmentResponse.setSegment("p3");
			} else {
				segmentResponse.setSegment("p1"); // default to p1
			}
			System.out.println("Using hardcoded segment: " + segmentResponse.getSegment());
		}
		return segmentResponse;
	}


}
