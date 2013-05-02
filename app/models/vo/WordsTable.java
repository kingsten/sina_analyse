package models.vo;

import util.CommonConstance;
import util.CommonUtil;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: lkq
 * Date: 13-4-24
 * Time: 下午4:09
 * To change this template use File | Settings | File Templates.
 */
public class WordsTable {

    private List<String> wordsList;

    private List<Map<Integer, Integer>> wordCountMatrix;

    private List<String> paperTerritoryList;

    private static WordsTable wordsTable;

    private WordsTable() {

        initWordList();
        initWordsFrequency();
        initPaperTerritory();


    }

    private void initPaperTerritory() {
        File paperTerritoryFile = new File(CommonUtil.getConfigureByKey("data.dir") + "/paperTerritory");
        paperTerritoryList = new ArrayList<String>();
        try {
            FileReader fileReader = new FileReader(paperTerritoryFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String paperTerritory = null;
            while ((paperTerritory = bufferedReader.readLine()) != null) {
                paperTerritoryList.add(paperTerritory);
            }
            bufferedReader.close();
            fileReader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    private void initWordList() {
        File file = new File(CommonUtil.getConfigureByKey("data.dir") + "/wordsNew.dic");
        wordsList = new ArrayList<String>();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                wordsList.add(line);
            }
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void initWordsFrequency() {
        File wordCountFile = new File(CommonUtil.getConfigureByKey("data.dir") + "/WordCountMatrix.dat");
        wordCountMatrix = new ArrayList<Map<Integer, Integer>>();
        try {
            FileReader wordCountFileReader = new FileReader(wordCountFile);
            BufferedReader wordCountFileBufferReader = new BufferedReader(wordCountFileReader);
            String wordCountLine = null;
            while ((wordCountLine = wordCountFileBufferReader.readLine()) != null) {
                String[] wordCountArray = wordCountLine.split(" ");
                int wordLine = 0;
                int wordFrequency = 0;
                Map<Integer, Integer> wordIdCountMap = new HashMap<Integer, Integer>();
                for (int i = 0; i < wordCountArray.length; i++) {
                    if (i % 2 == 0) {
                        wordLine = Integer.parseInt(wordCountArray[i]);
                    } else {
                        wordFrequency = Integer.parseInt(wordCountArray[i]);
                        wordIdCountMap.put(wordLine, wordFrequency);
                    }

                }
                wordCountMatrix.add(wordIdCountMap);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static WordsTable getInstance() {
        if (wordsTable == null) {
            wordsTable = new WordsTable();
        }
        return wordsTable;
    }

    public List<String> getWordsList() {
        return wordsList;
    }

    public List<Map<Integer, Integer>> getWordCountMatrix() {
        return wordCountMatrix;
    }


    public List<Map.Entry<Integer, Double>> getDistanceList(Map<Integer, Integer> sourceWordMap) {
        Map<Integer, Double> paperSimilarityMap = new HashMap<Integer, Double>();
        for (int i = 0; i < wordCountMatrix.size(); i++) {
            Double similarity = CommonUtil.getSimilarity(sourceWordMap, wordCountMatrix.get(i));
            if (similarity > CommonConstance.similarity) {
                paperSimilarityMap.put(i, similarity);
            }

        }
        List<Map.Entry<Integer, Double>> distanceList = new ArrayList<Map.Entry<Integer, Double>>(paperSimilarityMap.entrySet());
        Collections.sort(distanceList, new Comparator<Map.Entry<Integer, Double>>() {
            @Override
            public int compare(Map.Entry<Integer, Double> mapping1, Map.Entry<Integer, Double> mapping2) {
                return mapping1.getValue().compareTo(mapping2.getValue());
            }
        });
        return distanceList;
    }

    //添加对应的对比代码，在已计算的 最近N个 文本中，对其类别进行计算统计，在把数量按照降序排序，选取合适的阈值进行打上标签
}
