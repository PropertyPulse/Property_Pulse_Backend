package com.example.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api/v1/gc")
public class GeocodeController {
    private static final String apiKey = "06827d1729msh9189330a7e5769bp14f83ajsn71658498cd7f";

    @RequestMapping(path = "/geocode", method = RequestMethod.GET )
    public static GeocodeLocation GeocodeResult(@RequestParam String address) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String encodedAddress = URLEncoder.encode(address, "UTF-8");
        Request request = new Request.Builder()
                .url("https://google-maps-geocoding.p.rapidapi.com/geocode/json?language=en&address=" + encodedAddress)
                .get()
                .addHeader("x-rapidapi-host", "google-maps-geocoding.p.rapidapi.com")
                .addHeader("x-rapidapi-key",apiKey)
                .build();
        ResponseBody responseBody = client.newCall(request).execute().body();
        System.out.println(responseBody.string());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(responseBody.string(), GeocodeLocation.class);
    }

}