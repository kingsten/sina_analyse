package models.vo;

import util.CommonConstance;
import util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: LKQ
 * Date: 13-3-15
 * Time: 下午1:03
 * To change this template use File | Settings | File Templates.
 */
public class ClusterFeature {

    private int N;
    private double[] LS;
    private double[] SS;
    private List<String> contentList;


    public ClusterFeature() {
        LS = new double[CommonConstance.pointsNum];
        SS = new double[CommonConstance.pointsNum];
        contentList = new ArrayList<String>();
    }

    //根据一个data 的数据点坐标创建一个Cluster Feature
    public ClusterFeature(double data[], String content) {
        int length = data.length;
        this.N = 1;
        this.LS = data;
        this.SS = new double[length];
        for (int i = 0; i < length; i++) {
            this.SS[i] = Math.pow(data[i], 2);
        }
        contentList = new ArrayList<String>();
        contentList.add(content);
    }

    //复制构造函数(深度复制)
    public ClusterFeature(ClusterFeature clusterFeature) {
        this.N = clusterFeature.getN();
        int length = clusterFeature.getLS().length;
        this.contentList = new ArrayList<String>();
        this.LS = new double[length];
        this.SS = new double[length];
        for (int i = 0; i < length; i++) {
            this.LS[i] = clusterFeature.getLS()[i];
            this.SS[i] = clusterFeature.getSS()[i];
        }
        for (String content : clusterFeature.getContentList()) {
            this.contentList.add(content);
        }
    }

    //使用D2计算两个CF之间的距离,D2减去上字符内容的编辑距离的方差
    public double getDistanceTo(ClusterFeature entry) {
        double distance = 0.0;
        int length = this.LS.length;

        //采用D2
        for (int i = 0; i < length; i++) {
            distance += this.SS[i] / this.N + entry.getSS()[i] / entry.getN() - 2 * this.LS[i] * entry.getLS()[i] / (this.N * entry.getN());
        }

        //计算字符串之间的编辑距离
        List<Double> editDistanceList = new ArrayList<Double>();

        for (String content1 : this.contentList) {
            for (String content2 : entry.getContentList()) {
                Double editDistance = CommonUtil.getSimilarity(content1, content2);
                editDistanceList.add(editDistance);
            }
        }

        double variance = CommonUtil.getStanderVariance(editDistanceList);

        double result = Math.sqrt(distance) - variance;

        return result < 0 ? 0 : result;
    }

    //加上或者减去一个CF的值
    public void addCF(ClusterFeature entry, boolean add) {
        int opt = 1;
        if (!add) {
            opt = -1;
            this.contentList.removeAll(entry.getContentList());
        } else {
            this.contentList.addAll(entry.getContentList());
        }

        this.N = this.N + entry.getN() * opt;
        int length = this.LS.length;
        for (int i = 0; i < length; i++) {
            this.LS[i] = this.LS[i] + entry.getLS()[i] * opt;
            this.SS[i] = this.SS[i] + entry.getSS()[i] * opt;
        }

    }

    public static double calEuraDist(double[] array1, double[] array2, int length) {
        double result = 0.0;
        for (int i = 0; i < length; i++) {
            result += Math.pow(array1[i] - array2[i], 2.0);
        }
        return Math.sqrt(result);
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public double[] getLS() {
        return LS;
    }

    public void setLS(double[] LS) {
        this.LS = LS;
    }

    public double[] getSS() {
        return SS;
    }

    public void setSS(double[] SS) {
        this.SS = SS;
    }

    public List<String> getContentList() {
        return contentList;
    }

    public void setContentList(List<String> contentList) {
        this.contentList = contentList;
    }
}
