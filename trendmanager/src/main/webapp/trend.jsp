<%-- 
    Document   : w3test.jsp
    Created on : Aug 5, 2020, 9:11:03 PM
    Author     : baghe
--%>
<%@page import="jhazm.Normalizer" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="static net.sourceforge.htmlunit.corejs.javascript.Context.exit" %>
<%@ page import="org.elasticsearch.cluster.routing.DelayedAllocationService" %>
<%@ page import="edu.stanford.nlp.trees.Tree" %>
<%@ page import="java.util.*" %>
<%@ page import="com.fff.trendmanager.trend.NestedJSONObjectTest" %>
<%@ page import="java.time.Clock" %>
<%@ page import="com.fff.trendmanager.trend.saveRequests" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<!DOCTYPE html>
<html dir="rtl">
<head>
    <meta charset="UTF-8">
    <title>روندهای خبری</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <script src="https://code.highcharts.com/highcharts.js"></script>
    <script src="https://code.highcharts.com/highcharts-more.js"></script>
    <script src="https://code.highcharts.com/modules/exporting.js"></script>
    <script src="https://code.highcharts.com/modules/accessibility.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/persiandate@0.2.1/dist/persiandate.min.js"></script>
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
    String phrase = "";
    Long days = 10L;
    if (request.getParameter("d") != null)
        if (request.getParameter("d") != "")
            days = Long.parseLong(request.getParameter("d"));
    if (request.getParameter("p") != null)
        phrase = request.getParameter("p").trim();
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    Date date = new Date(System.currentTimeMillis()- 24 * 3600 * 1000);
    date.setMinutes(date.getMinutes()-date.getMinutes()%10);
    Date dateNow=new Date(System.currentTimeMillis()-1*60*1000);
    dateNow.setMinutes(dateNow.getMinutes()-dateNow.getMinutes()%10);
    Date dateBackground=new Date(System.currentTimeMillis()- 70 * 24 * 3600 * 1000);
    dateBackground.setMinutes(dateBackground.getMinutes()-dateBackground.getMinutes()%10);
    if(phrase.equals(""))
    {
        String json = "{\n"
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
        String url = "http://localhost:9200/fetchednews/_search";
        String s = com.fff.trendmanager.trend.NestedJSONObjectTest.sendJsonPOST(url, json, "elastic", "tbontb");
        TreeMap result = com.fff.trendmanager.trend.NestedJSONObjectTest.extractTrendFromJson(s);
        Iterator it=result.descendingKeySet().iterator();
        Boolean firsttimeflag=true;
        for (;it.hasNext();) {
            if(firsttimeflag==true){
                firsttimeflag=false;
            }
            else
                phrase=phrase+"،";
            Float score=(Float)it.next();
            phrase=phrase+result.get(score);
        }
    }
%>
<div class="w3-container w3-center">
    <a href="https://ravand.rwork.ir" style="font-size:25px ;color:blue" ;>ravand.rwork.ir</a>
    <form action="trend.jsp">
        <div class="row-old">
            <div class="col-75-old">
                <label for="pastdays">از چند روز گذشته؟:</label>
                <input type="text" id="d" name="d" placeholder="10" value="<%=days%>">
                <label for="phrase">عبارات(جدا شده با کاما یا shift+T):</label>
                <input type="text" id="p" name="p" placeholder="روحانی، ترامپ، بایدن" value="<%=phrase%>">
                <input type="submit" value="جستجو">
            </div>
        </div>
    </form>
    <%
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        Long l1=System.currentTimeMillis();
        Long l2=days * 24 * 3600 * 1000;
        Date date2 = new Date(l1 - l2);
        String url = "http://localhost:9200/fetchednews/_search";
        Normalizer normal = new Normalizer();
        String[] p = null;
        String json="";
        if (!phrase.equals("")) {
            p = phrase.split("[,،]");
            for (int i = 0; i < p.length; i++)
                p[i] = p[i].trim();
        }
        Map<String, TreeMap> tr = new HashMap<String, TreeMap>();
        if (p != null)
            for (int i = 0; i < p.length; i++) {
                 json = "{\n" +
                        "  \"query\": {\n" +
                        "   \"bool\" : {\n" +
                        "    \"must\" : [ {\n" +
                        "    \"range\" : {\n" +
                        "      \"publishtime\": {\n" +
                        "\t\t\"time_zone\": \"Asia/Tehran\",\n" +
                        "        \"gte\": \"" + formatter2.format(date2) + "\"\n" +
                        "      }\n" +
                        "    }},\n" +
                        "    {\n" +
                        "\t\"term\": {\n" +
                        "\t\t\"content\":\"" + normal.run(p[i]) + "\"\n" +
                        "\t}\n" +
                        "\t}]\n" +
                        "\t}\n" +
                        "  },\n" +
                        "  \"size\":0, \n" +
                        "  \"aggs\": {\n" +
                        "    \"byday\": {\n" +
                        "      \"date_histogram\": {\n" +
                        "\t    \"time_zone\": \"+04:30\", \n" +
                        "        \"field\": \"publishtime\",\n" +
                        "        \"calendar_interval\": \"day\"\n" +
                        "      }\n" +
                        "    }\n" +
                        "  }\n" +
                        "}";

                String s = com.fff.trendmanager.trend.NestedJSONObjectTest.sendJsonPOST(url, json, "elastic", "tbontb");
                TreeMap result = NestedJSONObjectTest.extractFrequencyFromJson(s);
                tr.put(p[i], result);
            }
    %>

</div>
<figure class="highcharts-figure">
    <div id="container"></div>
</figure>
<script type="text/javascript">
    Highcharts.chart('container', {
        chart: {
            zoomType: 'x',
            height: '55%'
        },
        xAxis: {
            type: 'datetime',
            labels: {
                formatter: function () {
                    var someDate = new Date(this.value);
                    return persianDate(someDate).format('YYYY-MMMM-D');
                }
            }
        },
        lang: {
            viewFullscreen: "تمام صفحه",
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
            text: 'روند',
            margin: 0
        },
        tooltip: {
            shared: true,
            useHTML: true
        },
        plotOptions: {
            series: {
                pointPlacement: 'on',
                pointInterval: 24 * 3600 * 1000 // one day
            }
        },
        series: [
            <%
            ArrayList<String> als=new ArrayList<String>();
            Iterator<Map.Entry<String,TreeMap>> it=tr.entrySet().iterator();
            for(;it.hasNext();)
                {
                    Date sdate=null;
                    Map.Entry e=(Map.Entry) it.next();
                    TreeMap v=(TreeMap)e.getValue();
                    Iterator it1=v.entrySet().iterator();
                    out.write("{\n");
                    als.add(e.getKey().toString());
                    out.write("name: '" + e.getKey().toString() + "',\n");
                    out.write("type: 'spline',\n");
                    //------------ Data Section Start
                    out.write("data: [");Boolean first=true;
                    for(;it1.hasNext();)
                        {
                            Map.Entry me = (Map.Entry)it1.next();
                            if(first==true){sdate= new Date(Long.parseLong(me.getKey().toString()));first=false;}
                            out.write(me.getValue().toString()+",");
                        }
                    out.write("],\n");
                    //------------ Data Section End
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tehran"));
                    if(sdate==null)
                        sdate=new Date();
                    cal.setTime(sdate);
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    out.write("pointStart: Date.UTC(" + year + "," + month + "," + day + "),\n");
                    out.write("xAxis: 0\n");
                    out.write("},");
                }
            %>

        ]
    });
</script>
<p>
<%for(int i=0;i<als.size();i++){%>
    <a href="index.jsp?q=<%=als.get(i)%>" ><%="#" + als.get(i)+ " ,"%></a>
    <% out.newLine();}
%>
<a href="https://ravand.rwork.ir/trend.jsp">https://ravand.rwork.ir/trend.jsp</a></p>
<div class="row-old">
    <div class="col-75-old">
        <a href="https://twitter.com/RworkIr" class="fa fa-twitter" style="font-size:36px;color:blue"></a>
        <a href="https://www.instagram.com/ravand.rwork.ir/" class="fa fa-instagram" style="font-size:36px;color:red"></a>
        <a href="mailto:newstrendir@gmail.com" class="fa fa-envelope" style="font-size:36px;color:green"></a>
    </div>
</div>
</body>
</html>
