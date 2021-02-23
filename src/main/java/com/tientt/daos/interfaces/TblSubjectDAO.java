package com.tientt.daos.interfaces;

import com.tientt.daos.implementations.TblSubjectDAOImpl;
import com.tientt.entities.TblSubject;

import java.util.List;

public interface TblSubjectDAO {
    static TblSubjectDAO newInstance(){
        return new TblSubjectDAOImpl();
    }

    TblSubject findTblSubjectByID(String subjectID);

    List<TblSubject> findAllTblSubject();
}
