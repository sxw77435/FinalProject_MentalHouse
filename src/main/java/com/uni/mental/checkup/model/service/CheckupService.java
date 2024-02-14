package com.uni.mental.checkup.model.service;

import com.uni.mental.mapper.CheckupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckupService {

    @Autowired
    private CheckupMapper checkupMapper; // CheckupMapper 주입

    public List<String> getAllMentalNames() {
        return checkupMapper.selectAllMentalNames(); // Mapper 메서드 호출
    }

    public List<String> getQuestionsByMentalName(String mentalName) {
        return checkupMapper.selectQuestionsByMentalName(mentalName);
    }

    public String getMentalNameByIdx(int idx) {
        return checkupMapper.selectMentalNameByIdx(idx); // Mapper 메소드 호출
    }



}
