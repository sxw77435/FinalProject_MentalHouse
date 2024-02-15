package com.uni.mental.mentalAddress.model.service;

import com.uni.mental.mentalAddress.model.dao.AddressDao;
import com.uni.mental.mentalAddress.model.dto.AddressDto;
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

    private static final double DEFAULT_RADIUS = 5.0; // 修改默认半径为5公里

    public List<AddressDto> findNearbyFacilities(String searchKeyword, String searchGugun) {
        // 使用 Naver Maps API 的地理编码服务获取选定地区的经度和纬度信息
        String address = searchKeyword + " " + searchGugun;
        String coordinatesString = getCoordinates(address);
        System.out.println(address);

        // 如果未能获取到地理坐标，返回空列表
        if (coordinatesString == null || coordinatesString.isEmpty()) {
            return new ArrayList<>();
        }

        // 解析经纬度字符串为double数组
        double[] coordinates = parseCoordinates(coordinatesString);

        // 如果解析失败，返回空列表
        if (coordinates == null || coordinates.length != 2) {
            return new ArrayList<>();
        }

        double facilityLatitude = coordinates[0];
        double facilityLongitude = coordinates[1];

        // 获取数据库中所有地址信息
        List<AddressDto> allAddresses = addressDao.getAllFacilities();

        // 计算每个地址与选择地址之间的距离，过滤出半径内的医院
        List<AddressDto> nearbyHospitals = new ArrayList<>();
        for (AddressDto facilityAddress : allAddresses) {
            double distance = calculateDistance(facilityLatitude, facilityLongitude, facilityAddress);
            if (distance <= DEFAULT_RADIUS) {
                nearbyHospitals.add(facilityAddress);
            }
        }

        // 打印附近医院
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
        final double R = 6371.0; // 地球的平均半径（单位：公里）

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








