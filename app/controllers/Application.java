package controllers;

import database.DatabaseAccessor;
import play.mvc.*;

import java.io.IOException;

import util.Util;

public class Application extends Controller {

    public static void index() {
        DatabaseAccessor db = DatabaseAccessor.getInstance();
        render();
    }
}