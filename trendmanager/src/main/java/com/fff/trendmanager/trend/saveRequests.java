package com.fff.trendmanager.trend;

import document.IndexApi;
import java.util.Date;
import org.elasticsearch.action.index.IndexResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class saveRequests {

    public void indexString(String s)
    {
        IndexApi ia = new IndexApi();
        Map<String,Object> fu=new HashMap<String,Object>();
        fu.put("url", s);
        fu.put("searchtime", new Date(System.currentTimeMillis()));
        IndexResponse ir;
        ir = ia.indexSync("userrequest", fu);
        fu.clear();
    }
    public String returnURL(HttpServletRequest request)
    {
        String str=request.getRequestURL()+"?";
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements())
        {
            String paramName = paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            for (int i = 0; i < paramValues.length; i++)
            {
                String paramValue = paramValues[i];
                str=str + paramName + "=" + paramValue;
            }
            str=str+"&";
        }
        System.out.println(str.substring(0,str.length()-1));
        return (str.substring(0,str.length()-1));
    }
    public void indexRequest(HttpServletRequest request)
    {
        indexString(returnURL(request));
    }
}
