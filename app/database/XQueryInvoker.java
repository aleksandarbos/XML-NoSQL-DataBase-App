package database;

import com.marklogic.client.eval.EvalResult;
import com.marklogic.client.eval.EvalResultIterator;
import com.marklogic.client.eval.ServerEvaluationCall;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;

public class XQueryInvoker {

	public static Vector<String> execute(String query) throws IOException {

		DatabaseAccessor db = DatabaseAccessor.getInstance();
		Vector<String> responses = new Vector<String>();

		System.out.println("[INFO] " + XQueryInvoker.class.getSimpleName());

		ServerEvaluationCall invoker = db.client.newServerEval();
		
		// Invoke the query
		invoker.xquery(query);
		
		// Interpret the results
		EvalResultIterator response = invoker.eval();

		System.out.print("[INFO] Response: ");
		
		if (response.hasNext()) {

			for (EvalResult result : response) {
				System.out.println("\n" + result.getString());
				responses.add(result.getString());
			}
		} else { 		
			System.out.println("your query returned an empty sequence.");
		}
		
		// Release the client
		db.client.release();
		
		return responses;

	}
	
	/**
	 * Convenience method for reading file contents into a string.
	 */
	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

}
