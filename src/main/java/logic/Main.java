package logic;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        String url;
        Scanner urlIn = new Scanner(System.in);
        Document doc = null;
        String docstr = null;
        System.out.println("Escriba la url");
        url = urlIn.nextLine();
        try {
            doc = Jsoup.connect(url).get();
            docstr = Jsoup.connect(url).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int lineas = lineasEnElDoc(docstr);
        System.out.println("lineas="+String.valueOf(lineas));
        int parrafos = parrafosEnElDoc(doc);
        System.out.println("parrafos="+String.valueOf(parrafos));
    }

    private static int lineasEnElDoc(String doc){
        //System.out.println(doc.html());
        String[] splitted;
        splitted = doc.split("\n");
        return splitted.length;        
    }
    private static int parrafosEnElDoc(Document doc){
        Elements ele = doc.select("p");

        return ele.size();
    }
    private


}
