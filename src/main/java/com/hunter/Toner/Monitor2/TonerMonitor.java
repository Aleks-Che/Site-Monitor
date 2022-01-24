package com.hunter.Toner.Monitor2;

import com.codeborne.selenide.ElementsCollection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$;

public class TonerMonitor {

    void konika223check() {
        Map<String, String> konica223MapDepartment = new HashMap<>();
        konica223MapDepartment.put("192.168.1.220", "ЭлектроУправление");
        konica223MapDepartment.put("192.168.1.221", "Отдел кадров");
        konica223MapDepartment.put("192.168.1.222", "ПЭО");
//        konica223MapDepartment.put("192.168.1.223", "Юристы");
        konica223MapDepartment.put("192.168.1.224", "Траспортники");
        konica223MapDepartment.put("192.168.1.225", "Бухгалтерия");
        konica223MapDepartment.put("192.168.1.228", "ООП");
        ArrayList<String> konica223IP = new ArrayList<>(Arrays.asList("192.168.1.220", "192.168.1.221", "192.168.1.222", "192.168.1.224", "192.168.1.225", "192.168.1.228"));
        Map<String, String> konica223Map = new HashMap<>();
        for (int i = 0; i < konica223IP.size(); i++) {
            konica223Map.put(konica223IP.get(i), konika223loginUser("http://" + konica223IP.get(i) + "/wcd/index.html"));
        }
        for (int i = 0; i < konica223IP.size(); i++) {
            System.out.println(konica223IP.get(i) + " " + konica223MapDepartment.get(konica223IP.get(i)) + " " + konica223Map.get(konica223IP.get(i)));
        }
    }

    void kyoceraP6235() {
        kyoceraP6235("http://192.168.1.217/wlmpor/index.htm");
    }

    void konika227() {
        konika227("http://192.168.1.218/wcd/system_device.xml");
    }

    public void kyoceraP6235(String URL) {
        open("");
        String result = null;
        ElementsCollection body = $$("#document");
        System.out.println(body.size());
        for (int i = 0; i < body.size(); i++) {
            System.out.println(body.get(i).getText());
            if (body.get(i).text().contains("%")) {
                System.out.println(body.get(i).text().substring(body.get(i).text().indexOf("%") - 2, body.get(i).text().indexOf("%") + 1));
            }
        }
    }

    public void konika227(String URL) {
        open(URL);
        String result = null;
        ElementsCollection body = $$(By.name("body"));
        System.out.println(body.size());
        for (int i = 0; i < body.size(); i++) {
            if (body.get(i).text().contains("%")) {
                result = body.get(i).text().substring(body.get(i).text().indexOf("%") - 2, body.get(i).text().indexOf("%") + 1);
                System.out.println(result);
            }
        }
        try {
            Document doc = Jsoup.connect(URL)
                    .timeout(0)
                    .post();
//                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.190 Safari/537.36 OPR/74.0.3911.154")
//                    .referrer("http://www.google.com")
//                    map.put("city_path", "ekaterinburg")
            System.out.println(doc.toString());
//            Elements listNews = doc.select("#document");
//            System.out.println(listNews.size());
//            for (int i = 0; i < listNews.size(); i++) {
//                listNews.get(i).text();
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return result;
    }

    public void konika223loginAdmin() {
        open("http://192.168.1.225/wcd/top.xml");
        $("input#Admin").click();
        $("input#Admin").pressEnter();
        $("input#Admin_Pass").setValue("12345678").pressEnter();
        ElementsCollection body = $$(By.className("contents-body"));
        for (int i = 0; i < body.size(); i++) {
            System.out.println(body.get(i).innerText());
        }
    }

    public String konika223loginUser(String URL) {
        open(URL);
        $("input#User").pressEnter();
        String result = null;
        ElementsCollection body = $$("table.data-table");
        for (int i = 0; i < body.size(); i++) {
            if (body.get(i).text().contains("%")) {
                result = body.get(i).text().substring(body.get(i).text().indexOf("%") - 2, body.get(i).text().indexOf("%") + 1);
            }
        }
        return result;
    }
}
