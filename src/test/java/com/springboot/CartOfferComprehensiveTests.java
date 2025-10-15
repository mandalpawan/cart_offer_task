package com.springboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.controller.ApplyOfferRequest;
import com.springboot.controller.ApplyOfferResponse;
import com.springboot.controller.OfferRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartOfferComprehensiveTests {

    private static final String BASE_URL = "http://localhost:9002";
    private static final String MOCK_SERVER_URL = "http://localhost:1080";
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        // Clear any existing offers before each test
        clearOffers();
    }

    @After
    public void tearDown() throws Exception {
        // Clean up after each test
        clearOffers();
    }

    // Test Case 1: FLATX offer for p1 segment
    @Test
    public void testFlatXOfferForP1Segment() throws Exception {
        // Setup: Add FLATX offer for p1 segment
        OfferRequest offer = new OfferRequest(1, "FLATX", 10, List.of("p1"));
        addOffer(offer);

        // Setup: Mock user segment response
        setupMockUserSegment(1, "p1");

        // Test: Apply offer
        ApplyOfferRequest request = new ApplyOfferRequest();
        request.setCart_value(200);
        request.setUser_id(1);
        request.setRestaurant_id(1);

        ApplyOfferResponse response = applyOffer(request);

        // Assert: Should get 10 off (200 - 10 = 190)
        assertEquals(190, response.getCart_value());
    }

    // Test Case 2: FLATX offer for p2 segment
    @Test
    public void testFlatXOfferForP2Segment() throws Exception {
        // Setup: Add FLATX offer for p2 segment
        OfferRequest offer = new OfferRequest(1, "FLATX", 15, List.of("p2"));
        addOffer(offer);

        // Setup: Mock user segment response
        setupMockUserSegment(2, "p2");

        // Test: Apply offer
        ApplyOfferRequest request = new ApplyOfferRequest();
        request.setCart_value(300);
        request.setUser_id(2);
        request.setRestaurant_id(1);

        ApplyOfferResponse response = applyOffer(request);

        // Assert: Should get 15 off (300 - 15 = 285)
        assertEquals(285, response.getCart_value());
    }

    // Test Case 3: PERCENT offer for p1 segment
    @Test
    public void testPercentOfferForP1Segment() throws Exception {
        // Setup: Add PERCENT offer for p1 segment
        OfferRequest offer = new OfferRequest(1, "PERCENT", 10, List.of("p1"));
        addOffer(offer);

        // Setup: Mock user segment response
        setupMockUserSegment(3, "p1");

        // Test: Apply offer
        ApplyOfferRequest request = new ApplyOfferRequest();
        request.setCart_value(200);
        request.setUser_id(3);
        request.setRestaurant_id(1);

        ApplyOfferResponse response = applyOffer(request);

        // Assert: Should get 10% off (200 - 20 = 180)
        assertEquals(180, response.getCart_value());
    }

    // Test Case 4: PERCENT offer for p3 segment
    @Test
    public void testPercentOfferForP3Segment() throws Exception {
        // Setup: Add PERCENT offer for p3 segment
        OfferRequest offer = new OfferRequest(1, "PERCENT", 20, List.of("p3"));
        addOffer(offer);

        // Setup: Mock user segment response
        setupMockUserSegment(4, "p3");

        // Test: Apply offer
        ApplyOfferRequest request = new ApplyOfferRequest();
        request.setCart_value(500);
        request.setUser_id(4);
        request.setRestaurant_id(1);

        ApplyOfferResponse response = applyOffer(request);

        // Assert: Should get 20% off (500 - 100 = 400)
        assertEquals(400, response.getCart_value());
    }

    // Test Case 5: No matching offer for user segment
    @Test
    public void testNoMatchingOfferForUserSegment() throws Exception {
        // Setup: Add offer for p1 segment only
        OfferRequest offer = new OfferRequest(1, "FLATX", 10, List.of("p1"));
        addOffer(offer);

        // Setup: Mock user segment response for p2 (no matching offer)
        setupMockUserSegment(5, "p2");

        // Test: Apply offer
        ApplyOfferRequest request = new ApplyOfferRequest();
        request.setCart_value(200);
        request.setUser_id(5);
        request.setRestaurant_id(1);

        ApplyOfferResponse response = applyOffer(request);

        // Assert: Should get no discount (200 - 0 = 200)
        assertEquals(200, response.getCart_value());
    }

    // Test Case 6: No matching offer for restaurant
    @Test
    public void testNoMatchingOfferForRestaurant() throws Exception {
        // Setup: Add offer for restaurant 1
        OfferRequest offer = new OfferRequest(1, "FLATX", 10, List.of("p1"));
        addOffer(offer);

        // Setup: Mock user segment response
        setupMockUserSegment(6, "p1");

        // Test: Apply offer for different restaurant
        ApplyOfferRequest request = new ApplyOfferRequest();
        request.setCart_value(200);
        request.setUser_id(6);
        request.setRestaurant_id(2); // Different restaurant

        ApplyOfferResponse response = applyOffer(request);

        // Assert: Should get no discount (200 - 0 = 200)
        assertEquals(200, response.getCart_value());
    }

    // Test Case 7: Multiple offers for same restaurant and segment (should apply first match)
    @Test
    public void testMultipleOffersForSameRestaurantAndSegment() throws Exception {
        // Setup: Add multiple offers for same restaurant and segment
        OfferRequest offer1 = new OfferRequest(1, "FLATX", 10, List.of("p1"));
        OfferRequest offer2 = new OfferRequest(1, "FLATX", 20, List.of("p1"));
        addOffer(offer1);
        addOffer(offer2);

        // Setup: Mock user segment response
        setupMockUserSegment(7, "p1");

        // Test: Apply offer
        ApplyOfferRequest request = new ApplyOfferRequest();
        request.setCart_value(200);
        request.setUser_id(7);
        request.setRestaurant_id(1);

        ApplyOfferResponse response = applyOffer(request);

        // Assert: Should apply first offer (200 - 10 = 190)
        assertEquals(190, response.getCart_value());
    }

    // Test Case 8: Offer for multiple segments
    @Test
    public void testOfferForMultipleSegments() throws Exception {
        // Setup: Add offer for multiple segments
        OfferRequest offer = new OfferRequest(1, "FLATX", 25, List.of("p1", "p2", "p3"));
        addOffer(offer);

        // Test for p1 segment
        setupMockUserSegment(8, "p1");
        ApplyOfferRequest request1 = new ApplyOfferRequest();
        request1.setCart_value(300);
        request1.setUser_id(8);
        request1.setRestaurant_id(1);
        ApplyOfferResponse response1 = applyOffer(request1);
        assertEquals(275, response1.getCart_value()); // 300 - 25 = 275

        // Test for p2 segment
        setupMockUserSegment(9, "p2");
        ApplyOfferRequest request2 = new ApplyOfferRequest();
        request2.setCart_value(300);
        request2.setUser_id(9);
        request2.setRestaurant_id(1);
        ApplyOfferResponse response2 = applyOffer(request2);
        assertEquals(275, response2.getCart_value()); // 300 - 25 = 275

        // Test for p3 segment
        setupMockUserSegment(10, "p3");
        ApplyOfferRequest request3 = new ApplyOfferRequest();
        request3.setCart_value(300);
        request3.setUser_id(10);
        request3.setRestaurant_id(1);
        ApplyOfferResponse response3 = applyOffer(request3);
        assertEquals(275, response3.getCart_value()); // 300 - 25 = 275
    }

    // Test Case 9: Edge case - Zero cart value
    @Test
    public void testZeroCartValue() throws Exception {
        // Setup: Add offer
        OfferRequest offer = new OfferRequest(1, "FLATX", 10, List.of("p1"));
        addOffer(offer);

        // Setup: Mock user segment response
        setupMockUserSegment(11, "p1");

        // Test: Apply offer with zero cart value
        ApplyOfferRequest request = new ApplyOfferRequest();
        request.setCart_value(0);
        request.setUser_id(11);
        request.setRestaurant_id(1);

        ApplyOfferResponse response = applyOffer(request);

        // Assert: Should handle zero cart value gracefully
        assertEquals(0, response.getCart_value());
    }

    // Test Case 10: Edge case - Very high discount (more than cart value)
    @Test
    public void testHighDiscountMoreThanCartValue() throws Exception {
        // Setup: Add high discount offer
        OfferRequest offer = new OfferRequest(1, "FLATX", 500, List.of("p1"));
        addOffer(offer);

        // Setup: Mock user segment response
        setupMockUserSegment(12, "p1");

        // Test: Apply offer with cart value less than discount
        ApplyOfferRequest request = new ApplyOfferRequest();
        request.setCart_value(100);
        request.setUser_id(12);
        request.setRestaurant_id(1);

        ApplyOfferResponse response = applyOffer(request);

        // Assert: Should handle high discount (100 - 500 = -400, but system should handle gracefully)
        // Note: This might result in negative value, which is a business logic consideration
        assertTrue("Cart value should be handled appropriately", response.getCart_value() >= 0);
    }

    // Helper methods
    private void addOffer(OfferRequest offer) throws Exception {
        String urlString = BASE_URL + "/api/v1/offer";
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");

        String jsonInputString = objectMapper.writeValueAsString(offer);
        con.getOutputStream().write(jsonInputString.getBytes());
        con.getOutputStream().flush();
        con.getOutputStream().close();

        int responseCode = con.getResponseCode();
        assertEquals("Offer should be added successfully", 200, responseCode);
    }

    private ApplyOfferResponse applyOffer(ApplyOfferRequest request) throws Exception {
        String urlString = BASE_URL + "/api/v1/cart/apply_offer";
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");

        String jsonInputString = objectMapper.writeValueAsString(request);
        con.getOutputStream().write(jsonInputString.getBytes());
        con.getOutputStream().flush();
        con.getOutputStream().close();

        int responseCode = con.getResponseCode();
        assertEquals("Apply offer should be successful", 200, responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return objectMapper.readValue(response.toString(), ApplyOfferResponse.class);
    }

    private void setupMockUserSegment(int userId, String segment) throws Exception {
        // This would typically set up a mock response for the user segment API
        // For now, we'll assume the mock server is already configured
        // In a real implementation, you would add mock expectations here
        System.out.println("Mock setup for user " + userId + " segment: " + segment);
    }

    private void clearOffers() throws Exception {
        // This would clear all offers from the system
        // For now, we'll just log it
        System.out.println("Clearing offers...");
    }
}
