package com.google.common.collect;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListsTest {
    private final static Logger logger = LoggerFactory.getLogger(ListsTest.class);

    @Test
    public void testAsList() throws Exception {
	List<String> base = Lists.newArrayList();
	String[] arr = {"123,456","789","a,,b,c"};
	
	logger.info("data: {}",Arrays.toString(Lists.asList(base, arr).toArray()));;
	
    }
    
    
    @Test
    public void testNewArrayList() throws Exception {
	String[] arr = {"123,456","789","a,,b,c"};
	logger.info("data: {}",Lists.newArrayList(arr));
    }
}
