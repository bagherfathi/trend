<%-- 
    Document   : w3test.jsp
    Created on : Aug 5, 2020, 9:11:03 PM
    Author     : baghe
--%>
<%@page import="jhazm.Normalizer"%>
<%@page import="java.util.Iterator"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.TreeMap" %>
<%@ page import="static net.sourceforge.htmlunit.corejs.javascript.Context.exit" %>
<%@ page import="org.elasticsearch.cluster.routing.DelayedAllocationService" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.fff.trendmanager.trend.saveRequests" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html dir="rtl">
    <head>
  <meta charset="UTF-8">
  <title>موضوعات داغ خبری</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <script src="https://code.highcharts.com/highcharts.js"></script>
        <script src="https://code.highcharts.com/highcharts-more.js"></script>
        <script src="https://code.highcharts.com/modules/exporting.js"></script>
        <script src="https://code.highcharts.com/modules/accessibility.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <style>
            .highcharts-figure, .highcharts-data-table table {
                min-width: 320px;
                max-width: 800px;
                margin: 1em auto;
            }

            .highcharts-data-table table {
                font-family: Verdana, sans-serif;
                border-collapse: collapse;
                border: 1px solid #EBEBEB;
                margin: 10px auto;
                text-align: center;
                width: 100%;
                max-width: 500px;
            }
            .highcharts-data-table caption {
                padding: 1em 0;
                font-size: 1.2em;
                color: #555;
            }
            .highcharts-data-table th {
                font-weight: 600;
                padding: 0.5em;
            }
            .highcharts-data-table td, .highcharts-data-table th, .highcharts-data-table caption {
                padding: 0.5em;
            }
            .highcharts-data-table thead tr, .highcharts-data-table tr:nth-child(even) {
                background: #f8f8f8;
            }
            .highcharts-data-table tr:hover {
                background: #f1f7ff;
            }
            .col-75-old {
                float: left;
                width: 100%;
                margin-top: 6px;
                text-align: center;
            }

            /* Clear floats after the columns */
            .row-old:after {
                content: "";
                display: table;
                clear: both;
            }
        </style>
</head> 

<body>
<%
    saveRequests sr=new saveRequests();
    sr.indexRequest(request);
    response.setCharacterEncoding("UTF-8");
    String agency="همه";
    int ph=24;
    if(request.getParameter("h")!=null)
        ph=Integer.parseInt(request.getParameter("h"));
    if(request.getParameter("a")!=null)
        agency=request.getParameter("a").trim();
    String url = "http://localhost:9200/fetchednews/_search";
    String json="{\n" +
            "  \"aggs\": {\n" +
            "    \"keys\": {\n" +
            "      \"terms\": {\n" +
            "        \"field\": \"name\",\n" +
            "        \"size\": 10000\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  \"size\": 0\n" +
            "}\n";
    String s = com.fff.trendmanager.trend.NestedJSONObjectTest.sendJsonPOST(url, json,"elastic","tbontb");
    java.util.TreeMap result0= com.fff.trendmanager.trend.NestedJSONObjectTest.extractAgenciesFromJson(s);
    Iterator it0=result0.descendingKeySet().iterator();
    %>
<div class="w3-container w3-center">
    <a href="http://87.247.176.67:8080" style="font-size:25px ;color:blue";>87.247.176.67:8080</a>
    <form action="hottopics.jsp">
        <div class="row-old">
            <div class="col-75-old">
                <label for="pasthour">ساعت گذشته:</label>
                <select id="h" name="h">
                    <option value="1" <% if(ph==1) out.write("selected");%> >1</option>
                    <option value="2" <% if(ph==2) out.write("selected");%> >2</option>
                    <option value="4" <% if(ph==4) out.write("selected");%>>4</option>
                    <option value="6" <% if(ph==6) out.write("selected");%>>6</option>
                    <option value="8" <% if(ph==8) out.write("selected");%>>8</option>
                    <option value="12" <% if(ph==12) out.write("selected");%>>12</option>
                    <option value="24" <% if(ph==24) out.write("selected");%>>24</option>
                </select>
                <label for="agency">خبرگزاری:</label>
                <select id="a" name="a">
                    <% if(agency.equals("همه")) out.write("<option value=\"همه\" selected>همه</option>\n");
                    else out.write("<option value=\"همه\">همه</option>\n");
                       for(;it0.hasNext();){
                           int nn= (int) it0.next();
                           String ag= (String) result0.get(nn);
                           if(ag.trim().equals(agency))
                                out.write("<option value=\"" + ag + "\" selected>" + ag + "</option>\n");
                           else
                               out.write("<option value=\"" + ag + "\" >" + ag + "</option>\n");
                       }
                    %>
                </select>
                <input type="submit" value="جستجو">
            </div>
        </div>
    </form>
  <%
      SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
      Date date = new Date(System.currentTimeMillis()- ph * 3600 * 1000);
      date.setMinutes(date.getMinutes()-date.getMinutes()%10);
      Date dateNow=new Date(System.currentTimeMillis()-1*60*1000);
      dateNow.setMinutes(dateNow.getMinutes()-dateNow.getMinutes()%10);
      Date dateBackground=new Date(System.currentTimeMillis()- 70 * 24 * 3600 * 1000);
      dateBackground.setMinutes(dateBackground.getMinutes()-dateBackground.getMinutes()%10);
      Date dateBackgroundAgency=new Date(System.currentTimeMillis()- 70 * 24 * 3600 * 1000);
      dateBackgroundAgency.setMinutes(dateBackgroundAgency.getMinutes()-dateBackgroundAgency.getMinutes()%10);
      System.out.println(formatter.format(date));
      System.out.println(formatter.format(dateNow));
      url = "http://localhost:9200/fetchednews/_search";
      Normalizer normal=new Normalizer();
      if(!agency.equals("همه"))
      json="{\n" +
              "  \"query\": {\n" +
              "    \"bool\": {\n" +
              "      \"must\": [\n" +
              "        {\n" +
              "        \"range\" : {\n" +
              "            \"fetchtime\" : {\n" +
              "               \"time_zone\": \"Asia/Tehran\",\n" +
              "                \"gte\" : \"" + formatter.format(date) + "\",\n" +
              "                \"lte\" : \"" + formatter.format(dateNow) + "\"\n" +
              "            }\n" +
              "        }\n" +
              "    },\n" +
              "        {\n" +
              "          \"term\": {\n" +
              "            \"name\": {\n" +
              "              \"value\": \""+ normal.run(agency) + "\"\n" +
              "            }\n" +
              "          }\n" +
              "        }\n" +
              "      ]\n" +
              "    }\n" +
              "  },\n" +
              "    \"size\": 0, \n" +
              "  \"aggregations\": {\n" +
              "    \"sample\": {\n" +
              "      \"sampler\": {\n" +
              "        \"shard_size\": 10000\n" +
              "      },\n" +
              "      \"aggregations\": {\n" +
              "        \"keywords\": {\n" +
              "          \"significant_text\": {\n" +
              "            \"field\": \"description\",\n" +
              "        \"background_filter\": {\n" +
              "    \"bool\": {\n" +
              "      \"must\": [\n" +
              "        {\n" +
              "        \"range\" : {\n" +
              "            \"fetchtime\" : {\n" +
              "               \"time_zone\": \"Asia/Tehran\",\n" +
              "                \"gte\" : \"" + formatter.format(dateBackgroundAgency) + "\"\n" +
              "            }\n" +
              "        }\n" +
              "    },\n" +
              "        {\n" +
              "          \"term\": {\n" +
              "            \"name\": {\n" +
              "              \"value\": \""+ normal.run(agency) + "\"\n" +
              "            }\n" +
              "          }\n" +
              "        }\n" +
              "      ]\n" +
              "    }\n" +
              "        },                                    " +
              "            \"filter_duplicate_text\": true\n" +
              "          }\n" +
              "        }\n" +
              "      }\n" +
              "    }\n" +
              "  }\n" +
              "}";
      else
      json = "{\n"
                + "  \"query\": " +
              "{\n"
                + "        \"range\" : {\n"
                + "            \"fetchtime\" : {\n"
                + "				\"time_zone\": \"Asia/Tehran\",\n"
                + "\"gte\" : \"" + formatter.format(date) + "\"\n"
              + ", \"lte\" : \"" + formatter.format(dateNow) + "\"\n"
                + "            }\n"
                + "        }\n"
                + "    },\n"
                + "    \"size\": 0, \n"
                + "  \"aggregations\": {\n"
                + "    \"sample\": {\n"
                + "      \"sampler\": {\n"
                + "        \"shard_size\": 10000\n"
                + "      },\n"
                + "      \"aggregations\": {\n"
                + "        \"keywords\": {\n"
                + "          \"significant_text\": {\n"
                + "            \"field\": \"description\",\n" +
      "        \"background_filter\": {\n" +
              "          \"range\": { \"fetchtime\": {\n" +
              "               \"time_zone\": \"Asia/Tehran\",\n" +
              "                \"gte\" : \"" + formatter.format(dateBackground) + "\"\n" +
              "            }\n" +
              "}\n" +
              "        },                                    "
                + "            \"filter_duplicate_text\": true\n"
                + "          }\n"
                + "        }\n"
                + "      }\n"
                + "    }\n"
                + "  }\n"
                + "}";
      System.out.println(json);
      s = com.fff.trendmanager.trend.NestedJSONObjectTest.sendJsonPOST(url, json,"elastic","tbontb");
        System.out.println(s);
        TreeMap result= com.fff.trendmanager.trend.NestedJSONObjectTest.extractTrendFromJson(s);
        Iterator it1=result.descendingKeySet().iterator();
        Float min=10000000F,max=0F;
        for(;it1.hasNext();)
        {
            Float temp=(Float) it1.next();
            if(temp<min) min=temp;
            if(temp>max) max=temp;
        }
//        it.remove();
      Iterator it=result.descendingKeySet().iterator();
      String agencyCaption;
      if(agency.equals("همه"))
          agencyCaption="";
      else
          agencyCaption=agency;
  %>

</div>
<figure class="highcharts-figure">
    <div id="container"></div>
</figure>
<script type="text/javascript">
Highcharts.chart('container', {
chart: {
type: 'packedbubble',
height: '55%'
},
    lang: {
        viewFullscreen: "تمام صفحه" ,
        downloadCSV: "دانلود سی اس وی",
        downloadJPEG: "دانلود تصویر جی پگ",
        downloadPDF: "دانلود پی دی اف",
        downloadPNG: "دانلود تصویر پی ان جی",
        downloadSVG: "دانلود اس وی جی",
        downloadXLS: "دانلود ایکس ال اس",
        exitFullscreen: "خروج از تمام صفحه",
        printChart: "پرینت نمودار",
        weekdays: ["یکشنبه", "دوشنبه", "سه شنبه", "چهارشنبه", "پنج شنبه", "جمعه", "شنبه"]
    },
title: {
    text: 'کلمات داغ <%=String.valueOf(ph)%> ساعت اخیر <%=agencyCaption%>',
    margin:0
},
tooltip: {
useHTML: true,
pointFormat: '<b>{point.name}</b>'
},
plotOptions: {
packedbubble: {
minSize: '10%',
maxSize: '150%',
zMin: 0,
zMax: 1,
layoutAlgorithm: {
splitSeries: false,
gravitationalConstant: 0.02
},
dataLabels: {
enabled: true,
format: '{point.name}',
filter: {
property: 'y',
operator: '>',
value: -1
},
style: {
color: 'black',
textOutline: 'none',
fontWeight: 'normal'
}
}
}
},
series: [

    <%
    ArrayList<String> als=new ArrayList<String>();
    boolean firsttimeflag=true;
    Float scoreNormalizer;
            for (;it.hasNext();) {
                if(firsttimeflag==true){
                    firsttimeflag=false;
                }
                else
                    out.write(",");
                Float score=(Float)it.next();
                scoreNormalizer=(score-min)/(max-min);
      %>

    {
name: '<%=result.get(score)%>',
        point: {
            events: {
                click: function () {
                    location.href="index.jsp?q=" + this.name
                }
            }
        },
data: [{
name: '<%=result.get(score)%>',
value: <%=scoreNormalizer%>,
}]
}
    <% als.add(result.get(score).toString());} %>
]
});
</script>
<p>
    <%for(int i=0;i<als.size();i++){%>
    <a href="index.jsp?q=<%=als.get(i)%>" ><%="#" + als.get(i)+ " ,"%></a>
    <% out.newLine();}
    %>
    <a href="http://87.247.176.67:8080/hottopics.jsp">http://87.247.176.67:8080/hottopics.jsp</a></p>
<div class="row-old">
    <div class="col-75-old">
        <a href="https://twitter.com/RworkIr" class="fa fa-twitter" style="font-size:36px;color:blue"></a>
        <a href="https://www.instagram.com/87.247.176.67:8080/" class="fa fa-instagram" style="font-size:36px;color:red"></a>
        <a href="mailto:newstrendir@gmail.com" class="fa fa-envelope" style="font-size:36px;color:green"></a>
    </div>
</div>
</body>
</html>
