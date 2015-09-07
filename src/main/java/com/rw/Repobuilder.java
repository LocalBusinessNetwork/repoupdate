package com.rw;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.json.JSONException;
import org.json.JSONObject;

import au.com.bytecode.opencsv.CSVReader;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.rw.persistence.RWJApplication;
import com.rw.persistence.RWJBusComp;
import com.rw.persistence.mongoMaster;
import com.rw.persistence.mongoRepo;
import com.rw.persistence.mongoStore;

public class Repobuilder {

    /**
	 * @param args
	 * @throws Exception 
	 * java -classpath ${RWHOME}/Web/target/web/WEB-INF/lib ${RWHOME}/Web/target/web/WEB-INF/lib/RepoBuilder-V1.jar -connection dbserver -tenant tenantSuffix -file filename 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
	  	
		String rwhome = System.getenv("RWHOME");
      	System.out.println("RWHOME = " + rwhome);   	     	
      	System.setProperty("WebRoot",rwhome + "/web/src/main/webapp");
      	
		System.setProperty("JDBC_CONNECTION_STRING","localhost");
    	System.setProperty("EMAIL","local"); // Run demo setup always in non-email mode.
	
		Options options = new Options();		
		
		options.addOption("connection", true, "DB server Connection");
		options.addOption("tenant", true, "Tenant Suffix");
		options.addOption("file", true, "file Name");
		
		CommandLineParser parser = new BasicParser();
		CommandLine cmd = parser.parse( options, args);
  
		if(cmd.hasOption("connection")){ 
			System.setProperty("JDBC_CONNECTION_STRING", cmd.getOptionValue("connection"));
		}
			
		if(cmd.hasOption("tenant")){ 
			System.setProperty("PARAM3", cmd.getOptionValue("tenant"));
		}

		String tenant = System.getProperty("PARAM3");
	   	if ( tenant == null ) {
	   		System.out.println("Tenant Not specified, exiting");
	   		return;
	   	}
	   	else 
			System.out.println("TENANT = " + tenant);
	   	
		String fileName = cmd.getOptionValue("file");
		
		if ( fileName == null ||  fileName.equals("all") ) {
			LoadAllMetadata(tenant);
		}
		else  {
			LoadMetadataFile(tenant, fileName);
		}

	}
	
	public static void LoadMetadataFile(String tenant, String fileName) {
		FileReader reader;
		try {
			reader = new FileReader(fileName);
			if ( reader != null ) {
				mongoRepo r = new mongoRepo(tenant);
				r.UpdateMetaData(reader);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public static void LoadAllMetadata( String tenant ) {
		
		mongoRepo r;
		
		try {
			r = new mongoRepo(tenant);
			r.cleanUp();

			// Load Repository
			r.LoadMetaData("/Generic32.xml");
			r.LoadMetaData("/Security.xml");
			r.LoadMetaData("/Party.xml");
			r.LoadMetaData("/Contacts.xml");
			r.LoadMetaData("/Referral.xml");
			r.LoadMetaData("/ReferralOffer.xml");
			r.LoadMetaData("/Events.xml");
			r.LoadMetaData("/Partners.xml");
			r.LoadMetaData("/Assoc.xml");
			r.LoadMetaData("/Org.xml");
			r.LoadMetaData("/LOVs.xml");
			r.LoadMetaData("/ActivityStream.xml");
			r.LoadMetaData("/IncentiveRecord.xml");
			r.LoadMetaData("/Billing.xml");
			r.LoadMetaData("/BillingRcpts.xml");
			r.LoadMetaData("/CFBOutbox.xml");
			r.LoadMetaData("/Criteria.xml");
			r.LoadMetaData("/LOVMap.xml");
			r.LoadMetaData("/Recommendations.xml");
			r.LoadMetaData("/Attendees.xml");
			r.LoadMetaData("/BannerMessages.xml");
			r.LoadMetaData("/Systemstats.xml");
			r.LoadMetaData("/Charts.xml");
			r.LoadMetaData("/SavedSearch.xml");
			r.LoadMetaData("/Award.xml");
			r.LoadMetaData("/Image.xml");
			r.LoadMetaData("/AwardLevel.xml");
			r.LoadMetaData("/Metric.xml");
			r.LoadMetaData("/Reports.xml");
			r.LoadMetaData("/EmailTemplate.xml");
			r.LoadMetaData("/tenant.xml");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
