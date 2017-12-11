package org.apache.http.client;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.codec.binary.BinaryCodec;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.codec.Base64;
import org.hsqldb.types.Charset;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import com.google.common.primitives.Bytes;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class HttpClientTest {
	private final static Logger logger = LoggerFactory.getLogger(HttpClientTest.class);

	@Test
	public void testGetJSON() throws Exception {

		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			httpclient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet("http://192.168.201.112/simple/test");
			httpget.setHeader("content-type", "application/json;charset=UTF-8");
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity);

			Gson gson = new Gson();
			JsonParser parser = new JsonParser();
			JsonElement ele = parser.parse(result);
			JsonArray jsonArray = ele.getAsJsonArray();
			Iterator<JsonElement> it = jsonArray.iterator();
			while (it.hasNext()) {
				JsonElement e = it.next();
				Map<String, Object> map = gson.fromJson(e, new TypeToken<Map<String, Object>>() {
				}.getType());
				logger.info("row: {}", map);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (Exception e2) {
			}
		}
	}

	@Test
	public void testDateString() throws Exception {
		Date now = new Date(System.currentTimeMillis());
		// 11 Dec 2017 06:55:10 GMT
		// Tue, 05 May 2015 06:11:34 GMT
		// Mon Dec 11 14:53:48 CST 2017
		
		SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);  
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		System.out.println(format.format(now));
	}


	@Test
	public void testSign() throws Exception {
		String string_to_sign = "POST /openapi/index.php?method=newOrder\n"+
				"Mon, 11 Dec 2017 15:27:10 GMT";
				String client_secret = "65bd39b97db32f409adb50d0e2dccd49";
				
				String hmacSha1 = Hashing.hmacSha1(client_secret.getBytes()).hashString(string_to_sign, Charsets.UTF_8).toString();
				
				String signature = BaseEncoding.base64().encode(hmacSha1.getBytes(Charsets.UTF_8));
				
				logger.info("string_to_sign:{}",string_to_sign);
				logger.info("client_secret:{}",client_secret);
				logger.info("hmacSha1:{}",hmacSha1);
				logger.info("signature:{}",signature); // LH test:WymO6w180DUmp91DaYiHY6Tt0rA=

	}
	@Test
	public void testPOSTJSON() throws Exception {

		// 测试系统网址:http://test.lianhepiaowu.com
		// 测试账号信息：
		// parnterId：508
		// apiname：test
		// clientSecre：65bd39b97db32f409adb50d0e2dccd49

		
		
		
		Date now = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);  
		
		String date = format.format(now);

		String string_to_sign = "POST " + "/openapi/index.php?method=newOrder" + "\n" + date;
		String client_secret = "65bd39b97db32f409adb50d0e2dccd49";
		String hmac = Hashing.hmacSha1(client_secret.getBytes()).hashString(string_to_sign, Charsets.UTF_8).toString();
		hmac = new BigInteger(hmac, 16).toString(2);
		String signature = BaseEncoding.base64().encode(hmac.getBytes());
		String appname = "test";
		String authorization = "LH " + appname + ":" + signature;
		String partnerId = "508";
		String version = "2.1";
		

		logger.info("string_to_sign:{}",string_to_sign);
		logger.info("client_secret:{}",client_secret);
		logger.info("hmac:{}",hmac);
		logger.info("signature:{}",signature);
		logger.info("authorization:{}",authorization);
		logger.info("appname:{}",appname);
		logger.info("version:{}",version);
		logger.info("partnerId:{}",partnerId);
		

		// $string_to_sign = $method . ' ' . $uri . "\n" . $date;
		// $client_secret = self::$clientSecret;
		// $signature = base64_encode(hash_hmac('sha1', $string_to_sign,
		// $client_secret, true));
		// $authorization = 'LH ' . self::$apiName . ':' . $signature;

		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			httpclient = HttpClients.createDefault();
			HttpPost httpost = new HttpPost("http://test.lianhepiaowu.com/openapi/index.php?method=newOrder");
			httpost.setHeader("Content-Type", "application/json;charset=UTF-8");
			httpost.setHeader("Date", date);
			httpost.setHeader("Version", version);
			httpost.setHeader("PartnerId", partnerId);
			httpost.setHeader("Authorization", authorization);

			
			Gson g = new Gson();

			Header[] headers = httpost.getAllHeaders();
			for (Header header : headers) {
				logger.info("{}:{}",header.getName(),header.getValue());
			}
			
			
			Map<String,Object> data = Maps.newLinkedHashMap();
			data.put("partnerId", partnerId);
			
			Map<String,Object> body = Maps.newLinkedHashMap();
			
		    String orderId = "123"; //string",
		    String payId = "456"; //string",
		    String LHDealId = "789"; //string",
		    int sellPrice = 4; // 5,
		    int quantity = 2; // 3,
		    String travelDate = "2017-12-10";
		    		
		    body.put("orderId",orderId);
		    body.put("payId",payId);
		    body.put("LHDealId",LHDealId);
		    body.put("sellPrice",sellPrice);
		    body.put("quantity",quantity);
		    body.put("travelDate",travelDate);

			Map<String,Object> visitor = Maps.newLinkedHashMap();
			String name = "王二"; // 王二
			String mobile = "18888888888"; // 18888888888
			String address = "中国北京市胡说区侃大山街不靠谱小区6号楼001号"; // 中国北京市胡说区侃大山街不靠谱小区6号楼001号
			String zipcode = "100000"; // 100000
			String credentials = "";
			String email = "100000@163.com"; // 100000@163.com
			     
			visitor.put("name",name);
			visitor.put("mobile",mobile);
			visitor.put("address",address);
			visitor.put("zipcode",zipcode);
			visitor.put("credentials",credentials);
			visitor.put("email",email);
			
			body.put("visitor",visitor);
			
			data.put("body", body);
			
			
//			{
//				  "partnerId": 1985,
//				  "body": {
//				    "orderId": "string",
//				    "payId": "string",
//				    "LHDealId": "string",
//				    "sellPrice": 5,
//				    "quantity": 3,
//				    "travelDate":"2016-4-13",
//				    "visitor": [
//				    	{
//				        "name": "王二",
//				        "mobile": "18888888888",
//				        "address": "中国北京市胡说区侃大山街不靠谱小区6号楼001号",
//				        "zipcode": "100000",
//				        "credentials":"string",
//				        "email": "100000@163.com"
//				      }
//				    ]
//				  }
//				}
			
			String reqEntity = g.toJson(data);
			logger.info("{}",reqEntity);
			
			StringEntity s = new StringEntity(reqEntity);  
            s.setContentEncoding("UTF-8");  
            s.setContentType("application/json");  
            httpost.setEntity(s); 

			// $header = array(
			// "Content-Type: application/json; charset=utf-8",
			// "Date: " . $date,
			// "Version: " . self::$version,
			// "PartnerId: " . self::$partnerId,
			// "Authorization: " . $authorization,
			// );

			response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity);
			
			logger.info("{}",result);

			Gson gson = new Gson();
			JsonParser parser = new JsonParser();
			JsonElement ele = parser.parse(result);
			JsonArray jsonArray = ele.getAsJsonArray();
			Iterator<JsonElement> it = jsonArray.iterator();
			while (it.hasNext()) {
				JsonElement e = it.next();
				Map<String, Object> map = gson.fromJson(e, new TypeToken<Map<String, Object>>() {
				}.getType());
				logger.info("row: {}", map);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (Exception e2) {
			}
		}
	}

}
