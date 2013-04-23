package models.vo;

import com.google.code.morphia.Datastore;
import com.mongodb.MongoURI;
import models.SinaInterimCluster;
import models.SinaInterimLeafNode;
import models.SinaOriginal;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import util.CommonConstance;
import util.MongoDbUtil;

import java.io.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: LKQ
 * Date: 13-3-16
 * Time: 上午11:37
 * To change this template use File | Settings | File Templates.
 */
public class BIRCH {

    LeafNode leafNodeHead = new LeafNode();
    int point_num = 0;


    //逐条扫描数据库，建立B-树
    public TreeNode buildBTree() {

        //先建立一个叶子节点
        LeafNode leaf = new LeafNode();
        TreeNode root = leaf;

        //把叶子节点加入到存储的叶子节点的双向链表
        leafNodeHead.setNext(leaf);
        leaf.setPre(leafNodeHead);

        try {
            Datastore ds = MongoDbUtil.getDatastore();
            Long total = ds.find(SinaOriginal.class).countAll();
            Long totalPage = total / 300 + 1;
            for (Long indexPage = 0l; indexPage < totalPage; indexPage++) {
                List<SinaOriginal> sinaOriginalList = ds.find(SinaOriginal.class).offset(indexPage.intValue() * 300).limit(300).asList();
                for (SinaOriginal sinaOriginal : sinaOriginalList) {
                    //读入point instance
                    point_num++;

                    double[] data = new double[CommonConstance.pointsNum];
                    data[0] = sinaOriginal.getPoiData();
                    data[1] = sinaOriginal.getLongitude();
                    data[2] = sinaOriginal.getLatitude();
//                    String mark = point_num + "";
                    String mark = sinaOriginal.get_id().toString();

                    //根据一个point instance创建一个MinCluster
                    ClusterFeature clusterFeature = new ClusterFeature(data, sinaOriginal.getContent());
                    MinCluster subCluster = new MinCluster();
                    subCluster.setClusterFeature(clusterFeature);
                    subCluster.getInst_marks().add(mark);

                    //把新的point instance 插入到树中
                    root.absorbSubCluster(subCluster);

                    //保证root是树的根节点
                    while (root.getParent() != null) {
                        root = root.getParent();
                    }
                }
            }
        } catch (Exception e) {
            Logger logger = Logger.getLogger(BIRCH.class);
            logger.error(e.getMessage(), e);
            System.out.println("error==============>" + e.getMessage());

        }
        return root;

    }

    public void printLeaf(LeafNode header) {
        //point_num清0
        while (header.getNext() != null) {
            System.out.println("\n一个叶子节点:");
            header = header.getNext();
            for (MinCluster cluster : header.getChildrenList()) {
                System.out.println("\n一个最小簇:");
                for (String mark : cluster.getInst_marks()) {
                    System.out.print(mark + "\t");
                }
            }
        }
    }

    public void printTree(TreeNode root) {
        if (!root.getClass().getName().equals("models.vo.LeafNode")) {
            System.out.println(root.getClass().getName());
            NonLeafNode nonLeaf = (NonLeafNode) root;
            for (TreeNode child : nonLeaf.getChildrenList()) {
                printTree(child);
            }
        } else {
            System.out.println("\n一个叶子节点:");
            LeafNode leaf = (LeafNode) root;
            for (MinCluster cluster : leaf.getChildrenList()) {
                System.out.println("\n一个最小簇:");
                for (String mark : cluster.getInst_marks()) {
                    System.out.print(mark + "\t");
                }
            }
        }
    }


    public void storeTree(TreeNode root) {
        if (!root.getClass().getName().equals("models.vo.LeafNode")) {
            System.out.println(root.getClass().getName());
            NonLeafNode nonLeaf = (NonLeafNode) root;
            System.out.println("size===============>" + nonLeaf.getChildrenList().size());
            for (TreeNode child : nonLeaf.getChildrenList()) {
                storeTree(child);
            }
        } else {
//            System.out.println("\n一个叶子节点:");
//            LeafNode leaf = (LeafNode) root;
//            for (MinCluster cluster : leaf.getChildrenList()) {
//                System.out.println("\n一个最小簇:");
//                for (String mark : cluster.getInst_marks()) {
//                    System.out.print(mark + "\t");
//                }
//            }
            //存储一个簇的内容，加上一个节点进行标记
            LeafNode leafNode = (LeafNode) root;
            SinaInterimLeafNode sinaInterimLeafNode = new SinaInterimLeafNode();
            for (MinCluster cluster : leafNode.getChildrenList()) {
                SinaInterimCluster sinaInterimCluster = new SinaInterimCluster();
                sinaInterimCluster.setLeafNodeId(sinaInterimLeafNode.getLeafNodeId());
                sinaInterimCluster.setClusterSize(cluster.getInst_marks().size());
                sinaInterimCluster.setOriginalDataIdList(cluster.getInst_marks());
                MongoDbUtil.getDatastore().save(sinaInterimCluster);
                System.out.println("id============>" + sinaInterimCluster.getClusterId().toString());
                sinaInterimLeafNode.getClusterIdList().add(sinaInterimCluster.getClusterId().toString());
            }
            System.out.println("size of clust===================>" + sinaInterimLeafNode.getClusterIdList().size());
            MongoDbUtil.getDatastore().save(sinaInterimLeafNode);
        }
    }

//    public List<String> gpaodingetTreeNodeId(TreeNode root){
//        if(!root.getClass().getName().equals("birch.LeafNode")){
//            NonLeafNode nonLeaf=(NonLeafNode)root;
//            for(TreeNode child:nonLeaf.getChildrenList()){
//                printTree(child);
//            }
//        }
//        else{
//            System.out.println("\n一个叶子节点:");
//            LeafNode leaf=(LeafNode)root;
//            for(MinCluster cluster:leaf.getChildrenList()){
//                System.out.println("\n一个最小簇:");
//                for(String mark:cluster.getInst_marks()){
//                    System.out.print(mark+"\t");
//                }
//            }
//        }
//    }
}

