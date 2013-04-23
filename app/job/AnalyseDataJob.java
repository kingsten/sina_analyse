package job;

import models.vo.BIRCH;
import models.vo.TreeNode;
import play.jobs.Job;

/**
 * Created with IntelliJ IDEA.
 * User: lkq
 * Date: 13-3-25
 * Time: 下午7:48
 * To change this template use File | Settings | File Templates.
 */
public class AnalyseDataJob extends Job {

    @Override
    public void doJob() {
        BIRCH birch = new BIRCH();
        TreeNode root = birch.buildBTree();
//        birch.storeTree(root);
        birch.printTree(root);
    }
}
