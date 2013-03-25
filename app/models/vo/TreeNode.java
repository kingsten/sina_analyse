package models.vo;

/**
 * Created with IntelliJ IDEA.
 * User: LKQ
 * Date: 13-3-15
 * Time: 下午4:35
 * To change this template use File | Settings | File Templates.
 */
public abstract class TreeNode extends ClusterFeature {

    private TreeNode parent;

    public TreeNode() {

    }

    public TreeNode(double[] data, String content) {
        super(data, content);
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }


    public void addCFUpToRoot(ClusterFeature clusterFeature) {
        TreeNode node = this;
        while (node != null) {

            node.addCF(clusterFeature, true);
            node = node.getParent();
        }
    }

    abstract void split();

    abstract void absorbSubCluster(MinCluster cluster);
}
