package com.uni.mental.mentalcomunity.model.service;

import com.uni.mental.mentalcomunity.model.dao.MenComCmtDAO;
import com.uni.mental.mentalcomunity.model.dto.MenComCmtDTO;
import com.uni.mental.mentalcomunity.model.dto.MenComDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenComCmtService {

    private final MenComCmtDAO menComCmtDAO;

    public MenComCmtService(MenComCmtDAO menComCmtDAO) {
        this.menComCmtDAO = menComCmtDAO;
    }

    public int deleteComment(int no) {

        return menComCmtDAO.deleteComment(no);
    }

    public List<MenComDTO> mencmtList() {

        return menComCmtDAO.mencmtList();

    }

    public int insertComment(MenComCmtDTO result)  {


        menComCmtDAO.updateReplyCnt(result.getMenNo(),1);

        return menComCmtDAO.insertComment(result);
    }

    public List<MenComCmtDTO> getList(MenComCmtDTO result) throws Exception {

        return menComCmtDAO.getList(result);
    }


}
