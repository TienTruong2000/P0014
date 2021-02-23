package com.tientt.blos.implementations;

import com.tientt.blos.interfaces.TblSubjectBLO;
import com.tientt.daos.interfaces.TblSubjectDAO;
import com.tientt.entities.TblSubject;


import java.util.List;

public class TblSubjectBLOImpl implements TblSubjectBLO {
    private final TblSubjectDAO subjectDAO = TblSubjectDAO.newInstance();

    @Override
    public List<TblSubject> findAllSubject() {
       return subjectDAO.findAllTblSubject();
    }
}
