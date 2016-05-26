package com.mobinil.sds.core.system.deu.runnerParams.model;

import java.sql.ResultSet;
import java.io.Serializable;

public class RunnerParamsModel  {
  private static final String DAEMON_IP="DAEMON_IP";
  private static final String DAEMON_PORT="DAEMON_PORT";
  private static final String DAEMON_PASS="DAEMON_PASS";
  private String daemonIP;
  private String daemonPort;
  private String daemonPass;
  
  public RunnerParamsModel(ResultSet res)
  {
  try
  {
    this.daemonIP = res.getString(DAEMON_IP);
    this.daemonPort = res.getString(DAEMON_PORT);
    this.daemonPass = res.getString(DAEMON_PASS);
  }
  catch(Exception  e)
  {
    e.printStackTrace();
  }
  }

  public String getDaemonIP() {
    return daemonIP;
  }

  public void setDaemonIP(String newDaemonIP) {
    daemonIP = newDaemonIP;
  }

  public String getDaemonPort() {
    return daemonPort;
  }

  public void setDaemonPort(String newDaemonPort) {
    daemonPort = newDaemonPort;
  }

  public String getDaemonPass() {
    return daemonPass;
  }

  public void setDaemonPass(String newDaemonPass) {
    daemonPass = newDaemonPass;
  }
}