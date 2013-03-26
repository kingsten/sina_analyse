package controllers;

import job.AnalyseDataJob;
import play.mvc.Controller;

/**
 * Created with IntelliJ IDEA.
 * User: lkq
 * Date: 13-3-25
 * Time: 下午7:47
 * To change this template use File | Settings | File Templates.
 */
public class AnalyseData extends Controller {

    public static void analyseUserData() {
        AnalyseDataJob analyseData = new AnalyseDataJob();
        analyseData.doJob();
        renderJSON("success");

    }
}
