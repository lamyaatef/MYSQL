package com.mobinil.sds.core.system.sip.dto;

import java.sql.Timestamp;

public class sipDistHistoryCSVDTO
{
  private String emp_id,line_name,kpi_name,sip_report_channel_name,mode_name, kpi_value,  sip_report_id;
  private String transaction_date;



public String getLine_name()
{
   return line_name;
}

public void setLine_name(String lineName)
{
   line_name = lineName;
}

public String getKpi_name()
{
   return kpi_name;
}

public void setKpi_name(String kpiName)
{
   kpi_name = kpiName;
}

public String getSip_report_channel_name()
{
   return sip_report_channel_name;
}

public void setSip_report_channel_name(String sipReportChannelName)
{
   sip_report_channel_name = sipReportChannelName;
}

public String getMode_name()
{
   return mode_name;
}

public void setMode_name(String modeName)
{
   mode_name = modeName;
}

public String getKpi_value()
{
   return kpi_value;
}

public void setKpi_value(String kpiValue)
{
   kpi_value = kpiValue;
}

public String getTransaction_date()
{
   return transaction_date;
}

public void setTransaction_date(String transactionDate)
{
   transaction_date = transactionDate;
}

public String getSip_report_id()
{
   return sip_report_id;
}

public void setSip_report_id(String sipReportId)
{
   sip_report_id = sipReportId;
}

public void setEmp_id(String emp_id)
{
   this.emp_id = emp_id;
}

public String getEmp_id()
{
   return emp_id;
}
}
