import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class basicFileCrawler{

    private HashSet<String> links;
    int i;
    
    public basicFileCrawler() throws IOException 
    {
        links = new HashSet<String>();
        i=0;
      
    }

    public void getPageLinks(String URL,String loc) throws IOException 
    {
   
    	//if URL contains any of the robot restrictions, don't parse and crawl URL 
    		if(URL.contains("/alumnae/04reunion/attendees.pdf") || URL.contains("/webstats/") ||
				URL.contains("/library/bb/") || URL.contains("/provost/cv/") || URL.contains("/treasurer/internal/")
				|| URL.contains("/humanresources/Internal/") || URL.contains("/library/budget/") ||
				URL.contains("/Library/budget/") || URL.contains("/creativewriting/archived_site/") ||
				URL.contains("/communications/proofs/") || URL.contains("mailto")) {
    			return;
    		} 			
    		
    	 	if (URL.contains("brynmawr.edu")) 
    		{
  
    	//4. Check if you have already crawled the URL	 
        if (!links.contains(URL) && i<=10000) 
        {
        	System.out.println(URL);
        		i=i+1;
            try {
                //If not add it to the index and write to recordURLs.txt
                links.add(URL);
                writeToFile(URL,loc);
                   
                //Fetch the HTML code
                Document document = Jsoup.connect(URL).get();
                //Parse the HTML to extract links to other URLs
                Elements linksOnPage = document.select("a[href]");

                //For each extracted URL... go back to Step 4.
                for (Element page : linksOnPage) {
                    getPageLinks(page.attr("abs:href"), loc);
                }
            } catch (IOException e){
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
          }
		}
    	  
    }
   
    public static void main(String[] args) throws IOException 
    {
    	File dir = new File(".");
		String loc = dir.getCanonicalPath() + File.separator + "responseURLs.txt";
		FileWriter fstream = new FileWriter(loc, true);
		BufferedWriter writer = new BufferedWriter(fstream);
		writer.newLine();
		writer.close();
        //1. Pick a URL from the frontier
        new basicFileCrawler().getPageLinks("http://www.brynmawr.edu/",loc);
    }
    
    public void writeToFile(String URL,String loc ) throws IOException
    {
    	try {
    		FileWriter fstream = new FileWriter(loc, true);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(URL);
			out.newLine();
			out.close();
    		
    	}
    	catch(IOException e){
    		System.out.println("Error\n:"+e);
    	}
    }

}
