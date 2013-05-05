package controllers;

import play.*;
import play.mvc.*;

import java.io.IOException;
import java.util.*;

import models.*;
import service.CutWordService;

public class Application extends Controller {

    public static void index() {


        renderTemplate("/Application/uploadData.html");
    }


}