import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class JavaTests {

	public static void main(String[] args) throws Exception {
		HashMap<String, String> queries = new HashMap<String, String>();
		//queries.put("username", "tom");
		//queries.put("sessionId", "77c50ade377b83efad528000600cd80398f568989fec9e3b8f022db0ec27221285f3a9f989992bf12fc648a97bf73d16571526a2d396bddb01137fc0457e5a9d");
		queries.put("id", "1");
		
		String url = WebAPI.queryBuilder(queries, "tom", "77c50ade377b83efad528000600cd80398f568989fec9e3b8f022db0ec27221285f3a9f989992bf12fc648a97bf73d16571526a2d396bddb01137fc0457e5a9d");
		System.out.println(WebAPI.getJson("user/info", url));

	}

}
