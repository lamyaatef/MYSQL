/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.xyzpos.dao;

import com.mobinil.mcss.xyzpos.model.DeletePOS;
import com.mobinil.mcss.xyzpos.model.POS;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gado
 */
@Repository("XYZDao")
public class XYZDaoImpl implements XYZDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<POS> getALLPOSs() {
        Session session = sessionFactory.openSession();
        Criteria cr = session.createCriteria(POS.class);
        List<POS> posCode = cr.list();

        List<POS> results = new ArrayList<POS>();
        Connection con = session.connection();
        for (POS code : posCode) {
            try {
                POS result = getPOSFromGenDcmByCode(code.getPosCode(), con);
                result.setUSER_ID(code.getUSER_ID());
                result.setEnrtyDate(code.getEnrtyDate());
                results.add(result);
            } catch (SQLException ex) {
                Logger.getLogger(XYZDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }


        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(XYZDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        session.close();



        return results;

    }

    public void deletePOS(POS pos) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(pos);
        tx.commit();
        session.close();
        DeletePOS deletepos = new DeletePOS();
        deletepos.setPosCode(pos.getPosCode());
        deletepos.setUSER_ID(pos.getUSER_ID());
        addDeletedPOS(deletepos);
    }

    public void addDeletedPOS(DeletePOS pos) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Date currentDate = new Date();
        System.out.print(currentDate.toString());
        pos.setEnrtyDate(currentDate);
        session.save(pos);
        tx.commit();


    }

    public boolean addPOS(POS pos) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        POS inserted = new POS();
        try {
            Connection con = session.connection();
            inserted = getPOSFromGenDcmByCode(pos.getPosCode(), con);
            con.close();
        } catch (SQLException ex) {
            session.close();
            Logger.getLogger(XYZDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (inserted.getPosName() != null) {
            Date currentDate = new Date();
            System.out.print(currentDate.toString());
            inserted.setEnrtyDate(currentDate);
            inserted.setUSER_ID(pos.getUSER_ID());
            session.save(inserted);
            tx.commit();
            return true;
        } else {
            session.close();

            return false;
        }

    }

    private POS getPOSFromGenDcmByCode(String posCode, Connection con) throws SQLException {

        Statement stmt = con.createStatement();
        String query = "SELECT DCM_NAME  FROM GEN_DCM WHERE "
                + "DCM_CODE = '" + posCode + "'";
        ResultSet rs = stmt.executeQuery(query);
        POS resultPOS = new POS();

        while (rs.next()) {
            resultPOS.setPosName(rs.getString("DCM_NAME"));

        }

        resultPOS.setPosCode(posCode);
        stmt.close();
        return resultPOS;
    }
}
