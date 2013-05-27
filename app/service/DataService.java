package service;

import com.mongodb.DBCollection;
import models.SinaOriginal;
import models.SinaUserTag;
import models.vo.WordsTable;
import org.apache.commons.lang.StringUtils;
import sun.swing.StringUIClientPropertyKey;
import util.CommonConstance;
import util.MongoDbUtil;

import java.util.List;
import java.util.regex.Pattern;

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
            stringBuilder.append(sinaOriginal.getContent());
        }
        return stringBuilder.toString();

    }

    public static List<SinaUserTag> getUsersByUserId(String userId, Integer curPage, Integer perPage) {
        List<SinaUserTag> sinaUserTagList = MongoDbUtil.getDatastore().find(SinaUserTag.class).filter("userId", Pattern.compile(".*" + userId + ".*",Pattern.CASE_INSENSITIVE)).offset((curPage - 1) * perPage).limit(perPage).asList();
        return sinaUserTagList;
    }

    public static List<SinaUserTag> getUserListByTag(String tag, Integer curPage, Integer perPage) {
        List<SinaUserTag> sinaUserTagList = MongoDbUtil.getDatastore().find(SinaUserTag.class).filter("userTagStr", Pattern.compile(".*" + tag + ".*",Pattern.CASE_INSENSITIVE)).offset((curPage - 1) * perPage).limit(perPage).asList();
        return sinaUserTagList;
    }



}
