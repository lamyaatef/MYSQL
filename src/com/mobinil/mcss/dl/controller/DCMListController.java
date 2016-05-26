package com.mobinil.mcss.dl.controller;

import com.mobinil.mcss.dl.constant.DCMListConstant;
import com.mobinil.mcss.dl.model.*;

import com.mobinil.mcss.dl.service.DCMListService;
import com.mobinil.mcss.dp.dpManagement.model.GenUser;
import com.mobinil.mcss.spring.util.ExcelFileReader;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author mabdelaal
 */
@Controller
@RequestMapping(DCMListConstant.DCM_LIST_REQUEST_MAPPING)
public class DCMListController {

    @Autowired
    private DCMListService dcmListService;

    @RequestMapping(value = "/list/DCMList", method = RequestMethod.GET)
    public ModelAndView loadDcmList(@ModelAttribute("dcmList") DCMList List, BindingResult result, HttpServletRequest request) {
        String userId = request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);
        HttpSession s = request.getSession();
        s.setAttribute(InterfaceKey.HASHMAP_KEY_USER_ID, userId);
        ModelAndView mv = new ModelAndView("/dl/list/DCMList");
        //DCMList dcmList = new DCMList();
        mv.addObject("dcmtypelist", dcmListService.getTypeList());
        mv.addObject("dataviews", dcmListService.getAllDataView());
        // mv.addObject("dcmList", dcmList);
        return mv;

    }

    @RequestMapping(value = "/list/DcmListManagment", method = RequestMethod.GET)
    public ModelAndView getAllList(HttpServletRequest request) {

        List<DCMList> list = dcmListService.getLists();
        ModelAndView mv = new ModelAndView("/dl/list/DcmListManagment");
        mv.addObject("results", list);
        return mv;
    }

    @RequestMapping(value = "/list/save", method = RequestMethod.GET)
    public ModelAndView saveList(@ModelAttribute("dcmList") DCMList dcmList,
            BindingResult result, HttpServletRequest request) {
        if (dcmList.getDL_LIST_NAME().equals("")) {
            result.rejectValue("DL_LIST_NAME", "NotEmpty.dcmList.DL_LIST_NAME", "");
        }
        if (result.hasErrors()) {

            return loadDcmList(dcmList, result, request);
//        
        }
        String userId = (String) request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
        DCMListStatus newStatus = new DCMListStatus();
        newStatus.setStatusId(5);
        dcmList.setDL_LIST_STATUS(newStatus);
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        GenUser user = new GenUser();
        Long id = Long.parseLong(userId);
        user.setUserId(id);
        dcmList.setUSER_ID(user);
        dcmList.setCREATION_DATE(currentDate);
        dcmList.setLAST_STATUS_DATE(currentDate);
        DcmListType staticType = new DcmListType();
        staticType.setTypeId(1);
        dcmList.setDL_TYPE(staticType);
        /*
         * if (dcmList.getDATA_VIEW_ID().getDataViewId() == 0) {
         * dcmList.setDATA_VIEW_ID(null); }
         */
        String msg = dcmListService.addList(dcmList);
        if (msg.equals("done")) {
            ModelAndView mv = new ModelAndView("/dl/list/Result");
            mv.addObject("cause", "List was Saved successfully");
            return mv;
        } else {
            ModelAndView mv = loadDcmList(dcmList, null, request);
            mv.addObject("msg", "Duplicated Data");
            return mv;
        }
    }

    @RequestMapping(value = "/list/delete", method = RequestMethod.GET)
    public ModelAndView deleteHistory(HttpServletRequest request) {
        String historyID = request.getParameter("hiddenhistory");
        dcmListService.updateHistoryStatus(new DCMListHistory(Long.valueOf(historyID), DCMListConstant.DCM_LIST_STATUS_DELETE));
        return viewHistory(request);
    }

    @RequestMapping(value = "/list/deactive", method = RequestMethod.GET)
    public ModelAndView deactiveList(HttpServletRequest request) {
        String historyID = request.getParameter("hiddendl");
        List<DCMList> dcmList = dcmListService.searchLists(new DCMList(Long.parseLong(historyID)));
        DCMList dcmListModel = dcmList.get(0);
        dcmListModel.setDL_LIST_STATUS(new DCMListStatus(DCMListConstant.DCM_LIST_STATUS_DEACTIVE));
        dcmListModel.setLAST_STATUS_DATE(new Date(System.currentTimeMillis()));
        dcmList.remove(0);
        dcmList.add(dcmListModel);
        dcmListService.changeStatusList(dcmList);
        return getAllList(request);
    }

    @RequestMapping(value = "/list/export", method = RequestMethod.GET)
    public ModelAndView exportHistory(HttpServletRequest request) {
        String historyID = request.getParameter("hiddenhistory"), monthName = request.getParameter("monthName"), yearName = request.getParameter("yearName");


        StringBuilder fileName = new StringBuilder("");
        fileName.append(monthName);
        fileName.append(DCMListConstant.UNDER_SCORE);
        fileName.append(yearName);
        fileName.append(DCMListConstant.UNDER_SCORE);
        fileName.append(historyID);
        fileName.append(System.currentTimeMillis());
        fileName.append(".xlsx");
        StringBuilder uniqueFileName = new StringBuilder("");
        uniqueFileName.append(request.getRealPath("/"));
        uniqueFileName.append("downloadItems");
        uniqueFileName.append(DCMListConstant.URL_SEPARATOR);
        uniqueFileName.append(fileName);

        dcmListService.exportDCMList(Long.parseLong(historyID), uniqueFileName.toString());
        ModelAndView mav = viewHistory(request);
        mav.addObject("list_excel_file", fileName);
        return mav;
    }

    @RequestMapping(value = "/list/upload", method = RequestMethod.GET)
    public ModelAndView upload(HttpServletRequest request) {
        String DLid = request.getParameter("hiddendl");
        DCMList dcmlist = new DCMList();
        if (!DLid.equals("")) {
            dcmlist = dcmListService.getDcmListById(Long.valueOf(DLid));
        }
        String update = request.getParameter("update");
        ModelAndView mv = new ModelAndView("/dl/list/upload");
        if (update.equals("1")) {
            String historyid = request.getParameter("hiddenhistory");
            DCMListHistory history = dcmListService.getHistoryById(Long.valueOf(historyid));
            Integer month = history.getMonth().getIndex();
            String year = history.getYear();
            dcmlist = history.getDcmList();
            mv.addObject("lock", "1");
            mv.addObject("month", month.toString());
            mv.addObject("year", year);
            mv.addObject("historyId", historyid);
        } else {
            mv.addObject("lock", "0");
        }
        mv.addObject("dcmList", dcmlist);
        mv.addObject("hiddendl", DLid);
        return mv;



    }

    @RequestMapping(value = "/list/history", method = RequestMethod.GET)
    public ModelAndView viewHistory(HttpServletRequest request) {
        String DLid = request.getParameter("hiddendl");
        String deleted = request.getParameter("deleted");
        List<DCMListHistory> dcmlistHistory = null;
        if (deleted != null) {
            dcmlistHistory = dcmListService.getDcmListHistoryDeleted(Long.valueOf(DLid));

        } else {
            dcmlistHistory = dcmListService.getDcmListHistory(Long.valueOf(DLid));
        }



        ModelAndView mv = new ModelAndView("/dl/list/DcmListHistory");
        String DcmListName = dcmListService.getDcmListById(Long.valueOf(DLid)).getDL_LIST_NAME();
        String DcmListId = dcmListService.getDcmListById(Long.valueOf(DLid)).getDL_LIST_ID().toString();
        mv.addObject("DcmListName", DcmListName);
        mv.addObject("DcmListId", DcmListId);
        mv.addObject("results", dcmlistHistory);
        mv.addObject("hiddendl", DLid);

        if (deleted != null) {
            mv.addObject("deleted", deleted);

        }
        return mv;

    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView importFromExcel(@ModelAttribute("dcmList") DCMList dcmList, BindingResult result, HttpServletRequest request) throws IOException, InvalidFormatException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {

        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.err.println("Error: " + error.getCode() + " - " + error.getDefaultMessage());
            }
        }
        ModelAndView mv = new ModelAndView("/dl/list/Result");
        List<DCMListDetail> detail = new ArrayList<DCMListDetail>();
        CommonsMultipartFile file = dcmList.getFileData();
        String history = (String) request.getParameter("historyId");
        String month = (String) request.getParameter("month");
        String year = (String) request.getParameter("year");
        if (file.getSize() != 0) {
            detail = ExcelFileReader.ReadExcel(file, DCMListDetail.class);
        }
        long historyId = 0;
        if (history == null || history.compareTo("") == 0) {
            try {
                historyId = dcmListService.insertHistoryForDcmList(dcmList.getDL_LIST_ID(), Integer.parseInt(month), year);
            } catch (Exception e) {
                mv.addObject("cause", "This year and month are used before.");
                mv.addObject("hiddendl", request.getParameter("hiddendl"));
                return mv;
            }

        }
        if (!detail.isEmpty()) {
            if (history == null || history.compareTo("") == 0) {

                dcmListService.importDcmListDetail(detail, historyId);
            } else {
                dcmListService.updateListFromExcelSheet(Long.parseLong(history), detail);
            }
        }


        //Ahmed Adel
        dcmList = dcmListService.getDcmListById(dcmList.getDL_LIST_ID());
        dcmListService.activeList(dcmList);
        mv.addObject("cause", "List was Saved successfully");
        mv.addObject("hiddendl", request.getParameter("hiddendl"));
        return mv;
    }

    @RequestMapping(value = "/list/historical_update", method = RequestMethod.GET)
    public ModelAndView getAllHistroicalUpdate(HttpServletRequest request) {

        List<DCMList> list = dcmListService.getAllHistroicalUpdate();
        ModelAndView mv = new ModelAndView("/dl/list/historical_update");
        mv.addObject("results", list);
        return mv;
    }
}
