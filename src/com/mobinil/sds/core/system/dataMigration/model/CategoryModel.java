package com.mobinil.sds.core.system.dataMigration.model;
import java.io.Serializable;
import java.sql.ResultSet;

public class CategoryModel implements Serializable{
	
	String categoryId;
	String categoryName;
	
	public static final String CATEGORY_ID = "CATEGORY_ID";
	public static final String CATEGORY_NAME = "CATEGORY_NAME";
	
	public CategoryModel(){
		
	}
	
	public CategoryModel(ResultSet res)
	{
		try
		{
			categoryId = res.getString(CATEGORY_ID);
			categoryName = res.getString(CATEGORY_NAME);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
