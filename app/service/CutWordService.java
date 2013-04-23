package service;

import com.chenlb.mmseg4j.*;
import org.apache.commons.collections.MapUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lkq
 * Date: 13-4-23
 * Time: 下午4:57
 * To change this template use File | Settings | File Templates.
 */
public class CutWordService {

    private String segStr(String text, String mode) throws IOException {
        String returnStr = "";
        Seg seg = null;
        Dictionary dic = Dictionary.getInstance();
        if ("simple".equals(mode)) {
            seg = new SimpleSeg(dic);
        } else {
            seg = new ComplexSeg(dic);
        }

        // String words = seg.
        StringReader reader = new StringReader(text);
//        MMSeg mmSeg = new MMSeg(new InputStreamReader(new ByteArrayInputStream(text.getBytes())), seg);
        MMSeg mmSeg = new MMSeg(reader, seg);
        Word word = null;
        while ((word = mmSeg.next()) != null) {
            returnStr += word.getString() + " ";
        }

        return returnStr;
    }

    public Map<String, Integer> wordCount(String[] wordArray) {
        Map<String, Integer> wordCountMap = new HashMap<String, Integer>();
        for (String word : wordArray) {
            Integer count = MapUtils.getInteger(wordCountMap, word);
            if (count == null) {
                count = 1;
            } else {
                count++;
            }

            wordCountMap.put(word, count);
        }
        return wordCountMap;
    }
}
