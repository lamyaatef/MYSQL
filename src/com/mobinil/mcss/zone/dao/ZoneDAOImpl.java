/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.zone.dao;

import com.mobinil.mcss.legacy_models.DcmRegion;
import com.mobinil.mcss.legacy_models.GenDcm;
import com.mobinil.mcss.zone.model.DcmRegionModel;
import com.mobinil.mcss.zone.model.Governorate;
import com.mobinil.mcss.zone.model.Zone;
import com.mobinil.mcss.zone.model.ZoneGovernorate;
import com.mobinil.sds.core.utilities.DBUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gado
 */
@Repository("ZoneDAO")
public class ZoneDAOImpl implements ZoneDAO {

    final int ADD_DCM = 1;
    final int EDIT_DCM = 2;
    final int DEL_DCM = 3;
    @Autowired
    private SessionFactory sessionfactory;
    private Session dbsession = null;

    public List<Zone> getAllZones() {
        Session session = sessionfactory.openSession();
        Criteria cr = session.createCriteria(Zone.class);
        List ls = cr.list();
        session.close();
        return ls;

    }

    public boolean addZone(Zone zone) {
        Session session = sessionfactory.openSession();
        Transaction trx = session.beginTransaction();
        try {
            session.save(zone);
            trx.commit();
        } catch (Exception ex) {
            return false;

        }
        session.close();
        return true;
    }

    public void editZone(String name, Zone zone) {
        Session session = sessionfactory.openSession();
        Transaction trx = session.beginTransaction();
        zone.setZoneName(name);
        session.update(zone);
        trx.commit();
        session.close();
    }

    public String getZoneName(Long zoneCode) {
        Session session = sessionfactory.openSession();
        Criteria cr = session.createCriteria(Zone.class);
        cr.add(Restrictions.eq("zoneCode", zoneCode));
        Zone zone = (Zone) cr.list().get(0);
        String zoneName = zone.getZoneName();
        return zoneName;

    }

    public boolean assginGovernrateToZone(String governoratecode, Zone zone) {
        try {
            Session session = sessionfactory.openSession();
            Transaction trx = session.beginTransaction();
            int goverId = checkgovernorateCode(governoratecode);
            if (goverId != -1) {
                if (checkGovernorateAssignedtoZone(goverId) == -1) {
                    ZoneGovernorate zonegover = new ZoneGovernorate();
                    zonegover.setZone(zone);
                    zonegover.setGovernrate(goverId);
                    session.save(zonegover);
                    trx.commit();
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ZoneDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private int checkGovernorateAssignedtoZone(int governorateId) throws SQLException {
        Session session = sessionfactory.openSession();
        Connection con = session.connection();

        int regionId = DBUtil.executeQuerySingleValueInt("select GOVERNRATE_ID from COMMSSION_ZONE_GOVERNORATE where GOVERNRATE_ID = " + governorateId, "GOVERNRATE_ID", con);
        con.close();
        session.close();
        return regionId;

    }

    private int checkgovernorateCode(String governoratecode) throws SQLException {
        Session session = sessionfactory.openSession();
        Connection con = session.connection();

        int regionId = DBUtil.executeQuerySingleValueInt("select region_id from dcm_region where region_code = '" + governoratecode + "' and region_level_type_id = 2", "region_id", con);
        con.close();
        session.close();
        return regionId;
    }

    public List<Governorate> getZoneGovernorate(Long zoneCode) {
        Zone zone = new Zone();
        zone.setZoneCode(zoneCode);
        Session session = sessionfactory.openSession();
        Criteria cr = session.createCriteria(ZoneGovernorate.class);
        cr.add(Restrictions.eq("zone", zone));
        List<ZoneGovernorate> govers = cr.list();
        session.close();
        List<Governorate> governames = new ArrayList<Governorate>();
        for (ZoneGovernorate zonegover : govers) {
            try {
                governames.add(getgoverdetails(zonegover.getGovernrate()));
            } catch (SQLException ex) {
                Logger.getLogger(ZoneDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return governames;

    }

    private Governorate getgoverdetails(long goverId) throws SQLException {
        Session session = sessionfactory.openSession();
        Connection con = session.connection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select region_name , region_code from dcm_region where region_id =" + goverId + " and region_level_type_id = 2");
        String name = "";
        String code = "";
        while (rs.next()) {

            name = rs.getString("region_name");
            code = rs.getString("region_code");

        }
        con.close();
        session.close();
        Governorate gover = new Governorate();
        gover.setGovernorateCode(code);
        gover.setGovernorateName(name);
        gover.setGovernorateId(goverId);
        return gover;
    }

    public void unassignGovernorate(Long zoneCode, long goverId) {
        Session session = sessionfactory.openSession();
        Zone zone = new Zone();
        zone.setZoneCode(zoneCode);
        ZoneGovernorate zonegover = new ZoneGovernorate();
        Criteria cr1 = session.createCriteria(ZoneGovernorate.class);
        Criterion c1 = Restrictions.eq("zone.zoneCode", zoneCode);
        Criterion c2 = Restrictions.eq("governrate", goverId);
        cr1.add(Restrictions.and(c1, c2));
        List<ZoneGovernorate> gover = cr1.list();
        zonegover = gover.get(0);
        Transaction trx = session.beginTransaction();
        session.delete(zonegover);
        trx.commit();
        session.close();
    }

    public boolean deleteZone(Long zoneCode) {
        Session session = sessionfactory.openSession();

        Criteria cr = session.createCriteria(ZoneGovernorate.class);
        cr.add(Restrictions.eq("zone.zoneCode", zoneCode));
        Zone zone = new Zone();
        zone.setZoneCode(zoneCode);
        List<ZoneGovernorate> ls = cr.list();
        if (ls.isEmpty()) {
            Session s = sessionfactory.openSession();
            Transaction trx = s.beginTransaction();
            s.delete(zone);
            trx.commit();
            s.close();
            return true;
        } else {
            return false;
        }

    }

    private Boolean doAddEditDelete(DcmRegionModel dcmRegionModel, int type) {

        Transaction trx = null;
        boolean retStatus = false;
        trx = dbsession.beginTransaction();

        try {

            if (type == ADD_DCM) {
                dbsession.save(dcmRegionModel);
            } else if (type == EDIT_DCM) {
                dbsession.update(dcmRegionModel);
            } else if (type == DEL_DCM) {
                dbsession.createQuery("delete from DcmRegionModel where dcm_id=" + dcmRegionModel.getDcmId()).executeUpdate();
            }
            trx.commit();
            retStatus = true;
        } catch (HibernateException e) {
            trx.rollback();
            e.printStackTrace();
        } finally {
            return retStatus;
        }
    }

    @Override
    public Boolean addNewDCMRegion(DcmRegionModel dcmRegionModel) {

        return doAddEditDelete(dcmRegionModel, ADD_DCM);
    }

    @Override
    public Boolean deleteDCMRegion(DcmRegionModel dcmRegionModel) {

        return doAddEditDelete(dcmRegionModel, DEL_DCM);
    }

    @Override
    public Boolean updateDCMRegion(DcmRegionModel dcmRegionModel) {
        return doAddEditDelete(dcmRegionModel, EDIT_DCM);
    }

    @Override
    public List<DcmRegionModel> getDCMRegions() {

        Criteria cr = dbsession.createCriteria(DcmRegionModel.class);
        List<DcmRegionModel> ls = cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

        return ls;

    }

    @Override
    public void openSession() {
        dbsession = sessionfactory.openSession();
    }

    @Override
    public void closeSession() {
        dbsession.close();
    }

    @Override
    public List<GenDcm> getAvailableDCMS() {
        return getDCMS(true);
    }
    
    private List<GenDcm> getDCMS(boolean modeType) {

        Criteria cr = dbsession.createCriteria(GenDcm.class);
        cr.add(Restrictions.eq("dcmLevelId", BigInteger.valueOf(1L)));
        cr.add(Restrictions.eq("dcmPaymentLevelId", BigInteger.valueOf(1L)));
        if (modeType) {

            List<DcmRegionModel> allRegions = getDCMRegions();
            if (!allRegions.isEmpty()) {
                Object[] values = new Object[allRegions.size()];
                for (int i = 0; i < allRegions.size(); i++) {
                    values [i]  = BigDecimal.valueOf(allRegions.get(i).getDcmId());
                }
                cr.add(Restrictions.not(Restrictions.in("dcmId", values)));
            }
        }
        return cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

    }

    @Override
    public List<DcmRegion> getRegions() {
        Criteria cr = dbsession.createCriteria(DcmRegion.class);
        cr.add(Restrictions.eq("regionLevelTypeId", BigInteger.valueOf(1L)));
        List<DcmRegion> ls = cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return ls;
    }

    @Override
    public List<DcmRegionModel> getDCMRegions(String dcm_id) {
        Criteria cr = dbsession.createCriteria(DcmRegionModel.class);
        cr.add(Restrictions.eq("dcmId", Long.parseLong(dcm_id)));
        List<DcmRegionModel> ls = cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return ls;
    }

    @Override
    public List<GenDcm> getAllDCMS() {
        return getDCMS(false);
    }
    
    
    
}
