package com.uni.mental.regionComunity.model.service;

import com.uni.mental.regionComunity.model.dao.RecomDao;
import com.uni.mental.regionComunity.model.dto.RecomDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class RecomService {

    private final RecomDao recomDao;

    @Autowired
    public RecomService(RecomDao recomDao){
        this.recomDao = recomDao;
    }
    public List<RecomDto> findAllRecom() {return recomDao.findAllRecom();}

    public RecomDto findRecomByCode(int recomno) {return recomDao.findRecomByCode(recomno);}

    public int updateRecomViews(int recomno, int recomviews) {
        return recomDao.updateRecomViews(recomno, recomviews);
    }

    public ResponseEntity<String> deleteRecom(int recomno) {
        recomDao.deleteRecom(recomno);
        return ResponseEntity.ok("Population deleted successfully");
    }

    public List<RecomDto> findRecomByCateno(int cateno) {return recomDao.findRecomByCateno(cateno);}
}
