import util.Util;

import java.io.IOException;

/**
 * Created by aleksandar on 4.6.16..
 */
public class Main {
    public static void main(String[] args) {
        Util.ConnectionProperties cp = null;
        try {
            cp = Util.loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("props.database = " + cp.database);
    }
}
