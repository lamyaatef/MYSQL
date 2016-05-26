/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.xyzpos.dao;

import com.mobinil.mcss.xyzpos.model.POS;
import java.util.List;

/**
 *
 * @author Gado
 */
public interface XYZDao {
    public List <POS> getALLPOSs ();
    public void deletePOS(POS dcmCode);
    public boolean addPOS (POS dcmCode);
}
