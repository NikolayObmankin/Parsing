import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ParsingHTML {
    public static void main(String[] args) {
        String URL = "https://skillbox-java.github.io/";
        Document doc = null;
        try {
            doc = Jsoup.connect(URL).get();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Elements lineNumber = doc.select("div.js-depend");
        for (int i = 0; i < lineNumber.size(); i++) {
            System.out.println();
            System.out.println(lineNumber.get(i).attr("data-depend-set"));
            System.out.println();
            for (int j = 0; j < lineNumber.get(i).select("p.single-station").size(); j++) {
                System.out.println(lineNumber.get(i).select("p.single-station").get(j).text());
                boolean isConnected = !lineNumber.get(i).select("p.single-station").get(j).select("span.t-icon-metroln").isEmpty();
                if (isConnected) {
                    for (int k = 0; k < lineNumber.get(i).select("p.single-station").get(j).select("span.t-icon-metroln").size(); k++) {
                        String numberLineConnection = lineNumber.get(i).select("p.single-station").get(j).select("span.t-icon-metroln").get(k).attr("class");
                        String nameStationConnection = lineNumber.get(i).select("p.single-station").get(j).select("span.t-icon-metroln").get(k).attr("title");
//                        System.out.println(nameStationConnection);
                        numberLineConnection = getNumberLineConnection(numberLineConnection);
                        nameStationConnection = getNameStationConnection(nameStationConnection);
//                        System.out.println(numberLineConnection);
                        System.out.println("Переход на станцию: " + nameStationConnection + " , линия: " + numberLineConnection);
                    }
                }
            }
        }
    }
    public static String getNumberLineConnection(String numberLineConnection) {
        String[] temp = numberLineConnection.split("-");
        String lineNumber = temp[temp.length-1];
        return lineNumber;
    }
    public static String getNameStationConnection(String nameStationConnection) {
        String[] temp = nameStationConnection.split("«|»");
        temp = temp[1].split("»");
        String nameConnection = temp[0];
        return nameConnection;
    }
}
