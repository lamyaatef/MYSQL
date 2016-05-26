package com.mobinil.sds.core.utilities;
import java.util.*;

public class Buffer 
{

private   HashMap myBuffer=null;

  public synchronized void put(HashMap data){
    myBuffer = data;
    notify();
  }
  public synchronized HashMap getData() {
    return myBuffer;
  }
}// Buffer
