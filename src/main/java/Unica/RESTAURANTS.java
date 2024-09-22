package Unica;

import java.util.ArrayList;
import java.util.List;

public enum RESTAURANTS {
    ASSARIN_ULLAKKO("https://www.unica.fi/modules/json/json/Index?costNumber=1920&language=fi"),
    DELICA("https://www.unica.fi/modules/json/json/Index?costNumber=1985&language=fi"),
    UNICAN_KULMA("https://www.unica.fi/modules/json/json/Index?costNumber=1990&language=fi"),
    DELI_PHARMA("https://www.unica.fi/modules/json/json/Index?costNumber=198501&language=fi"),
    KISÄLLI("https://www.unica.fi/modules/json/json/Index?costNumber=1900&language=fi"),
    MACCIAVELLI("https://www.unica.fi/modules/json/json/Index?costNumber=1970&language=fi"),
    SIGYN("https://www.unica.fi/modules/json/json/Index?costNumber=1965&language=fi"),
    PICCU_MACCIA("https://www.unica.fi/modules/json/json/Index?costNumber=197001&language=fi"),
    MONTTU_JA_MERKATORI("https://www.unica.fi/modules/json/json/Index?costNumber=1940&language=fi"),
    PUUTORIN_NURKKA("https://www.unica.fi/modules/json/json/Index?costNumber=1930&language=fi"),
    FABRIK("https://www.unica.fi/modules/json/json/Index?costNumber=198502&language=fi"),
    KAIVOMESTARI("https://www.unica.fi/modules/json/json/Index?costNumber=190001&language=fi"),
    RUOKAKELLO("https://www.unica.fi/modules/json/json/Index?costNumber=1950&language=fi"),
    DENTAL("https://www.unica.fi/modules/json/json/Index?costNumber=1980&language=fi"),
    KAFFELI("https://www.unica.fi/modules/json/json/Index?costNumber=1945&language=fi"),
    GALILEI("https://www.unica.fi/modules/json/json/Index?costNumber=1995&language=fi"),
    LINUS("https://www.unica.fi/modules/json/json/Index?costNumber=2000&language=fi");
    // Add more restaurants as needed
    // RESTAURANT_2("https://example.com/restaurant2"),
    // RESTAURANT_3("https://example.com/restaurant3");

    private final String url;

    RESTAURANTS(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public static List<String> getAllUrls() {
        List<String> urls = new ArrayList<>();
        for (RESTAURANTS restaurantURL : RESTAURANTS.values()) {
            urls.add(restaurantURL.getUrl());
        }
        return urls;
    }
    }
