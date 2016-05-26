package com.mobinil.sds.core.system.cam.memo.model;

public class MemoStateModel 
{
    int id;
    String state;

    public MemoStateModel(int id, String state) {
        this.id = id;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setState(String state) {
        this.state = state;
    }
}