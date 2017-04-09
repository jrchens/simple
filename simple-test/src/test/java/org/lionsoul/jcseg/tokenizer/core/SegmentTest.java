package org.lionsoul.jcseg.tokenizer.core;

import java.io.StringReader;

import org.junit.Test;
import org.lionsoul.jcseg.tokenizer.core.ADictionary;
import org.lionsoul.jcseg.tokenizer.core.DictionaryFactory;
import org.lionsoul.jcseg.tokenizer.core.ISegment;
import org.lionsoul.jcseg.tokenizer.core.IWord;
import org.lionsoul.jcseg.tokenizer.core.JcsegTaskConfig;
import org.lionsoul.jcseg.tokenizer.core.SegmentFactory;

public class SegmentTest {

    @Test
    public void testStandardQueryParser() throws Exception {
	JcsegTaskConfig tokenizerConfig = new JcsegTaskConfig(true);
	ADictionary dic = DictionaryFactory.createSingletonDictionary(tokenizerConfig);
	ISegment tokenizerSeg = SegmentFactory.createJcseg(JcsegTaskConfig.COMPLEX_MODE,
		new Object[] { tokenizerConfig, dic });

	String str = "江苏句容圣通华大天益电力科技有限公司";
	tokenizerSeg.reset(new StringReader(str));
	IWord word = null;

	while ((word = tokenizerSeg.next()) != null) {
	    System.out.println(word.getValue());
	}
	
    }
}
