package com.hunter.Toner.Monitor2;
import static com.codeborne.selenide.Selenide.*;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.WebDriverRunner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.Cookie;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.util.*;

@Configuration
@EnableScheduling
public class PS5Monitor {
    private static Logger logger = Logger.getLogger(PS5Monitor.class);

    Integer count = 0;
    Boolean dns = false;

    @Scheduled(fixedDelay = 48000)
    void monitoring() {
        if (dns) {
            loginDnsSelenide();
            dns = false;
        }
        ArrayList<String> foundList = new ArrayList<>(Arrays.asList("Добавить в корзину", "В корзину", "Купить", "Есть в наличии", "Предзаказать", "46"));
        ArrayList<String> exceptionListCommon = new ArrayList<>(Arrays.asList("Сообщить о поступлении", "Нет в наличии", "Товар закончился", "Скоро в продаже"));
        ArrayList<String> exceptionListVKFeed = new ArrayList<>(Arrays.asList("PlayStation 5 и Xbox Series", "PlayStation 5 — запускается только на PS5 со всеми", "радиатором идеально подходят для игровой консоли PlayStation 5", "В прошлом месяце Sony выпустила долгожданное обновление прошивки для PlayStation 5, которое разблокирует", "Друзья, мы наконец-то возвращаемся с долгожданными новостями по PlayStation 5.", "В сборник вошли четвёртая часть и «Утраченное наследие», обе игры с подтянутой графикой под PlayStation 5 и увеличенной частотой кадров. В продажу игра поступит 28 января 2022 года.", "Два замечательных приключения из серии Uncharted уже можно предзаказать в версии для PlayStation 5."));
        ArrayList<String> foundListOzon = new ArrayList<>(Arrays.asList("3 товара", "4 товара", "5 товаров", "6 товаров", "7 товаров", "8 товаров", "9 товаров", "10 товаров", "11 товаров", "12 товаров", "13 товаров", "14 товаров", "15 товаров"));
        ArrayList<String> vk = new ArrayList<>(Arrays.asList("PlayStation 5"));
        PS5Monitor PS5Monitor = new PS5Monitor();
        ArrayList<Site> sites = PS5Monitor.getSites();
        Date date = new Date();
        if (date.getHours() > 7 && date.getHours() < 24) {
            if (PS5Monitor.connectSelenide("https://www.ozon.ru/highlight/playstation-317367/", ".b4a1", foundListOzon, exceptionListCommon)) {
                PS5Monitor.sendPost("https://www.ozon.ru/highlight/playstation-317367/");
            }
            try {
                Thread.currentThread().sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.error(e);
            }
            if (PS5Monitor.connectSelenide("https://vk.com/mgame", ".wall_text", vk, exceptionListVKFeed)) {
                PS5Monitor.sendPost("https://vk.com/mgame");
            }
            logger.info("VK");
            try {
                Thread.currentThread().sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.error(e);
            }
            for (int i = 0; i < sites.size(); i++) {
                if (PS5Monitor.connectSelenide(sites.get(i).URL, sites.get(i).tag, foundList, exceptionListCommon)) {
                    PS5Monitor.sendPost(sites.get(i).URL);
                }
                logger.info(i + ") " + sites.get(i).URL);
                try {
                    Thread.currentThread().sleep(48000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.error(e);
                }
            }
            if (count == 15) {
                PS5Monitor.sendPost(String.valueOf(count));
                count = 0;
            }
            count++;
        } else {
            try {
                Thread.currentThread().sleep(240000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.error(e);
            }
        }
        logger.info(" ------------------------ count " + count + "------------------------------");
    } //Проверяет список сайтов из метода getSites на наличие для покупки консоли PS5, при наличии отправить себе сообщение через телеграмм бота

    ArrayList<Site> getSites() {
        ArrayList<Site> sites = new ArrayList<>();
        sites.add(new Site("https://www.gamepark.ru/playstation5/console/IgrovayakonsolSonyPlayStation5DigitalEdition/", ".dataCol_r.right"));
        sites.add(new Site("https://www.citilink.ru/product/igrovaya-konsol-playstation-5-digital-edition-ps719398806-belyi-cherny-1583761/", "#product-item-1583761"));
        sites.add(new Site("https://www.gamepark.ru/playstation5/console/IgrovayakonsolSonyPlayStation5/", ".dataCol_r.right"));
        sites.add(new Site("https://store.sony.ru/product/konsol-playstation-5-digital-edition-317400/", ".mv-pos-st.mv-reverse"));
        sites.add(new Site("https://www.eldorado.ru/cat/detail/igrovaya-pristavka-playstation-5-digital-edition/", ".buyBox.buyBoxCorners"));
        sites.add(new Site("https://www.citilink.ru/product/igrovaya-konsol-playstation-5-ps719398707-belyi-chernyi-1607201/", "#product-item-1607201"));
        sites.add(new Site("https://www.mvideo.ru/products/konsol-sony-playstation-5-digital-edition-40074203", ".specs-price.ng-star-inserted"));
//        sites.add(new Site("https://store.sony.ru/product/konsol-playstation-5-317406/", ".mv-pos-st.mv-reverse"));
        sites.add(new Site("https://www.ozon.ru/product/igrovaya-konsol-playstation-5-belyy-178337786/", ".d9w7"));
        sites.add(new Site("https://www.citilink.ru/product/igrovaya-konsol-playstation-5-ps719398707-belyi-chernyi-1607200/", "#product-item-1607200"));
        sites.add(new Site("https://www.ozon.ru/product/igrovaya-konsol-playstation-5-digital-edition-belyy-178715781/", ".d9w7"));
//        sites.add(new Site("https://www.eldorado.ru/cat/detail/igrovaya-pristavka-sony-playstation-5/", ".buyBox.buyBoxCorners"));
        sites.add(new Site("https://www.citilink.ru/product/igrovaya-konsol-playstation-5-ps719398707-belyi-chernyi-1497117/", "#product-item-1497117"));
        sites.add(new Site("https://www.mvideo.ru/products/konsol-sony-playstation-5-40073270", ".specs-price.ng-star-inserted"));
        return sites;
    }

    public boolean connectSelenide(String URL, String selector, ArrayList<String> foundList, ArrayList<String> exceptionList) {
        open(URL);
        boolean result = false;
        ElementsCollection body = $$(selector);
        System.out.println(body.size());
        for (int i = 0; i < body.size(); i++) {
            for (int j = 0; j < foundList.size(); j++) {
                if (body.get(i).text().contains(foundList.get(j))) {
                    result = true;
                    break;
                }
            }
            for (int j = 0; j < exceptionList.size(); j++) {
                if (body.get(i).text().contains(exceptionList.get(j))) {
                    System.out.println("!!!!!! " + body.get(i).text());
                    result = false;
                    break;
                }
            }
            System.out.println(body.get(i).getText());
        }
        return result;
    }

    public void testSelenide(String URL, String selector) {
        open(URL);
        ElementsCollection body = $$(selector);
        System.out.println(body.size());
        for (int i = 0; i < body.size(); i++) {
            System.out.println(body.get(i).text());
        }
    }

    ArrayList<Cookie> getDnsCookies() {
        Cookie cookie1 = new Cookie("1P_JAR", "2021-11-18-05");
        Cookie cookie2 = new Cookie("NID", "511=k0ZR2BVkT7LQ8qOlQf3Llarpd56M6JXPFWRd8bPGFdiswnyM_90bwZw2k456nCWPIFkKjzkXhtvJnsQKHog6CWhhJH0Y0OAnHdnHSGz9S0vCkTqfKDuOZfkhtQQ-cbQ2x24CWlmRyMxRW0l1QPV1Pq1d6mq1p3Nvf4LJ9AnS-5w");
        Cookie cookie3 = new Cookie("PHPSESSID", "2a12d223134868f81600d00fc49ea539");
        Cookie cookie4 = new Cookie("VID", "3D66Mf1L2Yo600000W10H426:::0-0-6822388-6822373:CAASEIEdW0Jc_-6R2C_t8qMmBLIacNmcuLIWC1rlA7v6f-djxZKkSXJM-UFoXNW0iYR_pMWQhb0i_Kn9Ax9ABiOm4c7TAX573jhfwSYPfVnyZLRZbBdf_cRXb79h1wD791ycO7YBFrGLJ52qlOtl7Hrqi_tTkRS1jhH-DIYJfzevU1Tsc5k");
        Cookie cookie5 = new Cookie("_ab_", "9f202a14a5f56f8d274a49b27517d6d362d3b97f46665e0d806a0223ad44c018a%3A2%3A%7Bi%3A0%3Bs%3A4%3A%22_ab_%22%3Bi%3A1%3Ba%3A1%3A%7Bs%3A21%3A%22auth-get-confirm-code%22%3Bs%3A3%3A%22sms%22%3B%7D%7D");
        Cookie cookie6 = new Cookie("_csrf", "ed0dfe5c815d2e97f407dd578b9f66ed3e06afdabaf98d353f836cd24f9f12cda%3A2%3A%7Bi%3A0%3Bs%3A5%3A%22_csrf%22%3Bi%3A1%3Bs%3A32%3A%22eC5Yi-GQ88pGidVCMsvkaGY7QWG2mrlG%22%3B%7D");
        Cookie cookie7 = new Cookie("_fbp", "fb.1.1634194645229.1574029524");
        Cookie cookie8 = new Cookie("_ga", "GA1.2.934985311.1634194619");
        Cookie cookie9 = new Cookie("_gcl_au", "1.1.209227142.1634194644");
        Cookie cookie10 = new Cookie("_gid", "GA1.2.1150197456.1637214828");
        Cookie cookie11 = new Cookie("_ym_d", "1634194645");
        Cookie cookie12 = new Cookie("_ym_isad", "2");
        Cookie cookie13 = new Cookie("_ym_uid", "1634194645400169444");
        Cookie cookie14 = new Cookie("_ym_visorc", "b");
        Cookie cookie31 = new Cookie("rerf", "AAAAAGGSRuiQUKspA3UmAg==");
        Cookie cookie32 = new Cookie("rrpvid", "650045970426077");
        Cookie cookie33 = new Cookie("tmr_detect", "0%7C1637214831603");
        Cookie cookie34 = new Cookie("tmr_lvid", "467660e8ed1c12c74ad97e1f73fd063d");
        Cookie cookie35 = new Cookie("tmr_lvidTS", "1634194644507");
        ArrayList<Cookie> cookies = new ArrayList<>();
        cookies.add(cookie1);
        cookies.add(cookie2);
        cookies.add(cookie3);
        cookies.add(cookie4);
        cookies.add(cookie5);
        cookies.add(cookie6);
        cookies.add(cookie7);
        cookies.add(cookie8);
        cookies.add(cookie9);
        cookies.add(cookie10);
        cookies.add(cookie11);
        cookies.add(cookie12);
        cookies.add(cookie13);
        cookies.add(cookie14);
        cookies.add(cookie31);
        cookies.add(cookie32);
        cookies.add(cookie33);
        cookies.add(cookie34);
        cookies.add(cookie35);
        return cookies;
    }

    public void loginDnsSelenide() {
        ArrayList<Cookie> cookies = getDnsCookies();
        try {
            open("https://www.dns-shop.ru");
            for (int i = 0; i < cookies.size(); i++) {
                WebDriverRunner.getWebDriver().manage().addCookie(cookies.get(i));
            }
            refresh();
            Thread.currentThread().sleep(5000);
            open("https://www.dns-shop.ru/ordering/027b1e6d3e890811/");
            ElementsCollection body = $$(".hype-landing-products__item");
            for (int i = 0; i < body.size(); i++) {
                System.out.println(body.get(i).text());
            }
//            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

            Thread.currentThread().sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void connectSelenideDns(Set cookies) {
        Boolean result = false;
        open("https://www.dns-shop.ru/ordering/027b1e6d3e890811/");
        for (Object cookie : cookies)
            System.out.println(cookie.toString());
        ElementsCollection body = $$(".hype-landing-products__item-button");
        System.out.println(body.size());
        for (int i = 0; i < body.size(); i++) {
            System.out.println(body.get(i).text());
        }
    }

    public void sendPost(String message) {
        try {
            Document doc = Jsoup.connect("https://api.telegram.org/bot1463487012:*BOT_TOKEN*/sendMessage?chat_id=-1001540568866&text=" + message)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.190 Safari/537.36 OPR/74.0.3911.154")
                    .referrer("http://www.google.com")
                    .cookie("city_path", "ekaterinburg")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

