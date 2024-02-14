package com.uni.mental.mentalAddress.controller;

import com.uni.mental.mentalAddress.model.dao.AddressDao;
import com.uni.mental.mentalAddress.model.dto.AddressDto;
import com.uni.mental.mentalAddress.model.service.AddressService;
import com.uni.mental.mentalAddress.model.service.NaverApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;
    private final NaverApiService naverApiService;
    private final AddressDao addressDao;

    public AddressController(NaverApiService naverApiService, AddressDao addressDao, AddressService addressService) {
        this.naverApiService = naverApiService;
        this.addressDao = addressDao;
        this.addressService = addressService;
    }

    @GetMapping("/nearbyfacilities")
    public ModelAndView getAllFacilities(ModelAndView mv) {
        List<AddressDto> addressList = addressService.getAllFacilities();


        addressList.forEach(addressDto ->{});

        mv.addObject("addressList", addressList);

        mv.setViewName("address/nearbyfacilities");

        return mv;

    }

    @PostMapping("/nearbyfacilities")
    public ResponseEntity<List<AddressDto>> findNearbyFacilities(@RequestParam("searchKeyword") String searchKeyword, @RequestParam("searchGugun") String searchGugun) {
        List<AddressDto> nearbyFacilities = naverApiService.findNearbyFacilities(searchKeyword, searchGugun);
        return ResponseEntity.ok(nearbyFacilities);
    }


}
