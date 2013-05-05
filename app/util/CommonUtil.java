package util;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import play.Play;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: LKQ
 * Date: 13-3-13
 * Time: 下午3:58
 * To change this template use File | Settings | File Templates.
 */
public class CommonUtil {

    public static String getConfigureByKey(String property) {
        return Play.configuration.getProperty(property);
    }

    public static double getSimilarity(String doc1, String doc2) {
        if (StringUtils.isNotEmpty(doc1) && StringUtils.isNotEmpty(doc2)) {
            Map<Integer, int[]> algroithMap = new HashMap<Integer, int[]>();

            for (int i = 0; i < doc1.length(); i++) {
                char d1 = doc1.charAt(i);
                if (isChinese(d1)) {
                    int charIndex = getGB2312Id(d1);
                    if (charIndex != -1) {
                        int[] array = (int[]) MapUtils.getObject(algroithMap, charIndex);
                        if (array != null && array.length == 2) {
                            array[0]++;
                        } else {
                            array = new int[2];
                            array[0] = 1;
                            array[1] = 0;
                            algroithMap.put(charIndex, array);
                        }
                    }
                }
            }

            for (int i = 0; i < doc2.length(); i++) {
                char d2 = doc2.charAt(i);
                if (isChinese(d2)) {
                    int charIndex = getGB2312Id(d2);
                    if (charIndex != -1) {
                        int[] array = (int[]) MapUtils.getObject(algroithMap, charIndex);
                        if (array != null && array.length == 2) {
                            array[1]++;
                        } else {
                            array = new int[2];
                            array[0] = 0;
                            array[1] = 1;
                            algroithMap.put(charIndex, array);
                        }
                    }
                }
            }

            double sqdoc1 = 0;
            double sqdoc2 = 0;
            double denominator = 0;
            for (Map.Entry<Integer, int[]> entry : algroithMap.entrySet()) {
                int[] c = entry.getValue();
                denominator += c[0] * c[1];
                sqdoc1 += c[0] * c[0];
                sqdoc2 += c[1] * c[1];
            }
            return denominator / Math.sqrt(sqdoc1 * sqdoc2);
        } else {
            return 0;
        }
    }

    public static Double getSimilarity(Map<Integer, Integer> wordMap_1, Map<Integer, Integer> wordMap_2) {
        Map<Integer, int[]> vectorSpace = new HashMap<Integer, int[]>();
        int[] itemCountArray = null;
//        System.out.println("wordMap_1=============>" + wordMap_1.size());
//        System.out.println("wordMap_2=============>" + wordMap_2.size());
        for (Map.Entry<Integer, Integer> entry : wordMap_1.entrySet()) {
            if (vectorSpace.containsKey(entry.getKey())) {
                (vectorSpace.get(entry.getKey())[0]) += entry.getValue();
            } else {
                itemCountArray = new int[2];
                itemCountArray[0] = entry.getValue();
                itemCountArray[1] = 0;
                vectorSpace.put(entry.getKey(), itemCountArray);
            }

        }

        for (Map.Entry<Integer, Integer> entry : wordMap_2.entrySet()) {
            if (vectorSpace.containsKey(entry.getKey())) {
                (vectorSpace.get(entry.getKey())[1]) += entry.getValue();
            } else {
                itemCountArray = new int[2];
                itemCountArray[0] = 0;
                itemCountArray[1] = entry.getValue();
                vectorSpace.put(entry.getKey(), itemCountArray);
            }
        }

        double vector1Modulo = 0.00;//向量1的模
        double vector2Modulo = 0.00;//向量2的模
        double vectorProduct = 0.00; //向量积

        for (Map.Entry<Integer, int[]> entry : vectorSpace.entrySet()) {
            itemCountArray = entry.getValue();

            vector1Modulo += itemCountArray[0] * itemCountArray[0];
            vector2Modulo += itemCountArray[1] * itemCountArray[1];

            vectorProduct += itemCountArray[0] * itemCountArray[1];
        }

        vector1Modulo = Math.sqrt(vector1Modulo);
        vector2Modulo = Math.sqrt(vector2Modulo);

        //返回相似度
        return (vectorProduct / (vector1Modulo * vector2Modulo));

    }


    public static boolean isChinese(char c) {
        return (c >= 0x4E00 && c <= 0x9FA5);
    }


    public static int getGB2312Id(char c) {
        try {
            byte[] buffer = Character.toString(c).getBytes("GB2312");
            if (buffer == null || buffer.length != 2) {
                return -1;
            }
            int b0 = (int) (buffer[0] & 0x0FF) - 161;
            int b1 = (int) (buffer[1] & 0x0FF) - 161;

            return (b0 * 94 + b1);
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public static double getStanderVariance(List<Double> dataList) {
        if (dataList == null || dataList.size() == 0)
            return 0;
        double sum = 0.0;
        for (Double data : dataList) {
            sum += data;
        }
        double average = sum / dataList.size();
        double result = 0.0;
        for (Double data : dataList) {
            result += Math.pow(data - average, 2);
        }
        return Math.sqrt(result);

    }

    public static Integer getNumberOfChar(String originStr) {
        if (StringUtils.isEmpty(originStr)) {
            return 0;
        }

        Integer result = 0;

        for (int index = 0; index < originStr.length(); index++) {
            result += originStr.charAt(index);
        }

        return result;
    }

}
