/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package time;

import com.github.eloyzone.jalalicalendar.DateConverter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import jhazm.Normalizer;
import xpath.ReturnXpathText;
import org.apache.log4j.Logger;

/**
 *
 * @author baghe
 */
public class TextToTime {

    private static final Logger LOGGER = Logger.getLogger(TextToTime.class);

    public static void main(String[] args) throws IOException {
        TextToTime ttt = new TextToTime();
        ttt.isna("isna");
        ttt.irna("irna");
        ttt.jamejam("jamejam");
    }

    public String returnMilliseconds(String ag, String publishdatetime) throws IOException {
        if (ag.trim().equalsIgnoreCase("isna")) {
            return Long.toString(isna(publishdatetime));
        } else if (ag.trim().equalsIgnoreCase("irna")) {
            return Long.toString(irna(publishdatetime));
        } else if (ag.trim().equalsIgnoreCase("jamejam")) {
            return Long.toString(jamejam(publishdatetime));
        } else if (ag.trim().equalsIgnoreCase("ilna")) {
            return Long.toString(ilna(publishdatetime));
        } else if (ag.trim().equalsIgnoreCase("mojnews")) {
            return Long.toString(mojnews(publishdatetime));
        } else if (ag.trim().equalsIgnoreCase("fars")) {
            return Long.toString(fars(publishdatetime));
        } else if (ag.trim().equalsIgnoreCase("tasnim")) {
            return Long.toString(tasnim(publishdatetime));
        }else if (ag.trim().equalsIgnoreCase("tarafdari")) {
            return Long.toString(tarafdari(publishdatetime));
        }else if (ag.trim().equalsIgnoreCase("varzesh3")) {
            return Long.toString(varzesh3(publishdatetime));
        }else if (ag.trim().equalsIgnoreCase("tabnak")) {
            return Long.toString(tabnak(publishdatetime));
        }else if (ag.trim().equalsIgnoreCase("entekhab")) {
            return Long.toString(entekhab(publishdatetime));
        }
        LOGGER.info(ag + "  " + publishdatetime);
        return "-1";
    }

    public long isna(String textDate) throws IOException {
        try {
            String s1[] = textDate.trim().split("/");
            String s2[] = s1[0].split(" ");
            String s3[] = s1[1].split(":");
            String en = ConvertDigitsToLatin(s2[0].trim());
            int day = Integer.parseInt(en);
            int month = numberOfMonth(s2[1].trim());
            int year = Integer.parseInt(ConvertDigitsToLatin(s2[2].trim()));
            int hour = Integer.parseInt(ConvertDigitsToLatin(s3[0].trim()));
            int min = Integer.parseInt(ConvertDigitsToLatin(s3[1].trim()));
            DateConverter dateConverter = new DateConverter();
            LocalDate localdate1 = dateConverter.jalaliToGregorian(year, month, day);
            LocalDateTime ldt = LocalDateTime.of(localdate1.getYear(), localdate1.getMonth(), localdate1.getDayOfMonth(), hour, min);
            ZoneId zoneId = ZoneId.of("Asia/Tehran");
            ZonedDateTime zdt = ZonedDateTime.of(ldt, zoneId);
            return zdt.toEpochSecond() * 1000;
        } catch (Exception e) {
            LOGGER.error(e);
            LOGGER.error("isna " + textDate);
            e.printStackTrace();
            return -1;
        }
    }
public long tabnak(String textDate) throws IOException {
        try {
//            textDate = textDate.replaceAll("\\s", "");
//            textDate = ConvertDigitsToLatin(textDate);
//            textDate = textDate.replaceAll("(^\\h*)|(\\h*$)", "");
//            textDate = textDate.replace('\u00A0', ' ').trim();
            String s1[] = textDate.trim().split("-");
            String s2[] = s1[0].split(" ");
            String s3[] = s1[1].split(":");
            String en = ConvertDigitsToLatin(s2[0].trim());
            int day = Integer.parseInt(en);
            int month = numberOfMonth(s2[1].trim());
            int year = Integer.parseInt(ConvertDigitsToLatin(s2[2].trim()));
            int hour = Integer.parseInt(ConvertDigitsToLatin(s3[0].trim()));
            int min = Integer.parseInt(ConvertDigitsToLatin(s3[1].trim()));
            LOGGER.info("tabnak year: " + year + " month:" + month + " day: " + day);
            LOGGER.info("tabnak hour: " + hour + " minute:" + min);
            DateConverter dateConverter = new DateConverter();
            LocalDate localdate1 = dateConverter.jalaliToGregorian(year, month, day);
            LocalDateTime ldt = LocalDateTime.of(localdate1.getYear(), localdate1.getMonth(), localdate1.getDayOfMonth(), hour, min);
            ZoneId zoneId = ZoneId.of("Asia/Tehran");
            ZonedDateTime zdt = ZonedDateTime.of(ldt, zoneId);
            return zdt.toEpochSecond() * 1000;
        } catch (Exception e) {
            LOGGER.error(e);
            LOGGER.error("tabnak: " + textDate);
            e.printStackTrace();
            return -1;
        }
    }
public long entekhab(String textDate) throws IOException {
        try {
            LOGGER.info("entekhab: "+textDate);
            textDate=textDate.replaceAll("تاریخ انتشار:", "").trim();
            LOGGER.info("entekhab: "+textDate);
             textDate = textDate.replace('\u1779', ' ').trim();
              textDate = textDate.replace('\u1778', ' ').trim();
              textDate=textDate.replaceAll("  ", " ");
              textDate=ConvertDigitsToLatin(textDate.trim());
//            textDate = textDate.replaceAll("\\s", "");
//            textDate = ConvertDigitsToLatin(textDate);
//            textDate = textDate.replaceAll("(^\\h*)|(\\h*$)", "");
//            textDate = textDate.replace('\u00A0', ' ').trim();
            String s1[] = textDate.trim().split("-");
            for(String el:s1){
                LOGGER.info("s1: " + el);
                printcharbychar(el);
            }
            String s2[] = s1[0].trim().split(":");
            String s3[] = s1[1].trim().split(" ");
            for(String el:s2)
                el=el.trim();
            for(String el:s3)
                el=el.trim();
            int day = Integer.parseInt(ConvertDigitsToLatin(s3[0].trim()));
            int month = numberOfMonth(s3[1].trim());
            int year = Integer.parseInt(ConvertDigitsToLatin(s3[2].trim()));
            int min = Integer.parseInt(ConvertDigitsToLatin(s2[0].trim()));
            int hour = Integer.parseInt(ConvertDigitsToLatin(s2[1].trim()));
            LOGGER.info("entekhab year: " + year + " month:" + month + " day: " + day);
            LOGGER.info("entekhab hour: " + hour + " minute:" + min);
            DateConverter dateConverter = new DateConverter();
            LocalDate localdate1 = dateConverter.jalaliToGregorian(year, month, day);
            LocalDateTime ldt = LocalDateTime.of(localdate1.getYear(), localdate1.getMonth(), localdate1.getDayOfMonth(), hour, min);
            ZoneId zoneId = ZoneId.of("Asia/Tehran");
            ZonedDateTime zdt = ZonedDateTime.of(ldt, zoneId);
            return zdt.toEpochSecond() * 1000;
        } catch (Exception e) {
            LOGGER.error(e);
//            LOGGER.error("entekhab: " + textDate + "date: " +s1[0]. + "time: " +s1[1]);
            e.printStackTrace();
            return -1;
        }
    }
    public long irna(String textDate) throws IOException {
        try {
            String s1[] = textDate.trim().split("،‏");
            String s2[] = s1[0].trim().split(" ");
            String s3[] = s1[1].trim().split(":");
            String en = ConvertDigitsToLatin(s2[0].trim());
            int day = Integer.parseInt(en);
            int month = numberOfMonth(s2[1].trim());
            int year = Integer.parseInt(ConvertDigitsToLatin(s2[2].trim()));
            int hour = Integer.parseInt(ConvertDigitsToLatin(s3[0].trim()));
            int min = Integer.parseInt(ConvertDigitsToLatin(s3[1].trim()));
            DateConverter dateConverter = new DateConverter();
            LocalDate localdate1 = dateConverter.jalaliToGregorian(year, month, day);
            LocalDateTime ldt = LocalDateTime.of(localdate1.getYear(), localdate1.getMonth(), localdate1.getDayOfMonth(), hour, min);
            ZoneId zoneId = ZoneId.of("Asia/Tehran");
            ZonedDateTime zdt = ZonedDateTime.of(ldt, zoneId);
            return zdt.toEpochSecond() * 1000;
        } catch (Exception e) {
            LOGGER.error(e);
            LOGGER.error("irna " + textDate);
            e.printStackTrace();
            return -1;
        }
    }

    public long jamejam(String textDate) throws IOException {
        try {
            String s1[] = textDate.trim().split("\\|");
            String s2[] = s1[0].trim().split(" ");
            String s3[] = s1[1].trim().split(":");
            String en = ConvertDigitsToLatin(s2[0].trim());
            int day = Integer.parseInt(en);
            int month = numberOfMonth(s2[1].trim());
            int year = Integer.parseInt(ConvertDigitsToLatin(s2[2].trim()));
            int hour = Integer.parseInt(ConvertDigitsToLatin(s3[0].trim()));
            int min = Integer.parseInt(ConvertDigitsToLatin(s3[1].trim()));
            DateConverter dateConverter = new DateConverter();
            LocalDate localdate1 = dateConverter.jalaliToGregorian(year, month, day);
            LocalDateTime ldt = LocalDateTime.of(localdate1.getYear(), localdate1.getMonth(), localdate1.getDayOfMonth(), hour, min);
            ZoneId zoneId = ZoneId.of("Asia/Tehran");
            ZonedDateTime zdt = ZonedDateTime.of(ldt, zoneId);
            return zdt.toEpochSecond() * 1000;
        } catch (Exception e) {
            LOGGER.error(e);
            LOGGER.error("jamejam " + textDate);
            e.printStackTrace();
            return -1;
        }
    }
public long tasnim(String textDate) throws IOException {
        try {
            String s1[] = textDate.trim().split("-");
            String s2[] = s1[0].trim().split(" ");
            String s3[] = s1[1].trim().split(":");
            String en = ConvertDigitsToLatin(s2[0].trim());
            int day = Integer.parseInt(en);
            int month = numberOfMonth(s2[1].trim());
            int year = Integer.parseInt(ConvertDigitsToLatin(s2[2].trim()));
            int hour = Integer.parseInt(ConvertDigitsToLatin(s3[0].trim()));
            int min = Integer.parseInt(ConvertDigitsToLatin(s3[1].trim()));
            LOGGER.info("tasnim year: " + year + " month:" + month + " day: " + day);
            LOGGER.info("tasnim hour: " + hour + " minute:" + min);
            DateConverter dateConverter = new DateConverter();
            LocalDate localdate1 = dateConverter.jalaliToGregorian(year, month, day);
            LocalDateTime ldt = LocalDateTime.of(localdate1.getYear(), localdate1.getMonth(), localdate1.getDayOfMonth(), hour, min);
            ZoneId zoneId = ZoneId.of("Asia/Tehran");
            ZonedDateTime zdt = ZonedDateTime.of(ldt, zoneId);
            return zdt.toEpochSecond() * 1000;
        } catch (Exception e) {
            LOGGER.error(e);
            LOGGER.error("tasnim: " + textDate);
            e.printStackTrace();
            return -1;
        }
    }
public long varzesh3(String textDate) throws IOException {
        try {
            textDate = textDate.replaceAll("\\s", "");
            textDate = ConvertDigitsToLatin(textDate);
            textDate = textDate.replaceAll("(^\\h*)|(\\h*$)", "");
            textDate = textDate.replace('\u00A0', ' ').trim();
            String s1[] = textDate.trim().split("-");
            String s2[] = s1[0].trim().split("/");
            String s3[] = s1[1].trim().split(":");
            String en = ConvertDigitsToLatin(s2[0].trim());
            int year = Integer.parseInt(en);
            int month = Integer.parseInt(ConvertDigitsToLatin(s2[1].trim()));
            int day = Integer.parseInt(ConvertDigitsToLatin(s2[2].trim()));
            int hour = Integer.parseInt(ConvertDigitsToLatin(s3[0].trim()));
            int min = Integer.parseInt(ConvertDigitsToLatin(s3[1].trim()));
            LOGGER.info("varzesh3 year: " + year + " month:" + month + " day: " + day);
            LOGGER.info("varzesh3 hour: " + hour + " minute:" + min);
            DateConverter dateConverter = new DateConverter();
            LocalDate localdate1 = dateConverter.jalaliToGregorian(year, month, day);
            LocalDateTime ldt = LocalDateTime.of(localdate1.getYear(), localdate1.getMonth(), localdate1.getDayOfMonth(), hour, min);
            ZoneId zoneId = ZoneId.of("Asia/Tehran");
            ZonedDateTime zdt = ZonedDateTime.of(ldt, zoneId);
            return zdt.toEpochSecond() * 1000;
        } catch (Exception e) {
            LOGGER.error(e);
            LOGGER.error("varzesh3: " + textDate);
            e.printStackTrace();
            return -1;
        }
    }

public long tarafdari(String textDate) throws IOException {
        try {
            String s1[] = textDate.trim().split("-");
            String s2[] = s1[0].trim().split("/");
            String s3[] = s1[1].trim().split(":");
            String en = ConvertDigitsToLatin(s2[0].trim());
            int month = Integer.parseInt(en);
            int day = Integer.parseInt(ConvertDigitsToLatin(s2[1].trim()));
            int year = Integer.parseInt(ConvertDigitsToLatin(s2[2].trim()));
            int hour = Integer.parseInt(ConvertDigitsToLatin(s3[0].trim()));
            int min = Integer.parseInt(ConvertDigitsToLatin(s3[1].trim()));
            LOGGER.info("tarafdari year: " + year + " month:" + month + " day: " + day);
            LOGGER.info("tarafdari hour: " + hour + " minute:" + min);
//            DateConverter dateConverter = new DateConverter();
//            LocalDate localdate1 = dateConverter.jalaliToGregorian(year, month, day);
            LocalDateTime ldt = LocalDateTime.of(year, month, day, hour, min);
            ZoneId zoneId = ZoneId.of("Asia/Tehran");
            ZonedDateTime zdt = ZonedDateTime.of(ldt, zoneId);
            return zdt.toEpochSecond() * 1000;
        } catch (Exception e) {
            LOGGER.error(e);
            LOGGER.error("tarafdari: " + textDate);
            e.printStackTrace();
            return -1;
        }
    }

    public long ilna(String textDate) throws IOException {
        try {
            String s1[] = textDate.trim().split(" ");
            String s2[] = s1[0].trim().split("/");
            String s3[] = s1[1].trim().split(":");
            String en = ConvertDigitsToLatin(s2[0].trim());
            int year = Integer.parseInt(en);
            int month = Integer.parseInt(ConvertDigitsToLatin(s2[1].trim()));
            int day = Integer.parseInt(ConvertDigitsToLatin(s2[2].trim()));
            int hour = Integer.parseInt(ConvertDigitsToLatin(s3[0].trim()));
            int min = Integer.parseInt(ConvertDigitsToLatin(s3[1].trim()));
            LOGGER.info("ilna year: " + ConvertDigitsToLatin(s2[0].trim()) + " month:" + ConvertDigitsToLatin(s2[1].trim()) + " day: " + ConvertDigitsToLatin(s2[2].trim()));
            LOGGER.info("ilna hour: " + ConvertDigitsToLatin(s3[0].trim()) + " minute:" + ConvertDigitsToLatin(s3[1].trim()));
            DateConverter dateConverter = new DateConverter();
            LocalDate localdate1 = dateConverter.jalaliToGregorian(year, month, day);
            LocalDateTime ldt = LocalDateTime.of(localdate1.getYear(), localdate1.getMonth(), localdate1.getDayOfMonth(), hour, min);
            ZoneId zoneId = ZoneId.of("Asia/Tehran");
            ZonedDateTime zdt = ZonedDateTime.of(ldt, zoneId);
            return zdt.toEpochSecond() * 1000;
        } catch (Exception e) {
            LOGGER.error(e);
            LOGGER.error("ilna " + textDate);
            e.printStackTrace();
            return -1;
        }
    }

    public long mojnews(String textDate) throws IOException {
        try {
            String s1[] = textDate.trim().split(" ");
            LOGGER.info("mojnews date: " + s1[0] + " time:" + s1[1]);
            String s2[] = s1[0].trim().split("/");
            String s3[] = s1[1].trim().split(":");
            String en = ConvertDigitsToLatin(s2[0].trim());
            LOGGER.info("mojnews year: " + ConvertDigitsToLatin(s2[0].trim()) + " month:" + ConvertDigitsToLatin(s2[1].trim()) + " day: " + ConvertDigitsToLatin(s2[2].trim()));
            int year = Integer.parseInt(en);
            int month = Integer.parseInt(ConvertDigitsToLatin(s2[1].trim()));
            int day = Integer.parseInt(ConvertDigitsToLatin(s2[2].trim()));
            int hour = Integer.parseInt(ConvertDigitsToLatin(s3[0].trim()));
            int min = Integer.parseInt(ConvertDigitsToLatin(s3[1].trim()));
            LOGGER.info("mojnews hour: " + ConvertDigitsToLatin(s3[0].trim()) + " minute:" + ConvertDigitsToLatin(s3[1].trim()));
            DateConverter dateConverter = new DateConverter();
            LocalDate localdate1 = dateConverter.jalaliToGregorian(year, month, day);
            LocalDateTime ldt = LocalDateTime.of(localdate1.getYear(), localdate1.getMonth(), localdate1.getDayOfMonth(), hour, min);
            ZoneId zoneId = ZoneId.of("Asia/Tehran");
            ZonedDateTime zdt = ZonedDateTime.of(ldt, zoneId);
            return zdt.toEpochSecond() * 1000;
        } catch (Exception e) {
            LOGGER.error(e);
            LOGGER.error("mojnews " + textDate);
            e.printStackTrace();
            return -1;
        }
    }

    public long fars(String textDate) throws IOException {
        try {
            textDate = textDate.replaceAll("\\s", "");
            textDate = ConvertDigitsToLatin(textDate);
            textDate = textDate.replaceAll("(^\\h*)|(\\h*$)", "");
            textDate = textDate.replace('\u00A0', ' ').trim();
            printcharbychar(textDate);
            String s1[] = textDate.trim().split("-");
            LOGGER.info("fars date: " + s1[1] + " time: " + s1[0]);
            String s3[] = s1[0].trim().split(":");
            String s2[] = s1[1].trim().split("/");
            String en = ConvertDigitsToLatin(s2[0].trim());
            LOGGER.info("fars year: " + ConvertDigitsToLatin(s2[0].trim()) + " month:" + ConvertDigitsToLatin(s2[1].trim()) + " day: " + ConvertDigitsToLatin(s2[2].trim()));
            int year = Integer.parseInt(en);
            int month = Integer.parseInt(ConvertDigitsToLatin(s2[1].trim()));
            int day = Integer.parseInt(ConvertDigitsToLatin(s2[2].trim()));
            int hour = Integer.parseInt(ConvertDigitsToLatin(s3[0].trim()));
            int min = Integer.parseInt(ConvertDigitsToLatin(s3[1].trim()));
            LOGGER.info("fars hour: " + ConvertDigitsToLatin(s3[0].trim()) + " minute:" + ConvertDigitsToLatin(s3[1].trim()));
            DateConverter dateConverter = new DateConverter();
            LocalDate localdate1 = dateConverter.jalaliToGregorian(year, month, day);
            LocalDateTime ldt = LocalDateTime.of(localdate1.getYear(), localdate1.getMonth(), localdate1.getDayOfMonth(), hour, min);
            ZoneId zoneId = ZoneId.of("Asia/Tehran");
            ZonedDateTime zdt = ZonedDateTime.of(ldt, zoneId);
            return zdt.toEpochSecond() * 1000;
        } catch (Exception e) {
            LOGGER.error(e);
            LOGGER.error("fars " + textDate);
            e.printStackTrace();
            return -1;
        }
    }

    public int numberOfMonth(String s) {
        Normalizer normal = new Normalizer();
        s = normal.run(s);
        if (s.trim().equalsIgnoreCase(normal.run("فروردین"))) {
            return 1;
        } else if (s.trim().equalsIgnoreCase(normal.run("اردیبهشت"))) {
            return 2;
        } else if (s.trim().equalsIgnoreCase(normal.run("خرداد"))) {
            return 3;
        } else if (s.trim().equalsIgnoreCase(normal.run("تیر"))) {
            return 4;
        } else if (s.trim().equalsIgnoreCase(normal.run("مرداد"))) {
            return 5;
        } else if (s.trim().equalsIgnoreCase(normal.run("شهریور"))) {
            return 6;
        } else if (s.trim().equalsIgnoreCase(normal.run("مهر"))) {
            return 7;
        } else if (s.trim().equalsIgnoreCase(normal.run("آبان"))) {
            return 8;
        } else if (s.trim().equalsIgnoreCase(normal.run("آذر"))) {
            return 9;
        } else if (s.trim().equalsIgnoreCase(normal.run("دی"))) {
            return 10;
        } else if (s.trim().equalsIgnoreCase(normal.run("بهمن"))) {
            return 11;
        } else if (s.trim().equalsIgnoreCase(normal.run("اسفند"))) {
            return 12;
        } else {
            LOGGER.info("month name error :" + s);
            return -1;
        }
    }

    public static String ConvertDigitsToLatin(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char hi = s.charAt(i);
            switch (hi) {
                //Persian digits
                case '\u06f0':
                    sb.append('0');
                    break;
                case '\u06f1':
                    sb.append('1');
                    break;
                case '\u06f2':
                    sb.append('2');
                    break;
                case '\u06f3':
                    sb.append('3');
                    break;
                case '\u06f4':
                    sb.append('4');
                    break;
                case '\u06f5':
                    sb.append('5');
                    break;
                case '\u06f6':
                    sb.append('6');
                    break;
                case '\u06f7':
                    sb.append('7');
                    break;
                case '\u06f8':
                    sb.append('8');
                    break;
                case '\u06f9':
                    sb.append('9');
                    break;

                //Arabic digits    
                case '\u0660':
                    sb.append('0');
                    break;
                case '\u0661':
                    sb.append('1');
                    break;
                case '\u0662':
                    sb.append('2');
                    break;
                case '\u0663':
                    sb.append('3');
                    break;
                case '\u0664':
                    sb.append('4');
                    break;
                case '\u0665':
                    sb.append('5');
                    break;
                case '\u0666':
                    sb.append('6');
                    break;
                case '\u0667':
                    sb.append('7');
                    break;
                case '\u0668':
                    sb.append('8');
                    break;
                case '\u0669':
                    sb.append('9');
                    break;
                default:
                    sb.append(hi);
                    break;
            }
        }
        LOGGER.info(s + "     :::    " + sb.toString());
        return sb.toString();
    }

    public void printcharbychar(String s) {
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            LOGGER.info(i + "st character is: " + (int) c);
            LOGGER.info(i + "st character is: " + c);
            if (c == '|') {
                LOGGER.info("vertical line detected");
            }
        }
    }

    public long datetimeextractorforcopy(String textDate) throws IOException {
        ReturnXpathText rxt = new ReturnXpathText("https://jamejamonline.ir/fa/news/1268389/%DA%86%D8%A7%D9%87-%D8%B3%D9%86%DA%AF%DB%8C-%D9%85%D8%AA%D8%B9%D9%84%D9%82-%D8%A8%D9%87-%D8%AF%D9%88%D8%B1%D9%87-%D8%B3%D8%A7%D8%B3%D8%A7%D9%86%DB%8C-%D8%AF%D8%B1-%D8%A7%D8%B7%D8%B1%D8%A7%D9%81-%D8%AA%D9%BE%D9%87-%D8%A7%D8%B4%D8%B1%D9%81-%D8%A7%D8%B5%D9%81%D9%87%D8%A7%D9%86-%DA%A9%D8%B4%D9%81-%D8%B4%D8%AF");
        String p = rxt.textOfXpath("//*[@id=\"news\"]/section[2]/div/div[1]/article/section[1]/span[3]","publishedtime");
        LOGGER.info(p);
        printcharbychar(p);
        String s = ConvertDigitsToLatin(p);
        LOGGER.info(s);
        LOGGER.info(s.length());
        LOGGER.info(s.trim().length());
        //String nbsp = "&nbsp;";
        //s.replaceAll(nbsp, " ");
        LOGGER.info(s.trim().length());
        String s1[] = s.split("\\|");
        LOGGER.info(s1[0]);
        for (int i = 0; i < s1.length; i++) {
            System.out.print(s1[i]);
        }

        System.out.println("time.TextToTime.jamejam()");
        //LOGGER.info(s1[1]);
        String s2[] = s1[0].trim().split(" ");
        String s3[] = s1[1].trim().split(":");
        LOGGER.info(textDate);
        LOGGER.info(s1[0]);
        LOGGER.info(s1.length);
        LOGGER.info(s2.length);
        LOGGER.info(s3.length);
        for (String s4 : s2) {
            LOGGER.info(s4.trim());
        }
        LOGGER.info("-----------------------------------------------------------------------------------------------------------------------------------------");
        for (String s4 : s3) {
            LOGGER.info(s4.trim());
        }
        LOGGER.info("-----------------------------------------------------------------------------------------------------------------------------------------");
        LOGGER.info(s2[0].trim().length());
        String en = ConvertDigitsToLatin(s2[0].trim());
        LOGGER.info(en);
        int day = Integer.parseInt(en);
        LOGGER.info(s2[1].trim());
        int month = numberOfMonth(s2[1].trim());
        int year = Integer.parseInt(ConvertDigitsToLatin(s2[2].trim()));
        int hour = Integer.parseInt(ConvertDigitsToLatin(s3[0].trim()));
        int min = Integer.parseInt(ConvertDigitsToLatin(s3[1].trim()));
        DateConverter dateConverter = new DateConverter();
        LocalDate localdate1 = dateConverter.jalaliToGregorian(year, month, day);
        LocalDateTime ldt = LocalDateTime.of(localdate1.getYear(), localdate1.getMonth(), localdate1.getDayOfMonth(), hour, min);
        ZoneId zoneId = ZoneId.of("Asia/Tehran");
        ZonedDateTime zdt = ZonedDateTime.of(ldt, zoneId);
        LOGGER.info(ldt);
        LOGGER.info(zoneId);
        LOGGER.info(zdt);
        LOGGER.info(zdt.toEpochSecond() * 1000);
        return zdt.toEpochSecond() * 1000;
    }
}
