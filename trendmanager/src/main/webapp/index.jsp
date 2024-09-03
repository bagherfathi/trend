<%@ page import="com.fff.trendmanager.trend.Search" %>
<%@ page import="org.elasticsearch.search.SearchHit" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="org.elasticsearch.action.search.SearchResponse" %>
<%@ page import="org.elasticsearch.client.RequestOptions" %>
<%@ page import="org.elasticsearch.search.SearchHits" %>
<%@ page import="org.elasticsearch.search.suggest.Suggest" %>
<%@ page import="org.elasticsearch.search.suggest.term.TermSuggestion" %>
<%@ page import="org.elasticsearch.search.fetch.subphase.highlight.HighlightField" %>
<%@ page import="org.elasticsearch.common.text.Text" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.fff.trendmanager.trend.saveRequests" %>
<%@page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html dir="rtl">
<head>
    <title>روند اخبار ایران</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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

        .acenter {
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
<body>
<%
    saveRequests sr=new saveRequests();
    sr.indexRequest(request);
    int f = 0;
    String qry = "";
    Enumeration<String> pn = request.getParameterNames();
    //    out.print("<br>");
    while (pn.hasMoreElements()) {
        String name = (String) pn.nextElement();
        if (name.trim().equals("from")) {
            f = Integer.parseInt(request.getParameter("from"));
        }
        if (name.trim().equals("q")) {
            qry = request.getParameter("q");
        }
        //             out.print("<b>" + name + ": </b>");
        //        out.print(request.getParameter(name) + "<br>");
    }
    //    out.print("<b> integer f:" + f + ": </b>");
    if (f < 0)
        f = 0;
%>
<div class="container">
    <form action="index.jsp">
        <div class="row-old">
            <div class="col-75-old">
                <a href="http://87.247.176.67:8080" style="font-size:25px ;color:blue" ;>87.247.176.67:8080</a>
                <br>
                <b style="font-size:15px ;color:red">(آزمایشی)</b>
                <br>
                <a href="http://87.247.176.67:8080" style="font-size:15px ;color:blue" ;><b>جستجوی اخبار</b></a>
                <b>&emsp;</b>
                <a href="http://87.247.176.67:8080/trend.jsp" style="font-size:15px ;color:blue" ;><b>روند اخبار</b></a>
                <b>&emsp;</b>
                <a href="http://87.247.176.67:8080/hottopics.jsp" style="font-size:15px ;color:blue" ;><b>کلمات داغ</b></a>
            </div>
        </div>
        <div class="row-old">
            <div class="col-75-old">
                <input type="text" id="q" name="q" value="<%=qry%>" placeholder="عبارت جستجو را وارد کنید">
            </div>
        </div>
        <div class="row-old">
            <div class="col-75-old">
                <input type="submit" value="جستجو">
            </div>
        </div>
    </form>
</div>
<%
    com.fff.trendmanager.trend.Search s = new Search();
    SearchResponse searchResponse = s.multiMatch(qry, f * 10);
    SearchHits hits = searchResponse.getHits();
    SearchHit[] sh = hits.getHits();
    Suggest suggest = searchResponse.getSuggest();
    TermSuggestion termSuggestion = suggest.getSuggestion("suggest_content");
    boolean hasSuggest = false;
    for (TermSuggestion.Entry entry : termSuggestion.getEntries()) {
        for (TermSuggestion.Entry.Option option : entry) {
            hasSuggest = true;
            String suggestText = option.getText().string();
%>
<a href="index.jsp?q=<%=suggestText%>&from=0"><b><%=suggestText%>
</b></a>&nbsp;
<%
        }
    }
    boolean hasHit = false;
    for (SearchHit hit : sh) {
        String fragmentString = "";
        hasHit = true;
        Map<String, HighlightField> highlightFields = hit.getHighlightFields();
        HighlightField highlight = highlightFields.get("content");
        if (highlight != null) {
            Text[] fragments = highlight.fragments();
            fragmentString = "";
            for (Text fs : fragments) {
                String temp = fs.string();
                String[] seperatedQ = qry.split(" ");
                for (String s1 : seperatedQ)
                    temp = temp.replace(s1.trim(), "<b>" + s1.trim() + "</b>");
                fragmentString = fragmentString + temp + "...";
            }
        }
        Map<String, Object> map = hit.getSourceAsMap();
        String mapid = hit.getId();
%>
<%-- news body --%>
<%--    <div class="container">--%>
<%--    <div class="row">--%>
<%--      <div class="col-75">--%>
<%--<div class="acenter">--%>
<div class="row">
    <div class="col-75">
        <a href="newsbody/<%=mapid.toString()%>"><%="<b>" + map.get("title").toString() + "</b>"%>
        </a>
        <%="  -  " + "<b>" + map.get("name").toString() + "</b>"%>
    </div>
</div>
<%--<%="  -  "+"<b>"+map.get("publishtime")+"</b>"%>--%>
<%--<%="  -  "+"<b>"+System.currentTimeMillis()+"</b>"%>--%>
<%
    Date d1 = new Date(Long.parseLong(map.get("publishtime").toString()));
    Date d2 = new Date(System.currentTimeMillis());
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
    String paststring = "";
    if (difference_In_Years > 0)
        paststring = difference_In_Years
                + " سال, ";
    if (difference_In_Days > 0)
        paststring = paststring + difference_In_Days
                + " روز, ";
    if (difference_In_Hours > 0)
        paststring = paststring + difference_In_Hours
                + " ساعت, ";
    if (difference_In_Minutes > 0)
        paststring = paststring + difference_In_Minutes
                + " دقیقه, ";
    if (difference_In_Seconds > 0)
        paststring = paststring + difference_In_Seconds
                + " ثانیه پیش";
%>
<div class="row">
    <div class="col-75">
        <b style="color:#ff4500;"><%="  -  " + "<b>" + paststring + "</b>"%>
        </b>
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
        <p><%=fragmentString%>
        </p>
    </div>
</div>
<%--        </div>--%>
<%--    </div>--%>
<%--  </div>--%>
<%-- end of news body --%>

<% } %>
<%if (hasHit == true) {%>
<div class="acenter">
    <a href="index.jsp?q=<%=qry%>&from=<%=f-1%>"><b>قبلی</b></a>
    <a href="index.jsp?q=<%=qry%>&from=<%=f+1%>"><b>بعدی</b></a>
</div>
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