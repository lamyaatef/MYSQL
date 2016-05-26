package com.mobinil.sds.core.system.nomadFile.model;

import com.mobinil.sds.core.system.authenticationResult.model.*;
import java.sql.ResultSet;
public class NomadLabelModel {

		String labelId;
		String labelName;
		String labelDescription;

		public static final String  LABEL_ID  = "LABEL_ID";
		public static final String LABEL_NAME  = "LABEL_NAME";
		public static final String LABEL_DESCRIPTION  = "LABEL_DESCRIPTION";
		NomadLabelModel() {}
		
		
		
		public NomadLabelModel(ResultSet res) {
			try {
				labelId = res.getString("NOMAD_LABEL_ID");
				labelName = res.getString("NOMAD_LABEL_NAME");
				labelDescription = res.getString("NOMAD_LABEL_DESC");
		
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		

		public String getLabelId() {
			return labelId;
		}
		public void setLabelId(String labelId) {
			this.labelId = labelId;
		}
		public String getLabelName() {
			return labelName;
		}
		public void setLabelName(String labelName) {
			this.labelName = labelName;
		}
		public String getLabelDescription() {
			return labelDescription;
		}
		public void setLabelDescription(String labelDescription) {
			this.labelDescription = labelDescription;
		}


}