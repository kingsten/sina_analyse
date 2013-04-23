package models;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import org.bson.types.ObjectId;

/**
 * Created with IntelliJ IDEA.
 * User: lkq
 * Date: 13-3-26
 * Time: 上午11:02
 * To change this template use File | Settings | File Templates.
 */

@Entity(value = "sina_interim", noClassnameStored = true)
public class SinaInterim {

    @Id
    private ObjectId id;

    private ObjectId sinaOriginalId;

}
