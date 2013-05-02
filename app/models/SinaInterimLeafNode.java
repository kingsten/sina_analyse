package models;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lkq
 * Date: 13-4-23
 * Time: 上午10:44
 * To change this template use File | Settings | File Templates.
 */

@Entity(value = "sina_interim_leaf_node", noClassnameStored = true)
public class SinaInterimLeafNode {

    @Id
    private ObjectId leafNodeId;

    private int childrenSize;

    private List<String> clusterIdList;

    public SinaInterimLeafNode() {
        childrenSize = 0;
        clusterIdList = new ArrayList<String>();
        leafNodeId = new ObjectId();
    }

    public ObjectId getLeafNodeId() {
        return leafNodeId;
    }

    public int getChildrenSize() {
        return childrenSize;
    }

    public void setChildrenSize(int childrenSize) {
        this.childrenSize = childrenSize;
    }

    public List<String> getClusterIdList() {
        return clusterIdList;
    }

    public void setClusterIdList(List<String> clusterIdList) {
        this.clusterIdList = clusterIdList;
    }
}
