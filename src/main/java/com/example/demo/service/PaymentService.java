package com.example.demo.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.stripe.param.ChargeCreateParams;

@Service
public class PaymentService {

    @Value("sk_test_51O6YMFDe6Cwf3TWUBu3VBJTFoeHmu6OlZoYXvjCT8ftjXu4zJ1vykiONIaBqN3ivQr93wAj5mMq218cCTLULw4RK00CPggqcsq")
    private String stripeSecretKey;

    public void processPayment(String token,float amount,String description) {
        Stripe.apiKey = stripeSecretKey;

        try {
            ChargeCreateParams params = ChargeCreateParams.builder()
                    .setAmount( (long)(amount*1000))
                    .setCurrency("USD")
                    .setDescription(description)
//                    .setCustomer("Thilanka ")
                    .setSource(token) // Make sure 'token' contains the valid payment token
                    .build();

            Charge charge = Charge.create(params);

            // Handle successful payment (e.g., save to the database or send confirmation email).
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }
}