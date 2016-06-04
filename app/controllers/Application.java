package controllers;

import play.mvc.*;

import java.io.IOException;

import util.Util;

public class Application extends Controller {

    public static Util.ConnectionProperties props;

    public static void index() {

        render();
    }

}