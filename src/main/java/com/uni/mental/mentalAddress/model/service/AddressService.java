package com.uni.mental.mentalAddress.model.service;


import com.uni.mental.mentalAddress.model.dao.AddressDao;
import com.uni.mental.mentalAddress.model.dto.AddressDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class AddressService {

    private final AddressDao addressDao;

    @Autowired
    public AddressService(AddressDao addressDao){
        this.addressDao = addressDao;
    }


    public List<AddressDto> getAllFacilities() {
        return addressDao.getAllFacilities();
    }
}
