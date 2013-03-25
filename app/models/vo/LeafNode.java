package models.vo;

import util.CommonConstance;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: LKQ
 * Date: 13-3-15
 * Time: 下午5:03
 * To change this template use File | Settings | File Templates.
 */
public class LeafNode extends TreeNode {

    //    public int L = 10;
//    private double T = 2.8;
    private ArrayList<MinCluster> childrenList;
    private LeafNode pre;
    private LeafNode next;

    public LeafNode() {
        childrenList = new ArrayList<MinCluster>();
    }

    public LeafNode(double[] data, String content) {
        super(data, content);
        childrenList = new ArrayList<MinCluster>();
    }

    //节点分裂
    @Override
    public void split() {
        int c1 = 0;
        int c2 = 0;
        double maxDist = 0;
        int length = this.getChildrenList().size();
        for (int i = 0; i < length - 1; i++) {
            for (int j = i + 1; j < length; j++) {
                double dist = this.getChildrenList().get(i).getClusterFeature().getDistanceTo(this.getChildrenList().get(j).getClusterFeature());
                if (dist > maxDist) {
                    maxDist = dist;
                    c1 = i;
                    c2 = j;
                }
            }
        }

        //以距离最远的孩子节点为中心，把B+1个孩子分为两个大簇，其中一个簇仍留做本节点的孩子，另一个新建一个节点来领养它们
        LeafNode newNode = new LeafNode();
        newNode.addChild(this.getChildrenList().get(c2));

        //如果本节点已经是root节点，则需要创建一个新的root节点
        if (this.getParent() == null) {
            NonLeafNode root = new NonLeafNode();
            root.setN(this.getN());
            root.setLS(this.getLS());
            root.setSS(this.getSS());
            this.setParent(root);
            root.addChild(this);
        }

        //建立新节点和本节点的父节点的父子关系
        newNode.setParent(this.getParent());
        ((NonLeafNode) this.getParent()).addChild(newNode);

        //把离newNode节点近的孩子归属到这个newNode簇里面
        for (int i = 0; i < length; i++) {
            if (i != c1 && i != c2) {
                if (this.getChildrenList().get(i).getClusterFeature().getDistanceTo(this.getChildrenList().get(c2).getClusterFeature()) < this.getChildrenList().get(i).getClusterFeature().getDistanceTo(this.getChildrenList().get(c1).getClusterFeature())) {
                    newNode.addChild(this.getChildrenList().get(i));
                }
            }
        }

        //把离newNode近的孩子从本节点中删除
        for (MinCluster cluster : newNode.getChildrenList()) {
            newNode.addCF(cluster.getClusterFeature(), true);
            this.deleteChild(cluster);
            this.addCF(cluster.getClusterFeature(), false);
        }

        //把新增加的LeafNode添加到LeafNode的双向链表中
        if (this.getNext() != null) {
            newNode.setNext(this.getNext());
            this.getNext().setPre(newNode);
        }

        this.setNext(newNode);
        newNode.setPre(this);

        //如果本节点的分裂导致了父节点的孩子书超过了分支因子，则引发父节点的分裂
        NonLeafNode pn = (NonLeafNode) this.getParent();
        if (pn.getChildrenList().size() > pn.getB()) {
            this.getParent().split();
        }


    }

    @Override
    public void absorbSubCluster(MinCluster cluster) {
        ClusterFeature clusterFeature = cluster.getClusterFeature();
        int nearIndex = 0;
        double minDist = Double.MAX_VALUE;
        int length = this.getChildrenList().size();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                double dist = clusterFeature.getDistanceTo(this.getChildrenList().get(i).getClusterFeature());
                if (dist < minDist) {
                    nearIndex = i;
                }
            }

            //计算两个簇合并后的直径
            double mergeDiameter = MinCluster.getDiameter(cluster, this.getChildrenList().get(nearIndex));

            //如果合并后发现簇的直径超过了阀值，则把cluster作为一个单独的孩子插入到本叶节点下
            if (mergeDiameter > CommonConstance.clusterDiameter) {
                this.addChild(cluster);
                if (this.getChildrenList().size() > CommonConstance.childrenNumbers) {
                    this.split();
                }
            } else {   //如果没有超过阀值，则直接合并两个簇
                this.getChildrenList().get(nearIndex).mergeCluster(cluster);
            }
        } else { //创建B树指出，叶节点都还没有children
            this.addChild(cluster);

        }

        this.addCFUpToRoot(cluster.getClusterFeature());
    }


    public void addChild(MinCluster child) {
        this.childrenList.add(child);
    }

    public void deleteChild(MinCluster child) {
        this.childrenList.remove(child);
    }


    public ArrayList<MinCluster> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(ArrayList<MinCluster> childrenList) {
        this.childrenList = childrenList;
    }

    public LeafNode getPre() {
        return pre;
    }

    public void setPre(LeafNode pre) {
        this.pre = pre;
    }

    public LeafNode getNext() {
        return next;
    }

    public void setNext(LeafNode next) {
        this.next = next;
    }
}
