package com.mobinil.sds.core.facade.handlers;

import java.util.Vector;
import java.util.HashMap;
import java.sql.*;

import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.dcm.DCMInterfaceKey;
import com.mobinil.sds.web.interfaces.sa.*;

import com.mobinil.sds.core.utilities.*;

import com.mobinil.sds.core.system.sa.importdata.dao.*;
import com.mobinil.sds.core.system.dcm.pos.model.*;
import com.mobinil.sds.core.system.dcm.pos.dao.*;
import com.mobinil.sds.core.system.dcm.user.dao.*;


import com.mobinil.sds.core.system.dcm.genericModel.*;
import com.mobinil.sds.core.system.dcm.genericModel.DAO.*;
import com.mobinil.sds.core.system.dcm.city.dao.*;
import com.mobinil.sds.core.system.dcm.region.model.*;
import com.mobinil.sds.core.system.dcm.region.dao.*;
import com.mobinil.sds.core.system.dcm.region.dto.*;
import com.mobinil.sds.core.system.dcm.group.dao.*;
import com.mobinil.sds.core.system.dcm.chain.dao.*;
import com.mobinil.sds.core.system.dcm.chain.model.*;
import com.mobinil.sds.web.interfaces.scm.SCMInterfaceKey;
import java.util.Map;

public class DCMIIHandler {

    static final int DCM_ADD_NEW_REGIONAL_MANAGER = 1;
    static final int DCM_ADD_NEW_SALES_AGENT = 2;
    static final int DCM_ADD_NEW_MANAGER = 3;
    static final int DCM_REGIONAL_MANAGEMENT_TREE = 4;
    static final int EDIT_REGION = 5;
    static final int INSERT_NEW_REGION = 6;
    static final int MOVE_REGION_FROM_TO = 8;
    static final int UPDATE_REGION_NAME = 9;
    static final int CHAIN_MANAGEMENT = 10;
    static final int SEARCH_GEN_DCM = 11;
    static final int NEXT_GEN_DCM = 12;
    static final int PREVIOUS_GEN_DCM = 13;
    static final int EDIT_GEN_DCM = 14;
    static final int EXPORT_GEN_DCM_TO_EXCEL = 15;
    static final int CREATE_NEW_GEN_DCM = 16;
    static final int UPDATE_GEN_DCM = 17;
    static final int SAVE_GEN_DCM = 18;
    static final int DCM_SUPER_ADMIN_POS_LIST = 19;
    static final int USER_SAVE_NEW_POS = 20;
    static final int DCM_POS_SAVE_DETAIL = 21;
    static final int DCM_UPLOAD_POS_FOR_GROUP = 22;
    static final int DCM_UPLOAD_POS_FOR_GROUP_PROCESS = 23;
    static final int search_region = 24;
    static final int add_childs_to_region = 25;
    static final int delete_region = 26;
    static final int action_view_region_parent = 27;
    static final int action_view_region_childs = 28;
    static final int save_childs_to_region = 29;
    static final int action_view_region_details = 30;
    static final int action_view_edit_parent = 31;
    static final int action_update_geographical = 32;
    static final int upload_excel_address = 33;
    static final int generate_excel_template = 34;
    static final int action_upload_generated_file = 35;

    public static HashMap handle(String action, HashMap paramHashMap, java.sql.Connection con) throws Exception {
        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        Vector chainLevelList = null;
        Vector chainPaymentLevelList = null;
        Vector chainChannelList = null;
        Vector chainCityList = null;
        Vector chainDistrictList = null;
        Vector chainStatusList = null;
        String chainCode = null;
        String chainName = null;
        String chainPhone = null;
        String chainMail = null;
        String chainRank = null;
        String chainStkNumber = null;
        String chainAddress = null;
        String chainStatus = null;
        String chainLevel = null;
        String chainPaymentLevel = null;
        String chainChannel = null;
        String chainCity = null;
        String chainDistrict = null;
        int actionType = 0;
        HashMap dataHashMap = new HashMap();


        try {
            //con = Utility.getConnection();
            if (action.equals(DCMInterfaceKey.ACTION_DCM_ADD_NEW_REGIONAL_MANAGER)) {
                actionType = DCM_ADD_NEW_REGIONAL_MANAGER;
            } else if (action.equals(DCMInterfaceKey.ACTION_DCM_ADD_NEW_SALES_AGENT)) {
                actionType = DCM_ADD_NEW_SALES_AGENT;
            } else if (action.equals(DCMInterfaceKey.ACTION_DCM_ADD_NEW_MANAGER)) {
                actionType = DCM_ADD_NEW_MANAGER;
            } else if (action.equals(DCMInterfaceKey.ACTION_DCM_REGIONAL_MANAGEMENT_TREE)) {
                actionType = DCM_REGIONAL_MANAGEMENT_TREE;
            } else if (action.equals(DCMInterfaceKey.ACTION_EDIT_REGION)) {
                actionType = EDIT_REGION;
            } else if (action.equals(DCMInterfaceKey.ACTION_INSERT_NEW_REGION)) {
                actionType = INSERT_NEW_REGION;
            } else if (action.equals(DCMInterfaceKey.ACTION_MOVE_REGION_FROM_TO)) {
                actionType = MOVE_REGION_FROM_TO;
            } else if (action.equals(DCMInterfaceKey.ACTION_UPDATE_REGION_NAME)) {
                actionType = UPDATE_REGION_NAME;
            } else if (action.equals(DCMInterfaceKey.ACTION_CHAIN_MANAGEMENT)) {
                actionType = CHAIN_MANAGEMENT;
            } else if (action.equals(DCMInterfaceKey.ACTION_SEARCH_GEN_DCM)) {
                actionType = SEARCH_GEN_DCM;
            } else if (action.equals(DCMInterfaceKey.ACTION_NEXT_GEN_DCM)) {
                actionType = NEXT_GEN_DCM;
            } else if (action.equals(DCMInterfaceKey.ACTION_PREVIOUS_GEN_DCM)) {
                actionType = PREVIOUS_GEN_DCM;
            } else if (action.equals(DCMInterfaceKey.ACTION_EDIT_GEN_DCM)) {
                actionType = EDIT_GEN_DCM;
            } else if (action.equals(DCMInterfaceKey.ACTION_EXPORT_GEN_DCM_TO_EXCEL)) {
                actionType = EXPORT_GEN_DCM_TO_EXCEL;
            } else if (action.equals(DCMInterfaceKey.ACTION_CREATE_NEW_GEN_DCM)) {
                actionType = CREATE_NEW_GEN_DCM;
            } else if (action.equals(DCMInterfaceKey.ACTION_UPDATE_GEN_DCM)) {
                actionType = UPDATE_GEN_DCM;
            } else if (action.equals(DCMInterfaceKey.ACTION_SAVE_GEN_DCM)) {
                actionType = SAVE_GEN_DCM;
            } else if (action.equals(DCMInterfaceKey.ACTION_DCM_SUPER_ADMIN_POS_LIST)) {
                actionType = DCM_SUPER_ADMIN_POS_LIST;
            } else if (action.equals(DCMInterfaceKey.ACTION_USER_SAVE_NEW_POS)) {
                actionType = USER_SAVE_NEW_POS;
            } else if (action.equals(DCMInterfaceKey.ACTION_DCM_POS_SAVE_DETAIL)) {
                actionType = DCM_POS_SAVE_DETAIL;
            } else if (action.equals(DCMInterfaceKey.ACTION_DCM_UPLOAD_POS_FOR_GROUP)) {
                actionType = DCM_UPLOAD_POS_FOR_GROUP;
            } else if (action.equals(DCMInterfaceKey.ACTION_DCM_UPLOAD_POS_FOR_GROUP_PROCESS)) {
                actionType = DCM_UPLOAD_POS_FOR_GROUP_PROCESS;
            } else if (action.equals(DCMInterfaceKey.ACTION_SEARCH_REGION)) {
                actionType = search_region;
            } else if (action.equals(DCMInterfaceKey.ADD_CHILDS_TO_REGION)) {
                actionType = add_childs_to_region;
            } else if (action.equals(DCMInterfaceKey.DELETE_REGION)) {
                actionType = delete_region;
            } else if (action.equals(DCMInterfaceKey.ACTION_VIEW_REGION_PARENT)) {
                actionType = action_view_region_parent;
            } else if (action.equals(DCMInterfaceKey.ACTION_VIEW_REGION_CHILDS)) {
                actionType = action_view_region_childs;
            } else if (action.equals(DCMInterfaceKey.SAVE_CHILDS_TO_REGION)) {
                actionType = save_childs_to_region;
            } else if (action.equals(DCMInterfaceKey.ACTION_VIEW_REGION_DETAILS)) {
                actionType = action_view_region_details;
            } else if (action.equals(DCMInterfaceKey.ACTION_VIEW_EDIT_PARENT)) {
                actionType = action_view_edit_parent;
            } else if (action.equals(DCMInterfaceKey.ACTION_UPDATE_GEOGRAPHICAL)) {
                actionType = action_update_geographical;
            } else if (action.equals(DCMInterfaceKey.ACTION_UPLOAD_EXCEL_ADDRESS)) {
                actionType = upload_excel_address;
            } else if (action.equals(DCMInterfaceKey.ACTION_GENERATE_EXCEL_TEMPLATE)) {
                actionType = generate_excel_template;
            } else if (action.equals(DCMInterfaceKey.ACTION_UPLOAD_GENERATED_FILE)) {
                actionType = action_upload_generated_file;
            }

            switch (actionType) {
                case DCM_POS_SAVE_DETAIL:
                    try {
                        dataHashMap = new HashMap();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        GenericModel statusModel = new GenericModel();
                        Vector statusVector = new Vector();
                        Vector cityVector = new Vector();
                        GenericModelDAO genericModelDAO = new GenericModelDAO();

                        statusModel = genericModelDAO.getColumns(con, "DCM_POS_STATUS_TYPE");
                        statusVector = genericModelDAO.getModels(con, statusModel);

                        cityVector = CityDAO.get_all_cities(con);

                        dataHashMap.put(DCMInterfaceKey.VECTOR_POS_CITY, cityVector);
                        dataHashMap.put(DCMInterfaceKey.VECTOR_POS_STATUS, statusVector);

                        String strFlagSuperAdmin = (String) paramHashMap.get(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);
                        dataHashMap.put(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG, strFlagSuperAdmin);

                        String posDetailId = (String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_POS_ID);
                        String posNextStatus = (String) paramHashMap.get(DCMInterfaceKey.INPUT_POS_STATUS);

                        int intPosDetailId = Integer.parseInt(posDetailId);
                        int intPosNextStatus = Integer.parseInt(posNextStatus);

                        PosDAO.updatePosStatus(con, intPosDetailId, intPosNextStatus, strFlagSuperAdmin);

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case USER_SAVE_NEW_POS: {
                    try {
                        dataHashMap = new HashMap();
                        String posName = (String) paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_NAME);
                        String posCode = (String) paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_CODE);
                        String posEmail = (String) paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_EMAIL);
                        String posAddress = (String) paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_ADDRESS);
                        int posRegion = Integer.parseInt((String) paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_REGION));
                        int posCommercialNumber = Integer.parseInt((String) paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_COMMERCIAL_NUMBER));
                        int posTaxID = Integer.parseInt((String) paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_TAX_ID));
                        int posLegalForm = Integer.parseInt((String) paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_LEGAL_FORM));
                        int posPlace = Integer.parseInt((String) paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_PLACE_TYPE));
                        String posOwnerName = (String) paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME);
                        String posOwnerBirthDate = (String) paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_OWNER_BIRTH_DATE);
                        int posOwnerIDType = Integer.parseInt((String) paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE));
                        int posOwnerIDNumber = Integer.parseInt((String) paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER));
                        String posManagerName = (String) paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME);
                        String posManagerBirthDate = (String) paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_BIRTH_DATE);
                        int posManagerIDType = Integer.parseInt((String) paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE));
                        int posManagerIDNumber = Integer.parseInt((String) paramHashMap.get(DCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER));
                        String userId = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        int UserID = Integer.parseInt(userId);

                        String Phones = (String) paramHashMap.get("manager_phones__R2__C1");
                        Vector POSPhones = new Vector();
                        String POSPhonesCount = (String) paramHashMap.get("phones_count");

                        int count = Integer.parseInt(POSPhonesCount);
                        for (int i = 1; i <= count; i++) {
                            String phone = (String) paramHashMap.get("phones__R" + i + "__C1");
                            POSPhones.add(phone);
                        }
                        String partners = (String) paramHashMap.get("partners__R1__C1");
                        Vector partnerVector = new Vector();
                        String PartnersCount = (String) paramHashMap.get("partners_count");
                        count = Integer.parseInt(PartnersCount);
                        Utility.logger.debug("partnersCount " + count);
                        for (int i = 1; i <= count; i++) {
                            String partner = (String) paramHashMap.get("partners__R" + i + "__C1");
                            partnerVector.add(partner);
                        }
                        Vector ownerPhones = new Vector();
                        String ownerPhonesCount = (String) paramHashMap.get("owner_phones_count");
                        count = Integer.parseInt(ownerPhonesCount);
                        for (int i = 1; i <= count; i++) {
                            String phone = (String) paramHashMap.get("owner_phones__R" + i + "__C1");
                            ownerPhones.add(phone);
                        }
                        Vector managerPhones = new Vector();
                        String managerPhonesCount = (String) paramHashMap.get("manager_phones_count");
                        count = Integer.parseInt(managerPhonesCount);
                        for (int i = 1; i <= count; i++) {
                            String phone = (String) paramHashMap.get("manager_phones__R" + i + "__C1");
                            managerPhones.add(phone);
                        }
                        Date OwnerBirthDate;
                        POSDetailModel mdl = new POSDetailModel();
                        mdl.setPosName(posName);
                        mdl.setPOSCode(posCode);
                        mdl.setPosAddress(posAddress);
                        mdl.setPosEmail(posEmail);
                        mdl.setUserID(UserID);
                        mdl.setPosRegionID(posRegion);
                        mdl.setPosPhones(POSPhones);
                        mdl.setPosManagerPhones(managerPhones);
                        mdl.setPosOwnerPhones(ownerPhones);
                        mdl.setPosCommercialNumber(posCommercialNumber);
                        mdl.setPosTaxID(posTaxID);
                        mdl.setPosLegalFormID(posLegalForm);
                        mdl.setPosPlaceTypeID(posPlace);
                        mdl.setPosOwnerName(posOwnerName);
                        mdl.setPosOwnerBirthDate(posOwnerBirthDate);
                        mdl.setPosOwnerIDTypeID(posOwnerIDType);
                        mdl.setPosOwnerIDNumber(posOwnerIDNumber + "");
                        mdl.setPosManagerName(posManagerName);
                        mdl.setPosManagerBirthDate(posManagerBirthDate);
                        mdl.setPosManagerIDTypeID(posManagerIDType);
                        mdl.setPosManagerIDNumber(posManagerIDNumber + "");
                        mdl.setPosPartners(partnerVector);

                        /*for(int j=0; j<paramHashMap.size(); j++)
                         {
                         String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
                         String keyValue = (String)paramHashMap.get(tempStatusString);
                         Utility.logger.debug("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
                         }*/

                        String strFlagSuperAdmin = (String) paramHashMap.get(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);
                        dataHashMap.put(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG, strFlagSuperAdmin);

                        PosDAO dao = new PosDAO();
                        dao.addNewPOS(con, mdl, strFlagSuperAdmin);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);

                        GenericModel statusModel = new GenericModel();
                        Vector statusVector = new Vector();
                        Vector cityVector = new Vector();
                        GenericModelDAO genericModelDAO = new GenericModelDAO();
                        statusModel = genericModelDAO.getColumns(con, "DCM_POS_STATUS_TYPE");
                        statusVector = genericModelDAO.getModels(con, statusModel);
                        cityVector = CityDAO.get_all_cities(con);
                        dataHashMap.put(DCMInterfaceKey.VECTOR_POS_CITY, cityVector);
                        dataHashMap.put(DCMInterfaceKey.VECTOR_POS_STATUS, statusVector);
                        dataHashMap.put(DCMInterfaceKey.POS_SEARCH_STATUS, "");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }

                }
                break;

                case DCM_ADD_NEW_REGIONAL_MANAGER:
                    try {
                        dataHashMap = new HashMap();
//          String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_LEVEL_TYPE_ID, "1");
                        int dcmUserRegionId = DCMUserDAO.getDcmUserRegionId(con, strUserID);
                        Utility.logger.debug("The Region idddddddddddddddd isssssssssss " + dcmUserRegionId);
                        Vector regions = RegionDAO.getAllRegionsByRegionId(con, dcmUserRegionId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, regions);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }

                    break;
                case DCM_SUPER_ADMIN_POS_LIST:
                    try {
                        dataHashMap = new HashMap();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        GenericModel statusModel = new GenericModel();
                        Vector statusVector = new Vector();
                        Vector cityVector = new Vector();
                        GenericModelDAO genericModelDAO = new GenericModelDAO();

                        statusModel = genericModelDAO.getColumns(con, "DCM_POS_STATUS_TYPE");
                        statusVector = genericModelDAO.getModels(con, statusModel);

                        cityVector = CityDAO.get_all_cities(con);

                        dataHashMap.put(DCMInterfaceKey.VECTOR_POS_CITY, cityVector);
                        dataHashMap.put(DCMInterfaceKey.VECTOR_POS_STATUS, statusVector);

                        String strFlagSuperAdmin = "1";
                        dataHashMap.put(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG, strFlagSuperAdmin);

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case DCM_ADD_NEW_SALES_AGENT:
                    try {
                        dataHashMap = new HashMap();
                        //String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_LEVEL_TYPE_ID, "3");
                        int dcmUserRegionId = DCMUserDAO.getDcmUserRegionId(con, strUserID);
                        Utility.logger.debug("The Region idddddddddddddddd isssssssssss " + dcmUserRegionId);
                        Vector regions = RegionDAO.getAllRegionsByRegionId(con, dcmUserRegionId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, regions);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, DCMInterfaceKey.ACTION_DCM_ADD_NEW_SALES_AGENT);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case DCM_ADD_NEW_MANAGER:
                    try {
                        dataHashMap = new HashMap();
//          String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_LEVEL_TYPE_ID, "2");
                        int dcmUserRegionId = DCMUserDAO.getDcmUserRegionId(con, strUserID);
                        Utility.logger.debug("The Region idddddddddddddddd isssssssssss " + dcmUserRegionId);
                        Vector regions = RegionDAO.getAllRegionsByRegionId(con, dcmUserRegionId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, regions);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case DCM_REGIONAL_MANAGEMENT_TREE:
                    try {
                        dataHashMap = new HashMap();
//          String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector dcmRegion = RegionDAO.getAllRegions(con);
                        Vector<RegionLevelDto> levels = RegionDAO.getALLRegionlevels(con);
                        dataHashMap.put(DCMInterfaceKey.VECTOR_ALL_REGIONS_LEVELS, levels);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmRegion);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, RegionDAO.getParentRegions(con));
                        dataHashMap.put(DCMInterfaceKey.SEARCH_REGION_RESULT, null);
                        dataHashMap.put(DCMInterfaceKey.INPUT_TEXT_REGION_NAME, "");
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_LEVEL_NAME, "");
                        dataHashMap.put(DCMInterfaceKey.Message, "first");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case EDIT_REGION:
                    try {
                        dataHashMap = new HashMap();
//          String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strRegionId = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_EDIT_REGION);
                        dataHashMap.put(DCMInterfaceKey.CONTROL_REGION_MANAG_TEXT_ID, strRegionId);
                        RegionModel regionModel = (RegionModel) RegionDAO.getRegionById(con, strRegionId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, regionModel);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case INSERT_NEW_REGION:
                    try {
                        dataHashMap = new HashMap();
//         String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Long strRegionId = Utility.getSequenceNextVal(con, "SEQ_REGION_ID");
                        String strRegionName = (String) paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_REGION_NAME);
                        String strRegionParentId = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_NAME);
                        Utility.logger.debug("sssssssssssssssssssssssssssssssss" + strRegionParentId);
                        RegionDAO.insertNewRegion(con, strRegionName, strRegionParentId, strRegionId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, RegionDAO.getParentRegions(con));
                        Vector dcmRegion = RegionDAO.getAllRegions(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmRegion);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;



                case MOVE_REGION_FROM_TO:
                    try {
                        dataHashMap = new HashMap();
//          String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strRegionId = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_NAME_TO);
//          System.out.println("The region id isssssss " + strRegionId);
                        String strRegionParentId = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_NAME_FROM);
                        System.out.println("The parent region idddddddddd is " + strRegionParentId);
                        RegionDAO.moveRegion(con, strRegionParentId, strRegionId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, RegionDAO.getParentRegions(con));
                        Vector dcmRegion = RegionDAO.getAllRegions(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmRegion);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case UPDATE_REGION_NAME:
                    try {
                        dataHashMap = new HashMap();
//          String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strRegionID = (String) paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_REGION_ID);
                        String strRegionName = (String) paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_REGION_NAME);
                        RegionDAO.updateRegionName(con, strRegionID, strRegionName);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, RegionDAO.getParentRegions(con));
                        Vector dcmRegion = RegionDAO.getAllRegions(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmRegion);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case CHAIN_MANAGEMENT:
                    try {
                        dataHashMap = new HashMap();
                        int rowNum = 0;
                        dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_ROW_NUM, "" + rowNum);
                        chainCode = "";
                        chainLevel = "";
                        chainChannel = "";
                        chainPaymentLevel = "";
                        chainCity = "";
                        chainDistrict = "";
                        chainStatus = "";
                        chainStkNumber = "";
                        int count = ChainDAO.countOfTheTable(con, chainCode, chainLevel, chainChannel, chainPaymentLevel, chainCity, chainDistrict, chainStatus, chainStkNumber);
                        dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_COUNT, "" + count);
                        Vector chainList = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, chainList);
                        chainLevelList = ChainDAO.getAllChainLevel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, chainLevelList);
                        chainPaymentLevelList = ChainDAO.getAllChainPaymentLevel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, chainPaymentLevelList);
                        chainChannelList = ChainDAO.getAllChainChannel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3, chainChannelList);
                        chainCityList = ChainDAO.getAllChainCity(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_4, chainCityList);
                        chainDistrictList = ChainDAO.getAllChainDistrict(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_5, chainDistrictList);
                        chainStatusList = ChainDAO.getAllChainStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_XXX, chainStatusList);
//          String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SEARCH_GEN_DCM:
                    try {
                        dataHashMap = new HashMap();
//          String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        int rowNum = 0;
                        int count = Integer.parseInt((String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_COUNT));
                        dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_COUNT, "" + count);
                        dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_ROW_NUM, "" + rowNum);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, DCMInterfaceKey.ACTION_SEARCH_GEN_DCM);
                        chainCode = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_CODE);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_CODE, chainCode);
                        chainLevel = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_LEVEL);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_LEVEL, chainLevel);
                        chainChannel = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CHANNEL);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CHANNEL, chainChannel);
                        chainPaymentLevel = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_PAYMENT_LEVEL);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_PAYMENT_LEVEL, chainPaymentLevel);
                        chainCity = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CITY);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CITY, chainCity);
                        chainDistrict = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_DISTRICT);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_DISTRICT, chainDistrict);
                        chainStatus = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_STATUS);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_STATUS, chainStatus);
                        chainStkNumber = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_STK_NUMBER);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_STK_NUMBER, chainStkNumber);
                        chainLevelList = ChainDAO.getAllChainLevel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, chainLevelList);
                        chainPaymentLevelList = ChainDAO.getAllChainPaymentLevel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, chainPaymentLevelList);
                        chainChannelList = ChainDAO.getAllChainChannel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3, chainChannelList);
                        chainCityList = ChainDAO.getAllChainCity(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_4, chainCityList);
                        chainDistrictList = ChainDAO.getAllChainDistrict(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_5, chainDistrictList);
                        chainStatusList = ChainDAO.getAllChainStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_XXX, chainStatusList);

                        Vector chainList = ChainDAO.getChainByFilter(con, rowNum, chainCode, chainLevel, chainChannel, chainPaymentLevel, chainCity, chainDistrict, chainStatus, chainStkNumber);
                        dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_ROW_NUM, "" + rowNum);
                        //int totalPages = (Integer.parseInt(chainList.get(0).toString()));
                        //if(totalPages%50 ==0)
                        //totalPages = (totalPages/50);
                        //else
                        //{
                        //totalPages = (totalPages/50);
                        //totalPages ++;
                        //}
                        //dataHashMap.put(DCMInterfaceKey.CONTROL_HIDDEN_TOTAL_PAGES, String.valueOf(totalPages) );
                        //chainList.remove(0);

                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, chainList);
                        //dataHashMap.put(DCMInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER, paramHashMap.get(DCMInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER));
                        dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_PAGE_END_INDEX, 50 + "");
                        dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_PAGE_START_INDEX, 0 + "");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case NEXT_GEN_DCM:
                    try {
                        dataHashMap = new HashMap();
                        //String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, DCMInterfaceKey.ACTION_SEARCH_GEN_DCM);
                        chainCode = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_CODE);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_CODE, chainCode);
                        chainLevel = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_LEVEL);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_LEVEL, chainLevel);
                        chainChannel = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CHANNEL);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CHANNEL, chainChannel);
                        chainPaymentLevel = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_PAYMENT_LEVEL);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_PAYMENT_LEVEL, chainPaymentLevel);
                        chainCity = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CITY);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CITY, chainCity);
                        chainDistrict = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_DISTRICT);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_DISTRICT, chainDistrict);
                        chainStatus = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_STATUS);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_STATUS, chainStatus);
                        chainStkNumber = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_STK_NUMBER);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_STK_NUMBER, chainStkNumber);
                        chainLevelList = ChainDAO.getAllChainLevel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, chainLevelList);
                        chainPaymentLevelList = ChainDAO.getAllChainPaymentLevel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, chainPaymentLevelList);
                        chainChannelList = ChainDAO.getAllChainChannel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3, chainChannelList);
                        chainCityList = ChainDAO.getAllChainCity(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_4, chainCityList);
                        chainDistrictList = ChainDAO.getAllChainDistrict(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_5, chainDistrictList);
                        chainStatusList = ChainDAO.getAllChainStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_XXX, chainStatusList);
                        int rowNum = Integer.parseInt((String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        rowNum = rowNum + 1;
                        Vector chainList = ChainDAO.getChainByFilter(con, rowNum, chainCode, chainLevel, chainChannel, chainPaymentLevel, chainCity, chainDistrict, chainStatus, chainStkNumber);
                        dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_ROW_NUM, "" + rowNum);
                        int count = Integer.parseInt((String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_COUNT));
                        dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_COUNT, "" + count);
                        //int totalPages = (Integer.parseInt(chainList.get(0).toString()));
                        //if(totalPages%50 ==0)
                        //totalPages = (totalPages/50);
                        //else
                        //{
                        //totalPages = (totalPages/50);
                        //totalPages ++;
                        //}
                        //dataHashMap.put(DCMInterfaceKey.CONTROL_HIDDEN_TOTAL_PAGES, String.valueOf(totalPages) );
                        //chainList.remove(0);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, chainList);
                        //dataHashMap.put(DCMInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER, paramHashMap.get(DCMInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER));
                        //int startIndex = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_PAGE_START_INDEX));
                        //int endIndex = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_PAGE_END_INDEX));
                        //startIndex+=50;
                        //endIndex+=50;

                        //dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_PAGE_END_INDEX , endIndex+"");
                        //dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_PAGE_START_INDEX , startIndex+"");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case PREVIOUS_GEN_DCM:
                    try {
                        dataHashMap = new HashMap();
                        //String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        int count = Integer.parseInt((String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_COUNT));
                        dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_COUNT, "" + count);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, DCMInterfaceKey.ACTION_SEARCH_GEN_DCM);
                        chainCode = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_CODE);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_CODE, chainCode);
                        chainLevel = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_LEVEL);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_LEVEL, chainLevel);
                        chainChannel = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CHANNEL);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CHANNEL, chainChannel);
                        chainPaymentLevel = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_PAYMENT_LEVEL);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_PAYMENT_LEVEL, chainPaymentLevel);
                        chainCity = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CITY);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CITY, chainCity);
                        chainDistrict = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_DISTRICT);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_DISTRICT, chainDistrict);
                        chainStatus = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_STATUS);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_STATUS, chainStatus);
                        chainStkNumber = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_STK_NUMBER);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_STK_NUMBER, chainStkNumber);
                        chainLevelList = ChainDAO.getAllChainLevel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, chainLevelList);
                        chainPaymentLevelList = ChainDAO.getAllChainPaymentLevel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, chainPaymentLevelList);
                        chainChannelList = ChainDAO.getAllChainChannel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3, chainChannelList);
                        chainCityList = ChainDAO.getAllChainCity(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_4, chainCityList);
                        chainDistrictList = ChainDAO.getAllChainDistrict(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_5, chainDistrictList);
                        chainStatusList = ChainDAO.getAllChainStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_XXX, chainStatusList);
                        int rowNum = Integer.parseInt((String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_ROW_NUM));

                        rowNum = rowNum - 1;
                        Vector chainList = ChainDAO.getChainByFilter(con, rowNum, chainCode, chainLevel, chainChannel, chainPaymentLevel, chainCity, chainDistrict, chainStatus, chainStkNumber);
                        dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_ROW_NUM, "" + rowNum);
                        //int totalPages = (Integer.parseInt(chainList.get(0).toString()));
                        //if(totalPages%50 ==0)
                        //totalPages = (totalPages/50);
                        //else
                        //{
                        //totalPages = (totalPages/50);
                        //totalPages ++;
                        //}
                        //dataHashMap.put(DCMInterfaceKey.CONTROL_HIDDEN_TOTAL_PAGES, String.valueOf(totalPages) );
                        //chainList.remove(0);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, chainList);
                        //dataHashMap.put(DCMInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER, paramHashMap.get(DCMInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER));
                        //int startIndex = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_PAGE_START_INDEX));
                        //int endIndex = Integer.parseInt((String)paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_PAGE_END_INDEX));
                        //startIndex-=50;
                        //endIndex=startIndex + 50;
                        //dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_PAGE_END_INDEX , endIndex+"");
                        //dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_PAGE_START_INDEX , startIndex+"");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case EDIT_GEN_DCM:
                    try {
                        dataHashMap = new HashMap();
                        //String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        int count = Integer.parseInt((String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_COUNT));
                        dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_COUNT, "" + count);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strDcmId = (String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_ID);
                        ChainModel chainModel = (ChainModel) ChainDAO.selectDcm(con, strDcmId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, chainModel);
                        chainLevelList = ChainDAO.getAllChainLevel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, chainLevelList);
                        chainPaymentLevelList = ChainDAO.getAllChainPaymentLevel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, chainPaymentLevelList);
                        chainChannelList = ChainDAO.getAllChainChannel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3, chainChannelList);
                        chainCityList = ChainDAO.getAllChainCity(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_4, chainCityList);
                        chainDistrictList = ChainDAO.getAllChainDistrict(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_5, chainDistrictList);
                        chainStatusList = ChainDAO.getAllChainStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_XXX, chainStatusList);
                        int rowNum = Integer.parseInt((String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_ROW_NUM, "" + rowNum);
                        Utility.logger.debug("The row num of edit is " + rowNum);
                        chainCode = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_CODE);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_CODE, chainCode);
                        chainLevel = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_LEVEL);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_LEVEL, chainLevel);
                        Utility.logger.debug("The chain level isssssssssssss " + chainLevel);
                        chainChannel = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CHANNEL);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CHANNEL, chainChannel);
                        chainPaymentLevel = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_PAYMENT_LEVEL);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_PAYMENT_LEVEL, chainPaymentLevel);
                        chainCity = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CITY);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CITY, chainCity);
                        chainDistrict = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_DISTRICT);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_DISTRICT, chainDistrict);
                        chainStatus = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_STATUS);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_STATUS, chainStatus);
                        chainStkNumber = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_STK_NUMBER);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_STK_NUMBER, chainStkNumber);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case EXPORT_GEN_DCM_TO_EXCEL:
                    try {
                        dataHashMap = new HashMap();
                        //String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        int count = Integer.parseInt((String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_COUNT));
                        dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_COUNT, "" + count);
                        /*//Vector vecchainList = new Vector();
                         //dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,vecchainList);
                         //Vector chainList = ChainDAO.getAllChains(con);
                         //dataHashMap.put(InterfaceKey.HASHMAP_KEY_XXX,chainList);*/
                        chainLevelList = ChainDAO.getAllChainLevel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, chainLevelList);
                        chainPaymentLevelList = ChainDAO.getAllChainPaymentLevel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, chainPaymentLevelList);
                        chainChannelList = ChainDAO.getAllChainChannel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3, chainChannelList);
                        chainCityList = ChainDAO.getAllChainCity(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_4, chainCityList);
                        chainDistrictList = ChainDAO.getAllChainDistrict(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_5, chainDistrictList);
                        chainCode = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_CODE);
                        //dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_CODE,chainCode);
                        chainLevel = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_LEVEL);
                        //dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_LEVEL,chainLevel);
                        chainChannel = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CHANNEL);
                        //dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CHANNEL,chainChannel);
                        chainPaymentLevel = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_PAYMENT_LEVEL);
                        //dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_PAYMENT_LEVEL,chainPaymentLevel);
                        chainCity = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CITY);
                        //dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CITY,chainCity);
                        chainDistrict = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_DISTRICT);
                        //dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_DISTRICT,chainDistrict);
                        chainStatus = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_STATUS);
                        //dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_STATUS,chainStatus);
                        chainStkNumber = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_STK_NUMBER);
                        //dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_STK_NUMBER,chainStkNumber);
                        int rowNum = Integer.parseInt((String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_ROW_NUM, "" + rowNum);
                        Vector chainList = ChainDAO.getChainByFilterwithoutRow(con, chainCode, chainLevel, chainChannel, chainPaymentLevel, chainCity, chainDistrict, chainStatus, chainStkNumber);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, chainList);
                        chainStatusList = ChainDAO.getAllChainStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_XXX, chainStatusList);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case CREATE_NEW_GEN_DCM:
                    try {
                        dataHashMap = new HashMap();
                        //String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        int count = Integer.parseInt((String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_COUNT));
                        dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_COUNT, "" + count);
                        chainLevelList = ChainDAO.getAllChainLevel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, chainLevelList);
                        chainPaymentLevelList = ChainDAO.getAllChainPaymentLevel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, chainPaymentLevelList);
                        chainChannelList = ChainDAO.getAllChainChannel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3, chainChannelList);
                        chainCityList = ChainDAO.getAllChainCity(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_4, chainCityList);
                        chainDistrictList = ChainDAO.getAllChainDistrict(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_5, chainDistrictList);
                        chainStatusList = ChainDAO.getAllChainStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_XXX, chainStatusList);
                        int rowNum = 0;
                        dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_ROW_NUM, "" + rowNum);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case UPDATE_GEN_DCM:
                    try {
                        dataHashMap = new HashMap();
                        //     String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        int count = Integer.parseInt((String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_COUNT));
                        dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_COUNT, "" + count);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, DCMInterfaceKey.ACTION_SEARCH_GEN_DCM);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strDcmId = (String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_ID);
                        chainCode = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_CODE);
                        chainName = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_NAME);
                        chainMail = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_EMAIL);
                        chainPhone = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_PHONE);
                        chainStatus = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_STATUS);
                        chainLevel = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_LEVEL);
                        chainRank = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_RANK);
                        chainCity = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CITY);
                        chainDistrict = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_DISTRICT);
                        chainPaymentLevel = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_PAYMENT_LEVEL);
                        chainAddress = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_ADDRESS);
                        chainChannel = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CHANNEL);

                        String searchChainCode = (String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_CHAIN_CODE);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_CODE, searchChainCode);
                        String searchChainLevel = (String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_CHAIN_LEVEL);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_LEVEL, searchChainLevel);
                        String searchChainChannel = (String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_CHAIN_CHANNEL);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CHANNEL, searchChainChannel);
                        String searchChainPaymentLevel = (String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_CHAIN_PAYMENT_LEVEL);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_PAYMENT_LEVEL, searchChainPaymentLevel);
                        String searchChainCity = (String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_CHAIN_CITY);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CITY, searchChainCity);
                        String searchChainDistrict = (String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_CHAIN_DISTRICT);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_DISTRICT, searchChainDistrict);
                        String searchChainStatus = (String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_CHAIN_STATUS);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_STATUS, searchChainStatus);
                        String searchStkNumber = (String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_STK_NUMBER);
                        dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_TEXT_STK_NUMBER, searchStkNumber);
                        Utility.logger.debug("The chain channel is " + chainChannel);
                        chainStkNumber = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_STK_NUMBER);

                        ChainDAO.updateGenDcm(con, strDcmId, chainCode, chainName, chainMail, chainPhone, chainRank, chainAddress, chainStkNumber, chainChannel, chainLevel, chainPaymentLevel, chainCity, chainDistrict, chainStatus);
                        chainLevelList = ChainDAO.getAllChainLevel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, chainLevelList);
                        chainPaymentLevelList = ChainDAO.getAllChainPaymentLevel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, chainPaymentLevelList);
                        chainChannelList = ChainDAO.getAllChainChannel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3, chainChannelList);
                        chainCityList = ChainDAO.getAllChainCity(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_4, chainCityList);
                        chainDistrictList = ChainDAO.getAllChainDistrict(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_5, chainDistrictList);
                        chainStatusList = ChainDAO.getAllChainStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_XXX, chainStatusList);
                        //int rowNum = 0;
                        int rowNum = Integer.parseInt((String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_ROW_NUM, "" + rowNum);
                        //Vector chainList = ChainDAO.getChainByFilter(con,rowNum,chainCode,chainLevel,chainChannel,chainPaymentLevel,chainCity,chainDistrict,chainStatus,chainStkNumber);
                        Vector chainList = ChainDAO.getChainByFilter(con, rowNum, searchChainCode, searchChainLevel, searchChainChannel, searchChainPaymentLevel, searchChainCity, searchChainDistrict, searchChainStatus, searchStkNumber);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, chainList);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SAVE_GEN_DCM:
                    try {
                        dataHashMap = new HashMap();
                        int count = Integer.parseInt((String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_COUNT));
                        dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_COUNT, "" + count);
                        //String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Long chainId = Utility.getSequenceNextVal(con, "SEQ_GEN_DCM_ID");
                        chainCode = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_CODE);
                        chainName = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_NAME);
                        chainPhone = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_PHONE);
                        chainMail = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_EMAIL);
                        chainRank = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_RANK);
                        chainStkNumber = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_STK_NUMBER);
                        chainAddress = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_ADDRESS);
                        chainStatus = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_STATUS);
                        chainLevel = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_LEVEL);
                        chainPaymentLevel = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_PAYMENT_LEVEL);
                        chainChannel = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CHANNEL);
                        chainCity = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CITY);
                        chainDistrict = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_DISTRICT);
                        ChainDAO.insertGenDcm(con, chainId, chainCode, chainName, chainMail, chainPhone, chainRank, chainAddress, chainStkNumber, chainChannel, chainLevel, chainPaymentLevel, chainCity, chainDistrict, chainStatus);
                        Vector chainList = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, chainList);
                        chainLevelList = ChainDAO.getAllChainLevel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, chainLevelList);
                        chainPaymentLevelList = ChainDAO.getAllChainPaymentLevel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, chainPaymentLevelList);
                        chainChannelList = ChainDAO.getAllChainChannel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3, chainChannelList);
                        chainCityList = ChainDAO.getAllChainCity(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_4, chainCityList);
                        chainDistrictList = ChainDAO.getAllChainDistrict(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_5, chainDistrictList);
                        chainStatusList = ChainDAO.getAllChainStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_XXX, chainStatusList);
                        int rowNum = 0;
                        dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_ROW_NUM, "" + rowNum);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case DCM_UPLOAD_POS_FOR_GROUP:
                    try {
                        dataHashMap = new HashMap();
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector tableDefVector = DataImportTableDefDAO.getTableDefByCategory("12");
                        dataHashMap.put(AdministrationInterfaceKey.TABLE_DEF_VECTOR, tableDefVector);
                        Vector listOfGroups = GroupDAO.getAllGroups(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, listOfGroups);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case DCM_UPLOAD_POS_FOR_GROUP_PROCESS:
                    try {
                        dataHashMap = new HashMap();
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String groupId = (String) paramHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_GROUP_ID);
                        dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_GROUP_ID, groupId);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case search_region: {
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);

                    String destinationPage = (String) paramHashMap.get(SCMInterfaceKey.DESTINATION_PAGE);
                    if (destinationPage == null) {
                        destinationPage = "0";
                    }
                    String regionName = (String) paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_REGION_NAME);
                    String levelId = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_LEVEL_NAME);
                    Integer totalpages = RegionDAO.getRegionByNameCount(regionName, levelId, con);
                    Vector<RegionModel> regions = RegionDAO.getRegionByName(regionName, levelId, con, destinationPage);
                    dataHashMap.put(DCMInterfaceKey.SEARCH_REGION_RESULT, regions);
                    Vector<RegionLevelDto> levels = RegionDAO.getALLRegionlevels(con);
                    dataHashMap.put(DCMInterfaceKey.VECTOR_ALL_REGIONS_LEVELS, levels);
                    dataHashMap.put(DCMInterfaceKey.INPUT_TEXT_REGION_NAME, regionName);
                    dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_LEVEL_NAME, levelId);
                    dataHashMap.put(DCMInterfaceKey.INPUT_CONTROL_PAGE_NUMBER, destinationPage);
                    dataHashMap.put(DCMInterfaceKey.STRING_OF_TOTAL_PAGE_NUMBER, totalpages.toString());
                    dataHashMap.put(DCMInterfaceKey.Message, "");
                }
                break;

                case add_childs_to_region: {
                    String regionId = (String) paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_REGION_ID);
                    Integer level = RegionDAO.getRegionLevelById(regionId, con);
                    dataHashMap.put(DCMInterfaceKey.INPUT_TEXT_REGION_ID, regionId);
                    dataHashMap.put(DCMInterfaceKey.Message, "");
                    dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_LEVEL_TYPE_ID, level.toString());
                }
                break;

                case save_childs_to_region: {
                    String regionId = (String) paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_REGION_ID);
                    String regionName = (String) paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_REGION_NAME);
                    String regionCode = (String) paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_REGION_CODE);
                    String regionArabicName = (String) paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_REGION_ANAME);
                    String campas_Code = (String) paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_CAPMAS_CODE);
                    String typeOriginalLevelId = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_ORIGINAL_LEVEL_NAME);
                    String typeCovarageLevelId = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_COVARAGE_LEVEL_NAME);
                    String pop_Code = (String) paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_POP_CODE);
                    String family = (String) paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_FAMILY);
                    String SCEARABICNAME = (String) paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_MARKAZ_ANAME);
                    String SCEENGLISHNAME = (String) paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_MARKAZ_ENAME);
                    RegionDAO.insertNewChild(con, regionName, regionId, campas_Code, regionArabicName, typeOriginalLevelId, typeCovarageLevelId, pop_Code, SCEARABICNAME, SCEENGLISHNAME, family, regionCode);
                    Integer level = RegionDAO.getRegionLevelById(regionId, con);
                    dataHashMap.put(DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_LEVEL_TYPE_ID, level.toString());
                    dataHashMap.put(DCMInterfaceKey.INPUT_TEXT_REGION_ID, regionId);
                    dataHashMap.put(DCMInterfaceKey.Message, "Operation Sucessfull");
                }
                break;

                case delete_region: {
                    String regionId = (String) paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_REGION_ID);
                    String message = RegionDAO.deleteRegion(con, regionId);
                    String regionName = (String) paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_REGION_NAME);
                    String levelId = (String) paramHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_LEVEL_NAME);
                    String destinationPage = (String) paramHashMap.get(DCMInterfaceKey.DESTINATION_PAGE);
                    if (destinationPage == null) {
                        destinationPage = "0";
                    }
                    if (message == "") {
                        dataHashMap.put(DCMInterfaceKey.Message, "Deleted");
                    } else {
                        dataHashMap.put(DCMInterfaceKey.Message, message);
                    }
                    Integer totalpages = RegionDAO.getRegionByNameCount(regionName, levelId, con);
                    Vector<RegionModel> regions = RegionDAO.getRegionByName(regionName, levelId, con, destinationPage);
                    dataHashMap.put(DCMInterfaceKey.SEARCH_REGION_RESULT, regions);
                    Vector<RegionLevelDto> levels = RegionDAO.getALLRegionlevels(con);
                    dataHashMap.put(DCMInterfaceKey.VECTOR_ALL_REGIONS_LEVELS, levels);
                    dataHashMap.put(DCMInterfaceKey.INPUT_CONTROL_PAGE_NUMBER, destinationPage);
                    dataHashMap.put(DCMInterfaceKey.STRING_OF_TOTAL_PAGE_NUMBER, totalpages.toString());
                    dataHashMap.put(DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_LEVEL_NAME, levelId);
                    dataHashMap.put(DCMInterfaceKey.INPUT_TEXT_REGION_NAME, regionName);
                }
                break;

                case action_view_region_parent: {
                    String regionId = (String) paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_REGION_ID);
                    Vector<RegionModel> parents = RegionDAO.getParentRegion(con, regionId);
                    dataHashMap.put(DCMInterfaceKey.VECTOR_REGION_PARENTS, parents);
                    dataHashMap.put(DCMInterfaceKey.INPUT_TEXT_REGION_ID, regionId);
                }
                break;

                case action_view_edit_parent: {
                    //Map<Long, String> checked = new HashMap<Long, String>();
                    int paramHashMapSize = paramHashMap.size();
                    Vector<RegionModel> selectedList = new Vector<RegionModel>();

                    for (int i = 0; i < paramHashMapSize; i++) {
                        String tempKey = (String) paramHashMap.keySet().toArray()[i];
                        //System.out.println(paramHashMap.get(tempKey));
//	                  String tempValue = (String)paramHashMap.get(tempKey);
                        //Utility.logger.debug("wwwwwww"+tempKey+"-----------------"+tempValue);

                        if (tempKey.startsWith(DCMInterfaceKey.CONTROL_SHOW_REGIONS_CHECKBOX)) {
                            String labelIdKey = tempKey.substring((DCMInterfaceKey.CONTROL_SHOW_REGIONS_CHECKBOX).length());
                            //Utility.logger.debug(reportId);
                            System.out.println("The label id issssssssssss " + labelIdKey);

                            selectedList.add(RegionDAO.selectedParent(con, labelIdKey));
                            // RegionDAO.editParent(con, labelIdKey);
                            Vector paymentTypes = RegionDAO.editParent(con, labelIdKey);
                            dataHashMap.put(DCMInterfaceKey.VECTOR_PARENTS, paymentTypes);
                            dataHashMap.put(DCMInterfaceKey.VECTOR_SELECTED, selectedList);


                        }
                    }
                    System.out.println("User ID IS:" + strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);

                    //       Vector<RegionModel> parents = RegionDAO.getParentRegion(con, regionId);
                    //      dataHashMap.put(DCMInterfaceKey.VECTOR_REGION_PARENTS, parents);
                    //      dataHashMap.put(DCMInterfaceKey.INPUT_TEXT_REGION_ID, regionId);
                }
                break;

                case action_update_geographical: {

                    String Id = (String) paramHashMap.get(DCMInterfaceKey.CONTROL_SHOW_PARENT_COMBOBOX);
                    int paramHashMapSize = paramHashMap.size();

                    Vector regionVector = new Vector();
                    for (int i = 0; i < paramHashMapSize; i++) {
                        String tempKey = (String) paramHashMap.keySet().toArray()[i];

                        if (tempKey.startsWith(DCMInterfaceKey.CONTROL_HIDDEN_UPDATE_CHILDS)) {
                            String labelIdKey = tempKey.substring((DCMInterfaceKey.CONTROL_HIDDEN_UPDATE_CHILDS).length());
                            System.out.println("The label id issssssssssss " + labelIdKey);

                            regionVector.add(labelIdKey);
                        }
                    }

                    RegionDAO.updateParent(con, Id, regionVector);

                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(DCMInterfaceKey.VECTOR_UPDATED, regionVector);

                }

                case action_view_region_childs: {
                    String destinationPage = (String) paramHashMap.get(DCMInterfaceKey.DESTINATION_PAGE);
                    if (destinationPage == null) {
                        destinationPage = "0";
                    }
                    String regionId = (String) paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_REGION_ID);
                    Integer totalChilds = RegionDAO.getChildstotal(con, regionId);
                    Vector<RegionModel> regions = RegionDAO.getChilds(con, regionId, destinationPage);
                    dataHashMap.put(DCMInterfaceKey.VECTOR_REGION_CHILDS, regions);
                    dataHashMap.put(DCMInterfaceKey.INPUT_TEXT_REGION_ID, regionId);
                    dataHashMap.put(DCMInterfaceKey.INPUT_CONTROL_PAGE_NUMBER, destinationPage);
                    dataHashMap.put(DCMInterfaceKey.STRING_OF_TOTAL_PAGE_NUMBER, totalChilds.toString());
                }
                break;
                case action_view_region_details: {
                    String regionId = (String) paramHashMap.get(DCMInterfaceKey.INPUT_TEXT_REGION_ID);
                    Vector<AreaDataDetailsModel> details = RegionDAO.getAllDetailsForArea(con, regionId);
                    dataHashMap.put(DCMInterfaceKey.VECTOR_REGION_DETAILS, details);
                    dataHashMap.put(DCMInterfaceKey.INPUT_TEXT_REGION_ID, regionId);
                }
                break;
                case upload_excel_address: {
                }
                break;
                case generate_excel_template: {
                }
                break;
                case action_upload_generated_file: {
                }
                break;


                default:
                    Utility.logger.debug("Unknown action received: " + action);
            }


        } catch (Exception objExp) {
            objExp.printStackTrace();


            throw objExp;
        }
        return dataHashMap;
    }
}
