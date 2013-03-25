package models.vo;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: LKQ
 * Date: 13-3-15
 * Time: 下午4:39
 * To change this template use File | Settings | File Templates.
 */
public class NonLeafNode extends TreeNode {

    private int B = 5;
    private ArrayList<TreeNode> childrenList;

    public NonLeafNode() {
        childrenList = new ArrayList<TreeNode>();
    }

    public NonLeafNode(double[] data, String content) {
        super(data, content);
        childrenList = new ArrayList<TreeNode>();
    }

    public int getB() {
        return B;
    }

    public void setB(int b) {
        B = b;
    }

    public ArrayList<TreeNode> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(ArrayList<TreeNode> childrenList) {
        this.childrenList = childrenList;
    }

    //节点分裂
    @Override
    public void split() {

        //找到距离最远的两个孩子节点
        int c1 = 0;
        int c2 = 0;
        double maxDIst = 0;
        int length = this.getChildrenList().size();
        for (int i = 0; i < length - 1; i++) {
            for (int j = i + 1; j < length; j++) {
                double dist = this.getChildrenList().get(i).getDistanceTo(this.getChildrenList().get(j));
                if (dist > maxDIst) {
                    maxDIst = dist;
                    c1 = i;
                    c2 = j;
                }
            }
        }

        //以距离最远的孩子节点为中心，把B+1个孩子分为两个大簇，其中一个簇仍留子啊本节点的孩子，另外一新创建一个节点来领养它们
        NonLeafNode newNode = new NonLeafNode();
        newNode.addChild(this.getChildrenList().get(c2));

        //如果本节点已经是root节点，则需要创建一个新的root节点
        if (this.getParent() == null) {
            NonLeafNode root = new NonLeafNode();
            root.setN(this.getN());
            root.setLS(this.getLS());
            root.setSS(this.getSS());
            root.addChild(this);
            this.setParent(root);
        }

        newNode.setParent(this.getParent());
        ((NonLeafNode) this.getParent()).addChild(newNode);
        for (int i = 0; i < length; i++) {
            if (i != c1 && i != c2) {
                if (this.getChildrenList().get(i).getDistanceTo(this.getChildrenList().get(c2)) < this.getChildrenList().get(i).getDistanceTo(this.getChildrenList().get(c1))) {
                    newNode.addChild(this.getChildrenList().get(i));
                }
            }
        }

        for (TreeNode treeNode : newNode.getChildrenList()) {
            newNode.addCF(treeNode, true);
            this.deleteChild(treeNode);
            this.addCF(treeNode, false);
        }

        //如果本节点分裂导致父节点的孩子数超过了分支因子，引发父节点的分裂
        NonLeafNode pn = (NonLeafNode) this.getParent();
        if (pn.getChildrenList().size() > B) {
            this.getParent().split();
        }

    }

    @Override
    public void absorbSubCluster(MinCluster cluster) {
        //从本节点的孩子中寻找与Cluster最近的子节点
        ClusterFeature clusterFeature = cluster.getClusterFeature();
        int nearIndex = 0;
        double minDist = Double.MAX_VALUE;
        for (int i = 0; i < this.getChildrenList().size(); i++) {
            double dist = clusterFeature.getDistanceTo(this.getChildrenList().get(i));
            if (dist < minDist) {
                nearIndex = i;
            }
        }

        //让最最近的那个子节点absorb掉这个新到的cluster
        this.getChildrenList().get(nearIndex).absorbSubCluster(cluster);        ///todo 这里意图不明

    }

    public void addChild(TreeNode child) {
        this.childrenList.add(child);
    }

    public void deleteChild(TreeNode child) {
        this.childrenList.remove(child);
    }
}
