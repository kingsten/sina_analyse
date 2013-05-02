package util;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.Morphia;
import com.mongodb.Mongo;

import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: lkq
 * Date: 13-3-18
 * Time: 下午8:34
 * To change this template use File | Settings | File Templates.
 */
public class MongoDbUtil {

    private static Morphia morphia;
    private static Mongo mongo;
    private static Datastore datastore;

    static {
        try {
            mongo = new Mongo(CommonUtil.getConfigureByKey("morphia.db.url"), Integer.parseInt(CommonUtil.getConfigureByKey("morphia.db.port")));
        } catch (UnknownHostException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        morphia = new Morphia();
        morphia.mapPackage("models", true);
        datastore = morphia.createDatastore(mongo, CommonUtil.getConfigureByKey("morphia.db.name"));
    }


    public static Morphia getMorphia() {
        return morphia;
    }

    public static Mongo getMongo() {
        return mongo;
    }

    public static Datastore getDatastore() {
        return datastore;
    }
}
