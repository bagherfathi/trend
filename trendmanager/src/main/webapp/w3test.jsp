<%-- 
    Document   : w3test.jsp
    Created on : Aug 5, 2020, 9:11:03 PM
    Author     : baghe
--%>

<%@page import="java.util.Iterator"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html dir="rtl">
    <head>
  <meta charset="UTF-8">
  <title>روند دات آی آر</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <script src="https://code.highcharts.com/highcharts.js"></script>
        <script src="https://code.highcharts.com/highcharts-more.js"></script>
        <script src="https://code.highcharts.com/modules/exporting.js"></script>
        <script src="https://code.highcharts.com/modules/accessibility.js"></script>
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
        </style>
</head> 

<body>

<div class="w3-container w3-center">
  <h2>روند دات آی آر</h2>
  <h2>Ravand.ir</h2>
  <% 
      String url = "http://localhost:9200/fetchednews/_search";
        String json = "{\n"
                + "  \"query\": {\n"
                + "        \"range\" : {\n"
                + "            \"fetchtime\" : {\n"
                + "				\"time_zone\": \"+04:30\",\n"
                + "                \"gte\" : \"now-24h/h\"\n"
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
                + "            \"field\": \"description\",\n"
                + "            \"filter_duplicate_text\": true\n"
                + "          }\n"
                + "        }\n"
                + "      }\n"
                + "    }\n"
                + "  }\n"
                + "}";
        String s = com.fff.trendmanager.trend.NestedJSONObjectTest.sendJsonPOST(url, json,"elastic","tbontb");
    java.util.TreeMap result= com.fff.trendmanager.trend.NestedJSONObjectTest.extractTrendFromJson(s);
        Iterator it=result.descendingKeySet().iterator();
        for (;it.hasNext();) {
            Float score=(Float)it.next();
  %>
<%--  <p>روندهای اخیر:<%=result.get(score)%></p>--%>
    <a href="index.jsp?q=<%=result.get(score)%>&from=0"><%=result.get(score)%></a>
    <br>
    <% } %>
</div>
<figure class="highcharts-figure">
    <div id="container"></div>
    <p class="highcharts-description">
        Packed bubble charts are visualizations where the size and optionally
        the color of the bubbles are used to visualize the data. The positioning
        of the bubbles is not significant, but is optimized for compactness.
        Try dragging the bubbles in this chart around, and see the effects.
    </p>
</figure>
<script type="text/javascript">
Highcharts.chart('container', {
chart: {
type: 'packedbubble',
height: '100%'
},
title: {
text: 'Carbon emissions around the world (2014)'
},
tooltip: {
useHTML: true,
pointFormat: '<b>{point.name}:</b> {point.value}m CO<sub>2</sub>'
},
plotOptions: {
packedbubble: {
minSize: '30%',
maxSize: '120%',
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
value: 2
},
style: {
color: 'black',
textOutline: 'none',
fontWeight: 'normal'
}
}
}
},
series: [{
name: 'Europe',
data: [{
name: 'Europe',
value: 1400.1
}]
}, {
name: 'Africa',
data: [{
name: "Africa",
value: 8.2
}]
}, {
name: 'North America',
data: [{
name: "North America",
value: 7.6
}]
}, {
name: 'South America',
data: [{
name: "South America",
value: 7.2
}]
}, {
name: 'Asia',
data: [{
name: "Asia",
value: 6.5
}]
}]
});
</script>
<div class="row-old">
    <div class="col-75-old">
        <a href="mailto:newstrendir@gmail.com">تماس با ما</a>
    </div>
</div>
</body>
</html>
