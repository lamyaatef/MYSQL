package com.mobinil.mcss.dl.model;

import com.mobinil.mcss.dp.dpManagement.model.GenUser;
import java.io.Serializable;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.*;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Entity
@Table(name = "DL_LIST")
public class DCMList implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SEQ_DL_LIST_ID")
    @SequenceGenerator(name = "GEN_SEQ_DL_LIST_ID", sequenceName = "SEQ_DL_LIST_ID", allocationSize = 1)
    @Column(name = "DL_LIST_ID")
    private Long DL_LIST_ID; 
    
    @Column(name="DL_LIST_NAME")
    private String  DL_LIST_NAME ;
    
    @ManyToOne
    @JoinColumn(name="USER_ID",insertable=false,updatable=false)
    private GenUser  USER_ID ;
    
    @Column(name="CREATION_DATE",updatable=false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date  CREATION_DATE ;
    
    @Column(name="LAST_STATUS_DATE" )
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date  LAST_STATUS_DATE ;
    
    @ManyToOne
    @JoinColumn(name="DL_TYPE_ID")
    private DcmListType  DL_TYPE;
    
    @ManyToOne
    @JoinColumn(name="DL_LIST_STATUS_ID")
    private DCMListStatus  DL_LIST_STATUS;
    
    @OneToOne
    @JoinColumn(name="DATA_VIEW_ID")
    private DataView  DATA_VIEW_ID;
    
    @Transient
    private CommonsMultipartFile fileData;
    
    @Column(name="DATA_VIEW_SQL")
    @Lob
    private byte[]  DATA_VIEW_SQL ;

    public DCMList(Long DL_LIST_ID) {
        this.DL_LIST_ID = DL_LIST_ID;
    }

    
   
    public DCMList() {
    }

    public Long getDL_LIST_ID() {
        return DL_LIST_ID;
    }

    public void setDL_LIST_ID(Long DL_LIST_ID) {
        this.DL_LIST_ID = DL_LIST_ID;
    }

    public String getDL_LIST_NAME() {
        return DL_LIST_NAME;
    }

    public void setDL_LIST_NAME(String DL_LIST_NAME) {
        this.DL_LIST_NAME = DL_LIST_NAME;
    }

    public GenUser getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(GenUser USER_ID) {
        this.USER_ID = USER_ID;
    }

    public Date getCREATION_DATE() {
        return CREATION_DATE;
    }

    public void setCREATION_DATE(Date CREATION_DATE) {
        this.CREATION_DATE = CREATION_DATE;
    }

    public Date getLAST_STATUS_DATE() {
        return LAST_STATUS_DATE;
    }

    public void setLAST_STATUS_DATE(Date LAST_STATUS_DATE) {
        this.LAST_STATUS_DATE = LAST_STATUS_DATE;
    }

    public DcmListType getDL_TYPE() {
        return DL_TYPE;
    }

    public void setDL_TYPE(DcmListType DL_TYPE) {
        this.DL_TYPE = DL_TYPE;
    }

   
    public DataView getDATA_VIEW_ID() {
        return DATA_VIEW_ID;
    }

    public void setDATA_VIEW_ID(DataView DATA_VIEW_ID) {
        this.DATA_VIEW_ID = DATA_VIEW_ID;
    }

    /**
     * @return the fileData
     */
    public CommonsMultipartFile getFileData() {
        return fileData;
    }

    /**
     * @param fileData the fileData to set
     */
    public void setFileData(CommonsMultipartFile fileData) {
        this.fileData = fileData;
    }

    /**
     * @return the DL_LIST_STATUS
     */
    public DCMListStatus getDL_LIST_STATUS() {
        return DL_LIST_STATUS;
    }

    /**
     * @param DL_LIST_STATUS the DL_LIST_STATUS to set
     */
    public void setDL_LIST_STATUS(DCMListStatus DL_LIST_STATUS) {
        this.DL_LIST_STATUS = DL_LIST_STATUS;
    }

    public byte[] getDATA_VIEW_SQL() {
        return DATA_VIEW_SQL;
    }

    public void setDATA_VIEW_SQL(byte[] DATA_VIEW_SQL) {
        this.DATA_VIEW_SQL = DATA_VIEW_SQL;
    }
   
}
