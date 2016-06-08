package controllers;

import java.sql.Time;
import java.util.Date;

import play.mvc.Controller;

public class Session extends Controller {

    public static void show() {
    	render();
    }
    
    public static void schedule(String sessionDate, String sessionTime) {
    	System.out.println("Zakazivanje za datum: " + sessionDate + ", time: " + sessionTime);
    	show();
    }
    
    public static void register(int documentId, String votingFirstResult, int votesFirstNumberYes, int votesFirstNumberNo, int votesFirstNumberOff) {
    	//System.out.println("Zakazivanje za datum: " + sessionDate + ", time: " + sessionTime);
    	show();
    }
}
