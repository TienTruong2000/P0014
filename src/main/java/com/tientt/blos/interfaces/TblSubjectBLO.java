package com.tientt.blos.interfaces;

import com.tientt.blos.implementations.TblSubjectBLOImpl;
import com.tientt.entities.TblSubject;

import java.util.List;

public interface TblSubjectBLO {
    static TblSubjectBLO newInstance(){
        return new TblSubjectBLOImpl();
    }

    List<TblSubject> findAllSubject();


}
