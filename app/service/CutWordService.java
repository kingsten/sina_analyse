package service;

import com.chenlb.mmseg4j.*;
import com.mongodb.DBCollection;
import models.SinaOriginal;
import models.vo.WordsTable;
import org.apache.commons.collections.MapUtils;
import util.CommonUtil;
import util.MongoDbUtil;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lkq
 * Date: 13-4-23
 * Time: 下午4:57
 * To change this template use File | Settings | File Templates.
 */
public class CutWordService {

    public static CutWordService cutWordService = null;
    MMSeg mmSeg = null;
    Seg seg = null;

    private CutWordService(String mode) {
        Dictionary dic = Dictionary.getInstance(CommonUtil.getConfigureByKey("data.dir") + "/wordsNew.dic");
        if ("simple".equals(mode)) {
            seg = new SimpleSeg(dic);
        } else {
            seg = new ComplexSeg(dic);
        }
    }

    public static CutWordService getInstance(String mode) {
        if (cutWordService == null) {
            cutWordService = new CutWordService(mode);
        }
        return cutWordService;
    }

    public String segStr(String text) {
        String returnStr = "";
//        Seg seg = null;
//        Dictionary dic = Dictionary.getInstance(CommonUtil.getConfigureByKey("data.dir") + "/wordsNew.dic");
//        if ("simple".equals(mode)) {
//            seg = new SimpleSeg(dic);
//        } else {
//            seg = new ComplexSeg(dic);
//        }
        List<String> stopWordList = WordsTable.getInstance().getStopWordList();
        StringReader reader = new StringReader(text);
        MMSeg mmSeg = new MMSeg(reader, seg);
        Word word = null;
        try {
            while ((word = mmSeg.next()) != null) {
                if (stopWordList.contains(word.getString())) {
                    continue;
                }
                returnStr += word.getString() + " ";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

    public Map<Integer, Integer> wordIdCount(String[] wordArray) {
        Map<Integer, Integer> wordIdCountMap = new HashMap<Integer, Integer>();
        WordsTable wordsTable = WordsTable.getInstance();
        for (String word : wordArray) {
            Integer wordIndex = wordsTable.getWordsList().indexOf(word);
            if (wordIndex == -1) {
                continue;
            }
            Integer count = MapUtils.getInteger(wordIdCountMap, wordIndex);
            if (count == null) {
                count = 1;
            } else {
                ++count;
            }
            wordIdCountMap.put(wordIndex, count);
        }
        return wordIdCountMap;
    }


}
