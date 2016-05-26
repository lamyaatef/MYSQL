package com.mobinil.sds.core.system.cam.memo.model;

public class MemoTypeModel 
{

    int id;
    String type;

    public MemoTypeModel(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }
}