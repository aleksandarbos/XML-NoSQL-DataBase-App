package database;

import models.rs.gov.parlament.propisi.Propis;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by aleksandar on 14.6.16..
 */
public class DatabaseQueryTest {
    @Test
    public void metadataSearch() throws Exception {
        DatabaseAccessor.getInstance();
        HashMap<String, Object> results = DatabaseQuery.metadataSearch("ZDRAVSTVO", "O PROMETU NEPOKRETNOSTI t123", "PREDLOZEN", "PROPIS", "p0@parlament.rs",
                "whatfuckever", "some collection", "fuck", "fuckenzi", "fuck", "shure", "yep", "ok",
                "ooo", "fff", "sss", "ssda", 0, 0, 0, 0, 0, 0);

    }

    @Test
    public void search() throws Exception {
        DatabaseAccessor.getInstance();
        HashMap<String, Object> results = DatabaseQuery.search("populacija", "", Propis.class);
        System.out.println("** Results size: " + results.size());
        Assert.assertTrue(!results.isEmpty());      // has results
    }

}