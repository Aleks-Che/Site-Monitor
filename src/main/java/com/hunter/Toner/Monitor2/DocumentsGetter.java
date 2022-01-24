package com.hunter.Toner.Monitor2;

import com.codeborne.selenide.WebDriverRunner;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;

import java.util.*;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class DocumentsGetter {
    void domRuSamara() {
        try {
            open("https://auth.dom.ru/auth/realms/user/protocol/openid-connect/auth?response_type=code&redirect_uri=https%3A%2F%2Fnewlkb2b.dom.ru%2Flk&client_id=lkb2b&nonce=96fd7cb4cbb2a9cdc8c772e5f2685565&state=76b92d2ffca2e2b4e07b1a06ad677445&scope=openid");
            $(".confirm__btn").click();
            WebDriverRunner.getWebDriver().manage().addCookie(new Cookie("city-domain", "samara"));
            WebDriverRunner.getWebDriver().manage().addCookie(new Cookie("localdomain", "samara"));
            WebDriverRunner.getWebDriver().manage().addCookie(new Cookie("citydomain", "samara"));
            WebDriverRunner.getWebDriver().manage().addCookie(new Cookie("CITY", "%D0%A1%D0%B0%D0%BC%D0%B0%D1%80%D0%B0"));
            refresh();
            $(".field__input").setValue("input login").pressTab().sendKeys("input password");
            ;
            $("#password.field__input").setValue("jF73!Hue2!r").pressEnter();
            Thread.currentThread().sleep(1000);
            open("https://lkb2b.dom.ru/docs");
//            $(".b-table__tbody").findAll("a").get(0).click();
//            $(".b-table__tbody").findAll("a").get(1).click();
//            $(".b-table__tbody").findAll("a").get(2).click();
            $(".f-header").findAll(".link-border").get(1).click();
            Set<Cookie> cookies = WebDriverRunner.getWebDriver().manage().getCookies();
//            Map<String, String> map1 = cookies.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            Map<String, String> map1 = new HashMap<String, String>();
//            for(Map.Entry<String, String> entry : cookies){
            for (Cookie cookie : cookies){
                System.out.println(cookie.getName() + "=" + cookie.getValue());
            }
            Thread.currentThread().sleep(30000);
            Set cookies2 = WebDriverRunner.getWebDriver().manage().getCookies();
            close();
            for (Object o : cookies2)
                System.out.println();
            Thread.currentThread().sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
