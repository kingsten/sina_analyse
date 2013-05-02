package service;

import com.mongodb.DBCollection;
import models.SinaOriginal;
import util.MongoDbUtil;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lkq
 * Date: 13-4-24
 * Time: 下午3:47
 * To change this template use File | Settings | File Templates.
 */
public class DataService {

    public static List<String> getAllUserId() {
        DBCollection dbCollection = MongoDbUtil.getDatastore().getCollection(SinaOriginal.class);
        List<String> userIdList = dbCollection.distinct("userId");
        return userIdList;
    }

    public static String getUserAllContentByUserId(String userId) {
        List<SinaOriginal> sinaOriginalList = MongoDbUtil.getDatastore().find(SinaOriginal.class).filter("userId", userId).asList();
        StringBuilder stringBuilder = new StringBuilder();
        for (SinaOriginal sinaOriginal : sinaOriginalList) {
            stringBuilder.append(sinaOriginal.getContent()).append(" ");
        }
        return stringBuilder.toString();

    }




}
