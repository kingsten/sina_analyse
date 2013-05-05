package models;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: LKQ
 * Date: 13-5-5
 * Time: 下午6:40
 * To change this template use File | Settings | File Templates.
 */
@Entity(value = "sina_usertag")
public class SinaUserTag {

    @Id
    private ObjectId id;

    private String userId;

    private List<String> userTagList;

    public ObjectId getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getUserTagList() {
        return userTagList;
    }

    public void setUserTagList(List<String> userTagList) {
        this.userTagList = userTagList;
    }
}
