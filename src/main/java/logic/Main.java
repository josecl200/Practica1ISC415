package logic;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
        int imagenes = imagenesEnElDoc(doc);
        System.out.println("imagenes="+String.valueOf(imagenes));
        int gets = formsEnElDoc(doc,"GET");
        System.out.println("forms con GET="+String.valueOf(gets));
        int posts = formsEnElDoc(doc,"POST");
        System.out.println("forms con POST="+String.valueOf(posts));
        inputsEnLosFormEnElDoc(doc);

        peticionAlDoc(doc,url);
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
    private static int imagenesEnElDoc(Document doc){
        Elements ele = doc.select("p img");

        return ele.size();
    }
    private static int formsEnElDoc(Document doc,String method){
        Elements ele = doc.select("form[method =\"" +method+"\"]");

        return ele.size();
    }
    private static void inputsEnLosFormEnElDoc(Document doc){
        Elements ele = doc.select("form");
        for (int i=0;i<ele.size();i++){
            Elements elem = ele.select("input");
            System.out.println("Form No. "+String.valueOf(i+1));
            for(Element el: elem){
                System.out.println("Input tipo: " + el.attr("type"));
            }
        }
        return;
    }
    private static void peticionAlDoc(Document doc, String url){
        Elements postForms = doc.select("form[method=\"POST\"]");
        String newstr = url;
        if (null != url && url.length() > 0 )
        {
            int endIndex = url.lastIndexOf("/");
            if (endIndex != -1 || endIndex != 6 || endIndex != 7)
            {
                newstr = url.substring(0, endIndex);
            }
        }

        for (Element el:postForms) {
            try {
                Document postReq = Jsoup.connect(newstr + el.attr("action")).header("matricula", "20160138")
                        .data("asignatura","practica1").post();
                System.out.println("Respuesta: ");
                System.out.println(postReq.html());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
