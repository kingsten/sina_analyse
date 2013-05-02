package controllers;

import play.*;
import play.mvc.*;

import java.io.IOException;
import java.util.*;

import models.*;
import service.CutWordService;

public class Application extends Controller {

    public static void index() {
//        render();
        CutWordService cutWordService = new CutWordService();
        String test = "我是中国人，我现在在北京上大学，我在北京邮电大学就读";
        try {
            System.out.println("result=============>" + cutWordService.segStr(test, "simp"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        renderTemplate("/Application/uploadData.html");
    }


}