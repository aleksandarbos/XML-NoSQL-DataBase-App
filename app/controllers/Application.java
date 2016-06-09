package controllers;

import database.DatabaseAccessor;
import play.mvc.*;

import java.io.IOException;

import util.Util;

public class Application extends Controller {

    private DatabaseAccessor db = DatabaseAccessor.getInstance();

    public static void index() {
        render();
    }

}