package service;

import com.mongodb.MongoURI;
import models.SinaOriginal;
import org.apache.log4j.Logger;
import util.MongoDbUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: LKQ Date: 13-3-13 Time: 下午3:23 To change
 * this template use File | Settings | File Templates.
 */
public class ImportDataService extends Thread {

    static Logger logger = Logger.getLogger(ImportDataService.class);

    public static void saveSinaOriginalDate(final File originalDataFile) {

        BufferedReader reader = null;
        try {
            int number = 0;
            reader = new BufferedReader(new FileReader(originalDataFile));
            String tempString = null;
            List<SinaOriginal> sinaOriginalList = new ArrayList<SinaOriginal>();
            while ((tempString = reader.readLine()) != null) {
                // todo 处理每行的数据
                String[] items = tempString.split("\\{@\\}");
                SinaOriginal sinaOriginal = new SinaOriginal();
                if (items == null || items.length < 6)
                    continue;
                System.out.println("before Save " + items[0]);
                sinaOriginal.setMachineTime(new Date(Long.parseLong(items[0])));
                sinaOriginal.setUserId(items[1]);
                sinaOriginal.setPoi(items[2]);
                sinaOriginal.setLongitude(Double.parseDouble(items[3]));
                sinaOriginal.setLatitude(Double.parseDouble(items[4]));
                sinaOriginal.setContent(items[5]);
                MongoDbUtil.getDatastore().save(sinaOriginal);
                number++;
                if (number > 10000) {
                    break;
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public static void initPoI() {
        try{
            File file = new File("");

           BufferedReader bufferedReader = null;



        }   catch (Exception e){
            logger.error(e.getMessage());
        }
    }


}
