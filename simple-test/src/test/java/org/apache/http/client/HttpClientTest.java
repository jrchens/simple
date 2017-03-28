package org.apache.http.client;

import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	    httpget.setHeader("content-type","application/json;charset=UTF-8");
	    response = httpclient.execute(httpget);
	    HttpEntity entity = response.getEntity();
	    String result = EntityUtils.toString(entity);
	    
	    Gson gson = new Gson();
	    JsonParser parser = new JsonParser();
	    JsonElement ele = parser.parse(result);
	    JsonArray jsonArray = ele.getAsJsonArray();
	    Iterator<JsonElement> it = jsonArray.iterator();
	    while(it.hasNext()){
	        JsonElement e = it.next();
	        Map<String,Object> map = gson.fromJson(e, new TypeToken<Map<String,Object>>(){}.getType());
	        logger.info("row: {}",map);
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
