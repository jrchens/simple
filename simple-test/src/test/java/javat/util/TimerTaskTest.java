package javat.util;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class TimerTaskTest {
    private final static Logger logger = LoggerFactory.getLogger(TimerTaskTest.class);
public static void main(String[] args) {
    
//}
//    @Test
//    public void testTimerTask() throws Exception {
	Timer timer = new Timer();
	timer.schedule(new TimerTask() {
	    @Override
	    public void run() {
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
		    httpclient = HttpClients.createDefault();
		    
		    Random random = new Random();
		    int limit = random.nextInt(11);
		    BasicNameValuePair limitAttr = new BasicNameValuePair("limit",String.valueOf(limit));
		    String params = EntityUtils.toString(new UrlEncodedFormEntity(Lists.newArrayList(limitAttr)));
		    
		    HttpGet httpget = new HttpGet("http://192.168.201.112/simple/test?"+params);
		    httpget.setHeader("content-type", "application/json;charset=UTF-8");
		    response = httpclient.execute(httpget);
		    HttpEntity entity = response.getEntity();
		    String result = EntityUtils.toString(entity);
		    logger.info("limit: {}",limit);
		    logger.info("result: {}",result);

//		    Gson gson = new Gson();
//		    JsonParser parser = new JsonParser();
//		    JsonElement ele = parser.parse(result);
//		    JsonArray jsonArray = ele.getAsJsonArray();
//		    Iterator<JsonElement> it = jsonArray.iterator();
//		    while (it.hasNext()) {
//			JsonElement e = it.next();
//			Map<String, Object> map = gson.fromJson(e, new TypeToken<Map<String, Object>>() {
//			}.getType());
//			logger.info("row: {}", map);
//		    }
		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
		    try {
			response.close();
			httpclient.close();
		    } catch (Exception e2) {
		    }
		}
		
	    }

	}, 1000, 120000);
    }

}
