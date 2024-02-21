package com.uni.mental.mentaladdress.model.service;

import com.uni.mental.mentaladdress.model.dao.AddressDao;
import com.uni.mental.mentaladdress.model.dto.AddressDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class NaverApiService {


    @Value("${naver.api.clientId}")
    private String naverApiClientId;

    @Value("${naver.api.clientSecret}")
    private String naverApiClientSecret;

    private final RestTemplate restTemplate;

    private final AddressDao addressDao;

    public NaverApiService(RestTemplate restTemplate, AddressDao addressDao) {
        this.restTemplate = restTemplate;
        this.addressDao = addressDao;
    }

    private static final double DEFAULT_RADIUS = 5.0; // 반경 설정

    public List<AddressDto> findNearbyFacilities(String searchKeyword, String searchGugun) {
        // Naver Maps API 사용하고 사용자 선택한 지역의 좌표얻기
        String address = searchKeyword + " " + searchGugun;
        String coordinatesString = getCoordinates(address);
        System.out.println(address);

        if (coordinatesString == null || coordinatesString.isEmpty()) {
            return new ArrayList<>();
        }

        double[] coordinates = parseCoordinates(coordinatesString);


        if (coordinates == null || coordinates.length != 2) {
            return new ArrayList<>();
        }

        double facilityLatitude = coordinates[0];
        double facilityLongitude = coordinates[1];

        // 데이터베이스 에 있는 모든 병원 얻기
        List<AddressDto> allAddresses = addressDao.getAllFacilities();

        // 데이터 베이스에 있는 병원와 사용자 선택한 지역이와 거리를 계삭 하고 ,반경내에 있는 병원 화면에 나오기
        List<AddressDto> nearbyHospitals = new ArrayList<>();
        for (AddressDto facilityAddress : allAddresses) {
            double distance = calculateDistance(facilityLatitude, facilityLongitude, facilityAddress);
            if (distance <= DEFAULT_RADIUS) {
                nearbyHospitals.add(facilityAddress);
            }
        }

        System.out.println("Nearby Hospitals:");
        for (AddressDto hospital : nearbyHospitals) {
            System.out.println(hospital.getFacilityname() + " - " + hospital.getFacilityaddress());
        }

        return nearbyHospitals;
    }

    private double[] parseCoordinates(String coordinatesString) {
        String[] parts = coordinatesString.split(",");
        if (parts.length != 2) {
            return null;
        }
        try {
            double latitude = Double.parseDouble(parts[0]);
            double longitude = Double.parseDouble(parts[1]);
            return new double[]{latitude, longitude};
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    private double calculateDistance(double facilityLatitude, double facilityLongitude, AddressDto address) {
        final double R = 6371.0;

        double lat1 = Math.toRadians(facilityLatitude);
        double lon1 = Math.toRadians(facilityLongitude);
        double lat2 = Math.toRadians(address.getFacilitylatitude());
        double lon2 = Math.toRadians(address.getFacilitylongitude());

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;

        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = R * c; // 距离（单位：公里）

        return distance;
    }


    private String getCoordinates(String address) {
        HttpHeaders headers = createHeaders();
        String apiUrl = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + address;

        System.out.println(apiUrl);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        JSONObject jsonResponse = new JSONObject(response.getBody());
        System.out.println("API Response: " + response.getBody());

        JSONArray addresses = jsonResponse.getJSONArray("addresses");
        JSONObject firstAddress = addresses.getJSONObject(0);
        double latitude = firstAddress.getDouble("y");
        double longitude = firstAddress.getDouble("x");
        return latitude + "," + longitude;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", naverApiClientId);
        headers.set("X-NCP-APIGW-API-KEY", naverApiClientSecret);
        return headers;
    }
}








