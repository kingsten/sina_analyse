package models;


import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import com.github.jmkgreen.morphia.annotations.PostLoad;
import com.github.jmkgreen.morphia.annotations.Transient;
import org.bson.types.ObjectId;
import util.CommonUtil;

import java.util.Date;

@Entity(value = "sina_original", noClassnameStored = true)
public class SinaOriginal {

    @Id
    private ObjectId id;

    private Date machineTime;

    @Transient
    private String sinaOriginalId;

    private String userId;

    private String poi;

    private Double longitude;


    private Double latitude;

    private String content;

    @Transient
    private Double poiData;

    @PostLoad
    public void postLoad() {
        Integer poiNumber = CommonUtil.getNumberOfChar(poi);
        poiData = Math.sqrt(poiNumber);
    }

    public ObjectId get_id() {
        return id;
    }

    public void setId(ObjectId _id) {
        this.id = _id;
    }

    public Date getMachineTime() {
        return machineTime;
    }

    public void setMachineTime(Date machineTime) {
        this.machineTime = machineTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPoi() {
        return poi;
    }

    public void setPoi(String poi) {
        this.poi = poi;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getPoiData() {
        return poiData;
    }

    public String getSinaOriginalId() {
        return sinaOriginalId;
    }
}
