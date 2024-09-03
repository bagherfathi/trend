<%@ page import="com.fff.trendmanager.trend.Search" %>
<%@ page import="org.elasticsearch.search.SearchHit" %>
<%@ page import="org.elasticsearch.action.search.SearchResponse" %>
<%@ page import="org.elasticsearch.client.RequestOptions" %>
<%@ page import="org.elasticsearch.search.SearchHits" %>
<%@ page import="org.elasticsearch.search.suggest.Suggest" %>
<%@ page import="org.elasticsearch.search.suggest.term.TermSuggestion" %>
<%@ page import="org.elasticsearch.search.fetch.subphase.highlight.HighlightField" %>
<%@ page import="com.github.eloyzone.jalalicalendar.DateConverter"%>
<%@ page import="org.elasticsearch.common.text.Text" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.fff.trendmanager.trend.saveRequests" %>
<%@ page import="com.github.eloyzone.jalalicalendar.JalaliDate" %>
<%@ page import="com.github.eloyzone.jalalicalendar.JalaliDateFormatter" %>
<%@ page import="java.time.Instant" %>
<%@ page import="java.util.*" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%
    saveRequests sr=new saveRequests();
    sr.indexRequest(request);
    int f=0;
    Enumeration<String> pn = request.getParameterNames();
//    out.print("<br>");
    while (pn.hasMoreElements()){
        String name = (String) pn.nextElement();
        if(name.trim().equals("from")) {
//              out.print("<b>" + "in if statement" + request.getParameter("from") + ": </b>");
            f = Integer.parseInt(request.getParameter("from"));
        }
//             out.print("<b>" + name + ": </b>");
//        out.print(request.getParameter(name) + "<br>");
    }
//    out.print("<b> integer f:" + f + ": </b>");
    if(f<0)
        f=0;
    com.fff.trendmanager.trend.Search s = new Search();
    SearchResponse searchResponse =s.matchById(request.getParameter("id"));
    SearchHits hits = searchResponse.getHits();
    SearchHit[] sh=hits.getHits();
    boolean hasHit=false;
    for (SearchHit hit : sh) {
        hasHit=true;
        Map<String, Object> map = hit.getSourceAsMap();
        String mapid=hit.getId();
%>
<!DOCTYPE html>
<html dir="rtl">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title><%=map.get("title").toString()%></title>
    <style>
        * {
            box-sizing: border-box;
        }

        input[type=text], select, textarea {
            width: 50%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 4px;
            resize: vertical;
        }

        label {
            padding: 12px 12px 12px 0;
            display: inline-block;
        }

        input[type=submit] {
            width: 10%;
            padding: 12px;
            background-color: #4CAF50;
            color: white;
            border: 1px solid #ccc;
            border-radius: 4px;
            cursor: pointer;
            resize: vertical;
        }

        input[type=submit]:hover {
            background-color: #45a049;
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
        .container {
            border-radius: 5px;
            background-color: #f2f2f2;
            padding: 20px;
        }

        .col-25 {
            float: left;
            width: 25%;
            margin-top: 6px;
        }

        .col-75 {
            float: inside;
            margin-top: 6px;
            text-align: center;
            max-width: 600px;
        }

        /* Clear floats after the columns */
        .row {
            display: table;
            clear: both;
            margin: 0 auto;
        }

        .acenter{
            width: 100%;
            text-align: center;
        }
        /* Responsive layout - when the screen is less than 600px wide, make the two columns stack on top of each other instead of next to each other */
        @media screen and (max-width: 600px) {
            .col-25, .col-75, input[type=submit] {
                width: 100%;
                margin-top: 0;
            }
        }
    </style>
</head>
<body >
<div class="container">
    <form action="index.jsp">
        <div class="row">
            <div class="col-75">
                <a href="http://87.247.176.67:8080" style="font-size:25px ;color:blue";>87.247.176.67:8080</a>
            </div>
        </div>
    </form>
</div>

<%-- news body --%>
<%--    <div class="container">--%>
<%--    <div class="row">--%>
<%--      <div class="col-75">--%>
<%--<div class="acenter">--%>
<div class="row">
    <div class="col-75">
        <h1 style="color:blue;"><%=map.get("title").toString()%></h1>
    </div>
</div>
<%--<%="  -  "+"<b>"+map.get("publishtime")+"</b>"%>--%>
<%--<%="  -  "+"<b>"+System.currentTimeMillis()+"</b>"%>--%>
<%
    Date d1=new Date(Long.parseLong(map.get("publishtime").toString()));
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd' 'HH:mm");
    DateConverter dateConverter = new DateConverter();
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(d1);
    JalaliDate jalaliDate1 = dateConverter.gregorianToJalali(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH));
    Date d2=new Date(System.currentTimeMillis());
    long difference_In_Time
            = d2.getTime() - d1.getTime();
    long difference_In_Seconds
            = (difference_In_Time
            / 1000)
            % 60;
    long difference_In_Minutes
            = (difference_In_Time
            / (1000 * 60))
            % 60;
    long difference_In_Hours
            = (difference_In_Time
            / (1000 * 60 * 60))
            % 24;
    long difference_In_Years
            = (difference_In_Time
            / (1000l * 60 * 60 * 24 * 365));
    long difference_In_Days
            = (difference_In_Time
            / (1000 * 60 * 60 * 24))
            % 365;
    String paststring="";
    if(difference_In_Years>0)
        paststring=difference_In_Years
                + " سال, ";
    if(difference_In_Days>0)
        paststring=paststring + difference_In_Days
                + " روز, ";
    if(difference_In_Hours>0)
        paststring=paststring + difference_In_Hours
                + " ساعت, ";
    if(difference_In_Minutes>0)
        paststring=paststring + difference_In_Minutes
                + " دقیقه, ";
    if(difference_In_Seconds>0)
        paststring=paststring + difference_In_Seconds
                + " ثانیه پیش";
%>
<div class="row">
    <div class="col-75">
        <b dir="ltr" style="color:#0c0f17;"><%=formatter.format(d1)%></b>
    </div>
</div>
<div class="row">
    <div class="col-75">
        <b style="color:#0c0f17;"><%=jalaliDate1.format(new JalaliDateFormatter("yyyy/mm/dd", JalaliDateFormatter.FORMAT_IN_PERSIAN)) + "    " + calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE) %></b>
    </div>
</div>
<div class="row">
    <div class="col-75">
        <b style="color:#ff4500;"><%="  -  "+"<b>"+paststring+"</b>"%></b>
    </div>
</div>
<%--</div>--%>
<%--      </div>--%>
<%--    </div>--%>
<%--    <div class="row">--%>
<%--        <div class="col-75">--%>
<%--            <p style="text-align:center;"><%=fragmentString%></p>--%>
<div class="row">
    <div class="col-75">
        <b style="color:black"><%="خلاصه خبر:" + map.get("description")%></b>
    </div>
</div>
    <br>
    <br>
<div class="row">
    <div class="col-75">
        <b style="color:midnightblue;text-align: justify;text-justify: inter-word;"><%="متن خبر:" + map.get("content")%></b>
    </div>
</div>
<div class="row">
    <div class="col-75">
        <b style="color:black"><%="خبرگزاری: " + map.get("name")%></b>
    </div>
</div>
    <div class="row">
        <div class="col-75">
            <a href="<%=map.get("newsurl")%>" style="color: blue">لینک اصل خبر</a>
        </div>
        <div class="col-75">
            <a href="http://87.247.176.67:8080" style="color: blue">برگشت به صفحه اصلی</a>
        </div>
    </div>
<%--        </div>--%>
<%--    </div>--%>
<%--  </div>--%>
<%-- end of news body --%>

<% } %>
<div class="row-old">
    <div class="col-75-old">
        <a href="https://twitter.com/RworkIr" class="fa fa-twitter" style="font-size:36px;color:blue"></a>
        <a href="https://www.instagram.com/87.247.176.67:8080/" class="fa fa-instagram" style="font-size:36px;color:red"></a>
        <a href="mailto:newstrendir@gmail.com" class="fa fa-envelope" style="font-size:36px;color:green"></a>
    </div>
</div>
</body>
</html>