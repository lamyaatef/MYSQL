package com.mobinil.sds.core.system.ccm.service.model;
import java.sql.ResultSet;
public class ServiceModel {

		String serviceId;
		String serviceName;
		String serviceDescription;

		public static final String  SERVICE_ID  = "SERVICE_ID";
		public static final String SERVICE_NAME  = "SERVICE_NAME";
		public static final String SERVICE_DESCRIPTION  = "SERVICE_DESCRIPTION";
		ServiceModel() {}
		
		
		
		public ServiceModel(ResultSet res) {
			try {
				serviceId = res.getString("SERVICE_ID");
				serviceName = res.getString("SERVICE_NAME");
				serviceDescription = res.getString("SERVICE_DESCRIPTION");
		
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		

		public String getServiceId() {
			return serviceId;
		}
		public void setServiceId(String serviceId) {
			this.serviceId = serviceId;
		}
		public String getServiceName() {
			return serviceName;
		}
		public void setServiceName(String serviceName) {
			this.serviceName = serviceName;
		}
		public String getServiceDescription() {
			return serviceDescription;
		}
		public void setServiceDescription(String serviceDescription) {
			this.serviceDescription = serviceDescription;
		}


}
