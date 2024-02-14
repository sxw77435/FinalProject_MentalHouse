package com.uni.mental.mentalInfo.model.service;

import com.uni.mental.mentalInfo.model.dao.MentalDao;
import com.uni.mental.mentalInfo.model.dto.AttachDto;
import com.uni.mental.mentalInfo.model.dto.MentalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public MentalDto findMentalByCode(int mentalinfono) {return mentalDao.findMentalByCode(mentalinfono);}

    public int updateAttach(AttachDto attachDto) {
        return mentalDao.updateAttach(attachDto);
    }

    public int updateMental(MentalDto mentalDto) {
        return mentalDao.updateMental(mentalDto);
    }

    public ResponseEntity<String> deleteMental(int mentalinfono) {
        mentalDao.deleteMental(mentalinfono);
        return ResponseEntity.ok("Mental deleted successfully");
    }

    public ResponseEntity<String> deleteAttach(int mentalinfono) {
        mentalDao.deleteAttach(mentalinfono);
        return ResponseEntity.ok("Mental attach deleted successfully");
    }

    public List<MentalDto> searchMentalByKeyword(String keyword) {
        return mentalDao.searchMentalByKeyword(keyword);
    }

}
