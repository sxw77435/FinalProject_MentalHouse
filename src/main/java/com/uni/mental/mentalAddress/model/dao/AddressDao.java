package com.uni.mental.mentalAddress.model.dao;

import com.uni.mental.mentalAddress.model.dto.AddressDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressDao {
    List<AddressDto> getAllFacilities();
}
