package com.uni.mental.mentalInfo.model.service;

import com.uni.mental.mentalInfo.model.dao.MentalDao;
import com.uni.mental.mentalInfo.model.dto.MentalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class MentalService {

    private final MentalDao mentalDao;

    @Autowired
    public MentalService(MentalDao mentalDao){
        this.mentalDao = mentalDao;
    }

    public List<MentalDto> findAllMental() {return mentalDao.findAllMental();}
}
