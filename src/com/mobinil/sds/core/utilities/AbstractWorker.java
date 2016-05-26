package com.mobinil.sds.core.utilities;

public class AbstractWorker extends Thread 
{
  public AbstractWorker()
  {
  }

  protected Buffer myBuffer= null;

  public void setBuffer(Buffer buffer)
  {
    this.myBuffer = buffer;
  }

  
}