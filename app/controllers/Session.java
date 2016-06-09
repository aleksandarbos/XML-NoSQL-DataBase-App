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
    
    public static void regulation(int regulationId, String votingFirstResult, int votesFirstNumberYes, int votesFirstNumberNo, int votesFirstNumberOff) {
    	System.out.println("akt: " + regulationId + ", glasanje: " + votingFirstResult +" rezult za: "+votesFirstNumberYes+ " rezult ne: " +votesFirstNumberNo+" rezult bez: " +votesFirstNumberOff);
    	
    }
    
    public static void amandment(int amandmentId, String votingAmandmentResult, int votesAmandmentNumberYes, int votesAmandmentNumberNo, int votesAmandmentNumberOff) {
    	System.out.println("amandman: " + amandmentId + ", glasanje: " + votingAmandmentResult +" rezult za: "+votesAmandmentNumberYes+ " rezult ne: " +votesAmandmentNumberNo+" rezult bez: " +votesAmandmentNumberOff);
    	
    }
    
    public static void finals(int regulationFinalId, String votingFinalResult, int votesFinalNumberYes, int votesFinalNumberNo, int votesFinalNumberOff) {
    	System.out.println("zakljucno: " + regulationFinalId + ", glasanje: " + votingFinalResult +" rezult za: "+votesFinalNumberYes+ " rezult ne: " +votesFinalNumberNo+" rezult bez: " +votesFinalNumberOff);
    	
    }
}
