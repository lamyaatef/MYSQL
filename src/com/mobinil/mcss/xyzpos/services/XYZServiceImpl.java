/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.xyzpos.services;

import com.mobinil.mcss.xyzpos.dao.XYZDao;
import com.mobinil.mcss.xyzpos.model.POS;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gado
 */
@Service("XYZService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class XYZServiceImpl implements XYZService{
    
    @Autowired
    private XYZDao xyzdao;

    public List<POS> getALLPOSs() {
       return xyzdao.getALLPOSs();
    }

    public void deletePOS(POS dcmCode) {
        xyzdao.deletePOS(dcmCode);
    }

    public boolean addPOS(POS dcmCode) {
        return xyzdao.addPOS(dcmCode);
    }

    
}
