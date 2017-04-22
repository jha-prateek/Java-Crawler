	
package crawler;

/*
	@author - Prateek Jha
	@date - 29 March 2017
	@class - Spider Class 
*/

//import java.io.IOException;
import java.util.LinkedList;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Spider {
    LinkedList<String> linksToVisit = new LinkedList<>();
    LinkedList<Integer> wordsFound = new LinkedList<>();
    private Document doc = null;
    private int pageIndex = 0, noOfWords;
    
    public void getUrl(String url)
    {
        this.linksToVisit.addFirst(url);
    }
   
    public void Scrape(String word)
    {
        try {
            // Bilding user Agent
            String userAgnt = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
            this.doc = Jsoup.connect(this.linksToVisit.get(pageIndex++)).userAgent(userAgnt).get();
            //String title = this.doc.title();
            Elements links = this.doc.select("a[href]");
            for(Element link: links)
            {
                if(this.linksToVisit.contains(link.absUrl("href")))
                {
                }
                else
                {
                    // Element not compatible with List 
                    // absUrl() method is used for typeCasting
                    this.linksToVisit.add(link.absUrl("href"));
                }
            }
            String query = this.doc.text();
            String[] listWords = query.toLowerCase().split("\\s+");
            //System.out.println("Total Text:" + listWords.length);
            
            // Frequency of Words in particular Link
            noOfWords = 0;
            for(String search: listWords)
            {
                if(search.contains(word.toLowerCase()))
                {
                    this.noOfWords++;
                }
            }
            this.wordsFound.add(noOfWords);
            
        } catch (Exception ex) {
            System.out.println(ex);
            // Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Crawl(String str, int times, String url)
    {
        getUrl(url);
        for(int index=0; index<times && (this.linksToVisit.get(index)!=null); index++)
        {
            //long startTime = System.currentTimeMillis();
            Scrape(str);
            //long endTime   = System.currentTimeMillis();
            //long totalTime = endTime - startTime;
            //System.out.println("Time Taken: " + totalTime);
        }
    }
}