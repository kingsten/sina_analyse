package models.vo;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: LKQ
 * Date: 13-3-15
 * Time: 下午4:26
 * To change this template use File | Settings | File Templates.
 */
public class MinCluster {
    private ClusterFeature clusterFeature;
    private ArrayList<String> inst_marks;

    public MinCluster() {
        clusterFeature = new ClusterFeature();
        inst_marks = new ArrayList<String>();
    }

    public ClusterFeature getClusterFeature() {
        return clusterFeature;
    }

    public void setClusterFeature(ClusterFeature clusterFeature) {
        this.clusterFeature = clusterFeature;
    }

    public ArrayList<String> getInst_marks() {
        return inst_marks;
    }

    public void setInst_marks(ArrayList<String> inst_marks) {
        this.inst_marks = inst_marks;
    }

    //计算簇直径
    public static double getDiameter(ClusterFeature clusterFeature) {
        double diameter = 0.0;
        int n = clusterFeature.getN();
        for (int i = 0; i < clusterFeature.getLS().length; i++) {
            double ls = clusterFeature.getLS()[i];
            double ss = clusterFeature.getSS()[i];
            diameter = diameter + 2 * n * ss - 2 * ls * ls;
        }
        diameter = diameter / (n * n - n);
        return Math.sqrt(diameter);
    }

    //计算两个簇合并后的簇直径
    public static double getDiameter(MinCluster cluster1, MinCluster cluster2) {
        ClusterFeature clusterFeature = new ClusterFeature(cluster1.getClusterFeature());
        clusterFeature.addCF(cluster2.getClusterFeature(), true);
        return getDiameter(clusterFeature);
    }

    //合并两个最小簇
    public void mergeCluster(MinCluster cluster) {
        this.getClusterFeature().addCF(cluster.getClusterFeature(), true);
        this.getInst_marks().addAll(cluster.getInst_marks());
    }


}
