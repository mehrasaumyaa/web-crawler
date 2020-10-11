
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
 
 
public class responseHeader {
 
	public static void main(String[] args) {
		try {
			File dir = new File(".");
			String loc = dir.getCanonicalPath() + File.separator + "record.txt";
			FileWriter fstream = new FileWriter(loc, true);
			BufferedWriter writer = new BufferedWriter(fstream);
			writer.newLine();
			writer.close();
			
			URL obj = new URL("https://www.brynmawr.edu/giving#main-content");
			URLConnection conn = obj.openConnection();
			Map<String, List<String>> map = conn.getHeaderFields();
 
			System.out.println("Printing All Response Header for URL: " + obj.toString() + "\n");
			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				System.out.println(entry.getKey() + " : " + entry.getValue());
			}
			
			System.out.println("\nGet Response Header By Key ...\n");
			List<String> contentLength = map.get("Content-Length");
			if (contentLength == null) {
				System.out.println("'Content-Length' not present in Header!");
			} else {
				for (String header : contentLength) {
					System.out.println("Content-Lenght: " + header);
				}
			}
			
			List<String> lastModified = map.get("Last-Modified");
			if (lastModified == null) {
				System.out.println("'Last-Modified' not present in Header!");
			} else {
				for (String header : lastModified) {
					System.out.println("Last-Modified: " + header);
				}
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

/*public class responseHeader {

  public static void main(String[] args) {

    try {

	URL obj = new URL("http://brynmawr.edu/library");
	URLConnection conn = obj.openConnection();
	Map<String, List<String>> map = conn.getHeaderFields();

	System.out.println("Printing Response Header...\n");

	for (Map.Entry<String, List<String>> entry : map.entrySet()) {
		System.out.println("Key : " + entry.getKey() 
                           + " ,Value : " + entry.getValue());
	}

	System.out.println("\nGet Response Header By Key ...\n");
	String server = conn.getHeaderField("Server");

	if (server == null) {
		System.out.println("Key 'Server' is not found!");
	} else {
		System.out.println("Server - " + server);
	}

	System.out.println("\n Done");

    } catch (Exception e) {
	e.printStackTrace();
    }

  }
}*/
