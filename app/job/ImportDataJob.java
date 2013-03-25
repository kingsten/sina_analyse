package job;

import play.jobs.Job;
import service.ImportDataService;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: lkq
 * Date: 13-3-18
 * Time: 下午6:11
 * To change this template use File | Settings | File Templates.
 */
public class ImportDataJob extends Job<Object>{

    private File sinaDataFile;

    public ImportDataJob(File sinaDataFile){
        this.sinaDataFile = sinaDataFile;
    }



    @Override
    public void doJob() throws Exception {
        ImportDataService.saveSinaOriginalDate(sinaDataFile);
    }


}
