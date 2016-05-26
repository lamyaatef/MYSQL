package com.mobinil.sds.core.utilities.Wrapper;

import java.util.Vector;

public class MatrixData {

    private Vector header;
    private Vector[] data;
    private int colNumber;

    public MatrixData(int colNumber) {
        this.colNumber = colNumber;
        data = new Vector[colNumber];
        for(int i = 0 ; i < this.colNumber ; i++){
            data[i] = new Vector();
        }
        header = new Vector();
    }

    public int getColNumber() {
        return colNumber;
    }

    public void setColNumber(int colNumber) {
        this.colNumber = colNumber;
    }

    public Vector[] getData() {
        return data;
    }

    public void setData(Vector[] data) {
        this.data = data;
    }

    public Vector getHeader() {
        return header;
    }

    public void setHeader(Vector header) {
        this.header = header;
    }
}