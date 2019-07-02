package com.bocsoft.callforcode.utils;

import static java.lang.String.format;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bocsoft.callforcode.common.JsonUtil;

@Component
public class WatsonOper {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public String predict(String asset, String transfreq,String credit) throws IOException {
		
		logger.info(format("send HTTP post to watson, with asset [%s] transfreq [%s] credit [%s]", asset, transfreq, credit));
		
		String predict_credit = "10000";
		
		// NOTE: generate <iam_token> based on provided documentation
		String wml_token = "Bearer eyJraWQiOiIyMDE5MDIwNCIsImFsZyI6IlJTMjU2In0.eyJpYW1faWQiOiJpYW0tU2VydmljZUlkLTA0NWUzNGNkLWFkMDgtNDc5Ni05MDFlLWIzMWI2NjEzNjkzZiIsImlkIjoiaWFtLVNlcnZpY2VJZC0wNDVlMzRjZC1hZDA4LTQ3OTYtOTAxZS1iMzFiNjYxMzY5M2YiLCJyZWFsbWlkIjoiaWFtIiwiaWRlbnRpZmllciI6IlNlcnZpY2VJZC0wNDVlMzRjZC1hZDA4LTQ3OTYtOTAxZS1iMzFiNjYxMzY5M2YiLCJzdWIiOiJTZXJ2aWNlSWQtMDQ1ZTM0Y2QtYWQwOC00Nzk2LTkwMWUtYjMxYjY2MTM2OTNmIiwic3ViX3R5cGUiOiJTZXJ2aWNlSWQiLCJhY2NvdW50Ijp7InZhbGlkIjp0cnVlLCJic3MiOiI0OTU0MDI0ZTE5NDY0MTE4ODQ5M2M2ZWRiOGMwYWE4MyJ9LCJpYXQiOjE1NjEzMDIzNDYsImV4cCI6MTU2MTMwNTk0NiwiaXNzIjoiaHR0cHM6Ly9pYW0ubmcuYmx1ZW1peC5uZXQvb2lkYy90b2tlbiIsImdyYW50X3R5cGUiOiJ1cm46aWJtOnBhcmFtczpvYXV0aDpncmFudC10eXBlOmFwaWtleSIsInNjb3BlIjoiaWJtIG9wZW5pZCIsImNsaWVudF9pZCI6ImJ4IiwiYWNyIjoxLCJhbXIiOlsicHdkIl19.P0oCVhAncd4hjH1YHsRVeccboYFYlZOw1qLlAaFrN3pqlXDTa1o7kinMisPGdhUsN_Fjm0-mCo_7WtDJwhmcysgPVQqv2XeUxtvhnHZ55pDv3bFTL6Ajn7KZVZaFohJ4vnnQsiF_Bp7J7oYkn_zftwl6nlUDuBLbMb-_uleJtpaDbkYsucFzRxYhB9EBOgrA-WIpUKyym6WAQk13k56hZpONjX0NJL63L2Ql1kjipVbuddQ4UdeggaZDeNWLhE4WM_55xpAdAUvdZFl5uSyQgfKM7gX4VQPVQKA5TvrxMX1u731_9QwxLjeOSBm3zT0Id8eOk4zzm1SYargQh7J6bg";

		// NOTE: retrieve <ml_instance_id> based on provided documentation
		String ml_instance_id = "6b50131c-e7e8-4e49-b836-93f2300392d9";

		HttpURLConnection scoringConnection = null;
		BufferedReader scoringBuffer = null;
		try {
			// Scoring request
			URL scoringUrl = new URL("https://us-south.ml.cloud.ibm.com/v3/wml_instances/6b50131c-e7e8-4e49-b836-93f2300392d9/deployments/c2409c20-653e-4937-8ad0-d187d28a078a/online");
			scoringConnection = (HttpURLConnection) scoringUrl.openConnection();
			scoringConnection.setDoInput(true);
			scoringConnection.setDoOutput(true);
			scoringConnection.setRequestMethod("POST");
			scoringConnection.setRequestProperty("Accept", "application/json");
			scoringConnection.setRequestProperty("Authorization", wml_token);
			scoringConnection.setRequestProperty("ML-Instance-ID", ml_instance_id);
			scoringConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(scoringConnection.getOutputStream(), "UTF-8");

			// NOTE: manually define and pass the array(s) of values to be scored in the next line
			//String payload = "{\"fields\": [\"asset\", \"transfreq\", \"credit\"], \"values\": [[12000, 40, 90]]}";
			String payload = format("{\"fields\": [\"asset\", \"transfreq\", \"credit\"], \"values\": [[%s, %s, %s]]}",asset, transfreq, credit);
			writer.write(payload);
			writer.close();

			scoringBuffer = new BufferedReader(new InputStreamReader(scoringConnection.getInputStream()));
			StringBuffer jsonStringScoring = new StringBuffer();
			String lineScoring;
			while ((lineScoring = scoringBuffer.readLine()) != null) {
				jsonStringScoring.append(lineScoring);
			}
			logger.info(jsonStringScoring.toString());
			
			WatsonResponse watsonResponse = new WatsonResponse();
			String watsonresp = jsonStringScoring.toString().replaceAll("\\[[0-9,. ]*\\],", "");
			watsonResponse = JsonUtil.jsonToObj(watsonresp,WatsonResponse.class);
			predict_credit = watsonResponse.getValues()[0][3].toString();
			
		} catch (IOException e) {
			logger.error("The URL is not valid.",e);
		}
		finally {
			if (scoringConnection != null) {
				scoringConnection.disconnect();
			}
			if (scoringBuffer != null) {
				scoringBuffer.close();
			}
			if (scoringConnection != null) {
				scoringConnection.disconnect();
			}
			if (scoringBuffer != null) {
				scoringBuffer.close();
			}
		}
		
		return predict_credit;
	}
	
	public static class WatsonResponse{
		private String[] fields;
		private String[][] values;
		public String[] getFields() {
			return fields;
		}
		public void setFields(String[] fields) {
			this.fields = fields;
		}
		public String[][] getValues() {
			return values;
		}
		public void setValues(String[][] values) {
			this.values = values;
		}
		
	}
	public static void main(String []args) {
//		String payload = format("{\"fields\": [\"asset\", \"transfreq\", \"credit\"], \"values\": [[%s, %s, %s]]}","1", "2", "3");
//		System.out.println(payload);
		
		WatsonOper watsonOper = new WatsonOper();
		try {
			watsonOper.predict("10000", "20", "90");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		String watsonresp = "{  \"fields\": [\"asset\", \"transfreq\", \"credit\", \"features\", \"prediction\"],  \"values\": [[10000, 20, 90, [10000.0, 20.0, 90.0], 10956.27183793744]]}";
//		watsonresp = watsonresp.replaceAll("\\[[0-9,. ]*\\],", "");
//		String resp = "{  \"fields\": [\"asset\", \"transfreq\", \"credit\", \"features\", \"prediction\"] }";
//		WatsonResponse watson = new WatsonResponse();
//		watson = JsonUtil.jsonToObj(watsonresp,WatsonResponse.class);
//		System.out.println();
		
	}
}