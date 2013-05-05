package controllers;

import job.ImportDataJob;
import play.libs.Files;
import play.mvc.Controller;
import service.ImportDataService;
import util.CommonUtil;

import java.io.File;

/**
 * Created with IntelliJ IDEA. User: LKQ Date: 13-3-13 Time: 下午3:41 To change
 * this template use File | Settings | File Templates.
 */
public class ImportData extends Controller {


    public static void importData(File sinaOriginalFile) {
        String imgName = sinaOriginalFile.getName();


        // 这里存放的是指定的目录data（一般是从配置文件中读取）
        final File storeFile = new File(
                CommonUtil.getConfigureByKey("attachments.path"), imgName);

        Files.copy(sinaOriginalFile, storeFile);

        ImportDataJob importDataJob = new ImportDataJob(storeFile);

        try {
            importDataJob.doJob();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        renderText("success");

    }
}
