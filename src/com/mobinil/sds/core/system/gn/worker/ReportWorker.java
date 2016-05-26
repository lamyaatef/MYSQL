package com.mobinil.sds.core.system.gn.worker;

import java.util.*;
import java.sql.*;
import com.mobinil.sds.web.interfaces.*;
import com.mobinil.sds.core.utilities.*;
import com.mobinil.sds.core.system.gn.reports.domain.*;
import com.mobinil.sds.core.system.gn.reports.dto.*;
import com.mobinil.sds.core.system.gn.querybuilder.dto.QueryViewerDTO;
import com.mobinil.sds.web.interfaces.gn.reports.ReportInterfaceKey;

public class ReportWorker extends AbstractWorker
{
    private HashMap paramHashMap = null;

    public ReportWorker(HashMap paramHashMap)
    {
        this.paramHashMap = paramHashMap;
    }

    public void run()
    {
        HashMap data = null;
        try
        {
            Connection conSDSConnection = Utility.getConnection();
            ReportEngine reportEngine = new ReportEngine();
            ReportBuilderWizardDTO reportBuilderWizardDTO = null;

            int nReportID = new Integer((String) paramHashMap.get(ReportInterfaceKey.PARAM_PREVIEW_REPORT_ID)).intValue();
            reportBuilderWizardDTO = reportEngine.loadReport(conSDSConnection, nReportID);

            int nPageNum = new Integer((String) paramHashMap.get(ReportInterfaceKey.PARAM_PREVIEW_REPORT_PAGE_NUM)).intValue();

            int nRowsPerPage = new Integer((String) paramHashMap.get(ReportInterfaceKey.PARAM_PREVIEW_REPORT_ROWS_PER_PAGE)).intValue();

            System.out.println("nRowsPerPage=" + nRowsPerPage);

            if (nRowsPerPage > 65000)
            {
                nRowsPerPage = -1;
            }

            Vector vecReportParameterList = reportEngine.constructReportParamList(paramHashMap);
            String userID = (String) paramHashMap.get("USER_ID");
            QueryViewerDTO queryViewerDTO = null;
           
            if (nRowsPerPage != -1)
                queryViewerDTO = reportEngine.previewReport(conSDSConnection, reportBuilderWizardDTO.getReport(), vecReportParameterList, nPageNum, nRowsPerPage, userID);
            else
            {
                String fileName = (String) paramHashMap.get(ReportInterfaceKey.EXPORT_FILE_PATH);
                queryViewerDTO = reportEngine.exportReport(conSDSConnection, reportBuilderWizardDTO.getReport(), vecReportParameterList, nPageNum, nRowsPerPage, userID, fileName);
            }

            data = new HashMap();
            data.put(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, queryViewerDTO);
            Utility.closeConnection(conSDSConnection);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        this.myBuffer.put(data);
    }
}