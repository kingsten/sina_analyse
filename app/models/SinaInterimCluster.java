package models;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lkq
 * Date: 13-4-23
 * Time: 上午10:50
 * To change this template use File | Settings | File Templates.
 */
@Entity(value = "sina_interim_cluster", noClassnameStored = true)
public class SinaInterimCluster {

    @Id
    private ObjectId clusterId;

    private ObjectId leafNodeId;

    private int clusterSize;

    private List<String> originalDataIdList;

    public ObjectId getClusterId() {
        return clusterId;
    }

    public ObjectId getLeafNodeId() {
        return leafNodeId;
    }

    public void setLeafNodeId(ObjectId leafNodeId) {
        this.leafNodeId = leafNodeId;
    }

    public int getClusterSize() {
        return clusterSize;
    }

    public void setClusterSize(int clusterSize) {
        this.clusterSize = clusterSize;
    }

    public List<String> getOriginalDataIdList() {
        return originalDataIdList;
    }

    public void setOriginalDataIdList(List<String> originalDataIdList) {
        this.originalDataIdList = originalDataIdList;
    }
}
