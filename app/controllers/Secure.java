package controllers;

import play.mvc.Before;
import play.mvc.Controller;

/**
 * Created by aleksandar on 12.6.16..
 */
public class Secure extends Controller {

    @Before
    public static void checkLogin() {
        if(session.get("user") == null)
            Application.index();
    }
}
