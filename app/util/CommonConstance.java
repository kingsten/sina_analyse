package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: LKQ
 * Date: 13-3-15
 * Time: 下午1:06
 * To change this template use File | Settings | File Templates.
 */
public class CommonConstance {

    public static final int pointsNum = 3;  //计算使用的坐标点数
    public static final double clusterDiameter = 25.8;  //簇直径
    public static final int childrenNumbers = 1000;  //最大的孩子数
    public static final double similarity = 0.5;
    public static final String cutWordModel = "simple";

    public static Set<String> paperTerritorySet;

    static {
        File paperTerritoryFile = new File(CommonUtil.getConfigureByKey("data.dir") + "/paperTerritory.dat");
        paperTerritorySet = new HashSet<String>();
        try {
            FileReader fileReader = new FileReader(paperTerritoryFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String paperTerritory = null;
            while ((paperTerritory = bufferedReader.readLine()) != null) {
                paperTerritorySet.add(paperTerritory);
            }

            bufferedReader.close();
            fileReader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }
}
