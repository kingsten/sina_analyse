package job;

import models.SinaUserTag;
import models.vo.BIRCH;
import models.vo.TreeNode;
import models.vo.WordsTable;
import play.jobs.Job;
import service.CutWordService;
import service.DataService;
import util.CommonConstance;
import util.MongoDbUtil;

import java.util.List;
import java.util.Map;

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
//        TreeNode root = birch.buildBTree();
//        birch.storeTree(root);
        List<String> userIdList = DataService.getAllUserId();
        for (String userId : userIdList) {
            String userContent = DataService.getUserAllContentByUserId(userId);
            String keywordString = CutWordService.getInstance(CommonConstance.cutWordModel).segStr(userContent);
            String[] wordArray = keywordString.split(" ");
            Map<Integer, Integer> sourceWordMap = CutWordService.getInstance(CommonConstance.cutWordModel).wordIdCount(wordArray);
            List<String> userTagList = WordsTable.getInstance().getClassification(sourceWordMap);
            System.out.println("userTag List =================>" + userTagList.toString());
            SinaUserTag sinaUserTag = new SinaUserTag();
            sinaUserTag.setUserId(userId);
            sinaUserTag.setUserTagList(userTagList);
            MongoDbUtil.getDatastore().save(sinaUserTag);
        }
    }
}
