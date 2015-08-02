package com.kewensheng.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kewensheng.tool.LogCat;

/**
 */
public class NetWorkRequest {

	public static String doPost(String senData,String urlStr) {
		try {
		    LogCat.say("senData->>>"+senData+"\nurl->>>"+urlStr);
			String reString = "";
			try {
				// Send the request
				URL url = new URL(urlStr);

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setRequestProperty("Charset", "UTF-8"); 
				conn.setRequestMethod("POST");
				conn.setConnectTimeout(10000);

				OutputStreamWriter writer = new OutputStreamWriter(
						conn.getOutputStream());

				// senData = URLEncoder.encode(senData, "UTF-8");
				// senData = toUtf8String(senData);
				// write parameters
				writer.write(senData);

				writer.flush();

				// Get the response
				StringBuffer answer = new StringBuffer();

				conn.connect();
				InputStream is = conn.getInputStream();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				String line;
				while ((line = reader.readLine()) != null) {
					answer.append(line);
				}
				writer.close();
				reader.close();
				// Output the response
				reString = answer.toString();				
				
//				try {
//					JSONArray array = new JSONArray(reString);
//					reString = array.get(0).toString();
//					LogCat.say(reString);
//					JSONObject jsonObject = new JSONObject(reString);
//					reString = jsonObject.get("body").toString();
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}

			} catch (MalformedURLException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

	 		return reString;
		
		} catch (Exception e) {
			LogCat.say(e.getMessage());
			return "";
		}
	}

//	public static String sendFile(String fileid, String offset, String md5,
//			String reset, String filepath, String postUrl) throws Exception {
//		HttpClient httpclient = new DefaultHttpClient();
//		httpclient.getParams().setParameter(
//				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
//
//		postUrl = postUrl + "?fileId=" + fileid;
//		postUrl = postUrl + "&offset=" + offset;
//		postUrl = postUrl + "&md5Key=" + md5;
//		postUrl = postUrl + "&reset=" + reset;
//
//		HttpPost httppost = new HttpPost(postUrl);
//		File file = new File(filepath);
//
//		MultipartEntity mpEntity = new MultipartEntity();
//		ContentBody cbFile = new FileBody(file, "application/octet-stream");
//		mpEntity.addPart("content", cbFile);
//
//		httppost.setEntity(mpEntity);
//
//		HttpResponse response = httpclient.execute(httppost);
//
//		HttpEntity resEntity = response.getEntity();
//
//		String reString = "";
//		if (resEntity != null) {
//
//			if (response.getStatusLine().getStatusCode() == 200) {
//				reString = EntityUtils.toString(response.getEntity(),
//						HTTP.UTF_8).trim();
//			}
//
//			resEntity.consumeContent();
//		}
//
//		httpclient.getConnectionManager().shutdown();
//
////		HashMap<String, Object> map = Encoder.getJsonValues(reString);
//
//		return map.get("ret").toString();
//	}
//
//	public static String sendUserFaceFile(String UserId, String Attachment,
//			String postUrl) throws Exception {
//		HttpClient httpclient = new DefaultHttpClient();
//		httpclient.getParams().setParameter(
//				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
//
//		postUrl = postUrl + "?UserId=" + UserId;
//		// Attachment = URLEncoder.encode(Attachment, "UTF-8");
//		// postUrl = postUrl + "&Attachment=" + Attachment;
//		HttpPost httppost = new HttpPost(postUrl);
//		File file = new File(Attachment);
//
//		MultipartEntity mpEntity = new MultipartEntity();
//		ContentBody cbFile = new FileBody(file, "application/octet-stream");
//		mpEntity.addPart("Attachment", cbFile);
//
//		httppost.setEntity(mpEntity);
//
//		HttpResponse response = httpclient.execute(httppost);
//
//		HttpEntity resEntity = response.getEntity();
//
//		String reString = "";
//		if (resEntity != null) {
//
//			if (response.getStatusLine().getStatusCode() == 200) {
//				reString = EntityUtils.toString(response.getEntity(),
//						HTTP.UTF_8).trim();
//			}
//
//			resEntity.consumeContent();
//		}
//
//		httpclient.getConnectionManager().shutdown();
//
////		HashMap<String, Object> map = Encoder.getJsonValues(reString);
//
//		return map.get("Status").toString();
//	}

}
