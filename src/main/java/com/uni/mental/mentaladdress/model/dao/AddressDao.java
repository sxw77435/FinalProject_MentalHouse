package com.uni.mental.mentaladdress.model.dao;

import com.uni.mental.mentaladdress.model.dto.AddressDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressDao {
    List<AddressDto> getAllFacilities();
}
