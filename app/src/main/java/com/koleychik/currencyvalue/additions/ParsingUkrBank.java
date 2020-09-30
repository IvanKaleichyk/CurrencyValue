package com.koleychik.currencyvalue.additions;

import android.util.Log;

import com.koleychik.currencyvalue.model.Currencies;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParsingUkrBank {

    public ArrayList<String[]> list = new ArrayList<>();

    public List<Currencies> currenciesList;

    public void start() {
        String baseUrl = "https://minfin.com.ua/currency/nbu/";
        String url = baseUrl + getUrlDate();

        Document document;
        try {
            document = getDocument(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Element main = getMainElement(document, "table-auto");

        Elements currencies_items = getElements(main, "row--collapse");

        list = getItemsList(currencies_items);
        currenciesList = getItemsCurrenciesList(currencies_items);

        addAdditionToList(document);

        currenciesList = makeRight(currenciesList);

        Log.d("list", String.valueOf(currenciesList));
    }

    private void addAdditionToList(Document document) {
        currenciesList.addAll(allListAddition(document));
    }

    private List<Currencies> makeRight(List<Currencies> list){
        List<Currencies> listRes = new ArrayList<>();
        for (Currencies i : list){
            i.setValue(fixValue(i.getValue()));
            Log.d(Keys.APP, "i.getValue() "+ i.getValue());
            listRes.add(i);
        }
        return listRes;
    }

    private String fixValue(String value){
        String[] arr = value.split(" ");
        return arr[0];
    }

    //  get list with all addition currencies
    private List<Currencies> allListAddition(Document document) {
        Element element = document.getElementsByClass("mfm-responsive-table").get(1);
        List<Currencies> list = new ArrayList<>();
        list.add(getBelarusianCurrencies(element));
        return list;
    }

    private Currencies getBelarusianCurrencies(Element element) {
        Element el = element.getElementsByClass("row--collapse").get(3);
        return new Currencies(getName(el, "/currency/nbu/byn/"), getValue(el, "data-title"));
    }

    private String getName(Element element, String hrefName) {
        return element.select("a").text();
    }

    private String getValue(Element element, String className) {
        return element.getElementsByClass(className).get(0).text();
    }

    private List<Currencies> getItemsCurrenciesList(Elements elements) {
        List<Currencies> list = new ArrayList<>();
        int num = 0;
        for (Element element : elements) {
            if (num > 0) {
                list.add(getItemCurrenciesInfo(element.child(0), "data-title"));
            } else {
                num++;
            }
        }
        return list;
    }

    private ArrayList<String[]> getItemsList(Elements elements) {
        ArrayList<String[]> list = new ArrayList<>();
        int num = 0;
        for (Element element : elements) {
            if (num > 0) {
                list.add(getItemInfo(element.child(0), "data-title", 0));
            } else {
                num++;
            }
        }
        return list;
    }

    private Currencies getItemCurrenciesInfo(Element element, String price_class_name) {
        return new Currencies(
                String.valueOf(element.select("a").text()),
                element.getElementsByClass(price_class_name).get(0).text());
    }

    private String[] getItemInfo(Element element, String price_class_name, int num) {
        String[] mas = new String[2];

        mas[0] = String.valueOf(element.select("a").text());
        mas[1] = element.getElementsByClass(price_class_name).get(0).text();

        System.out.println(mas[0] + " " + mas[1]);

        return mas;
    }

    private Elements getElements(Element element, String class_name) {
        return element.getElementsByClass(class_name);
    }

    private Element getMainElement(Document document, String class_name) {
        return document.getElementsByClass("table-auto").get(0);
    }

    private Document getDocument(String url) throws IOException {

        return Jsoup.connect(url).get();
    }

    public static String getUrlDate() {
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd ");
        return formatForDateNow.format(dateNow);
    }
}