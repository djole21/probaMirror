package com.group.application.filters;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "ValidationFilter", urlPatterns = "/app/startAll")
public class FieldValidation implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        String country = req.getParameter("country");
        String league = req.getParameter("league");

        UrlValidator urlValidator = UrlValidator.getInstance();
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html;charset=UTF-8");
        boolean moz = urlValidator.isValid(req.getParameter("mozzart"));
        boolean mer = urlValidator.isValid(req.getParameter("meridian"));
        boolean sport888 = urlValidator.isValid(req.getParameter("sport888"));
        boolean bet = urlValidator.isValid(req.getParameter("bet365"));

        RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
        if(StringUtils.isNotEmpty(country) && StringUtils.isNotEmpty(league)){
            if(moz && mer && sport888 && bet){
                filterChain.doFilter(req, res);
            }else {
                pw.println("unesite ispravne url adrese obavezno");
                rd.include(req, res);
            }
        }else {
            pw.println("Unesite sva polja");
            rd.include(req, res);
        }

        pw.close();
    }

    @Override
    public void destroy() {

    }
}
