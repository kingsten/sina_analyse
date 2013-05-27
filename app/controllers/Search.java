package controllers;

import models.SinaUserTag;
import models.vo.ReturnData;
import play.mvc.Controller;
import service.DataService;
import util.CommonConstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: LKQ
 * Date: 13-5-20
 * Time: 下午12:17
 * To change this template use File | Settings | File Templates.
 */
public class Search extends Controller {

    public static void index() {
        renderTemplate("/Application/search.html");
    }

    public static void searchByUser(String userId, Integer curPage, Integer perPage) {
        List<SinaUserTag> sinaUserTagList = DataService.getUsersByUserId(userId, curPage, perPage);
        System.out.println(sinaUserTagList.toString());
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("sinaUserList", sinaUserTagList);
        data.put("curPage", curPage);
        renderJSON(new ReturnData(data, true, "success"));
    }

    public static void searchByTag(String tag, Integer curPage, Integer perPage) {
        List<SinaUserTag> sinaUserTagList = DataService.getUserListByTag(tag, curPage, perPage);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("sinaUserList", sinaUserTagList);
        data.put("curPage", curPage);
        renderJSON(new ReturnData(data, true, "success"));
    }

    public static void getAllTag() {
        Set<String> tagStrList = CommonConstance.paperTerritorySet;
        renderJSON(new ReturnData(tagStrList,true,"success"));
    }
}
