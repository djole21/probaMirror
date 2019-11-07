package com.group.application.filters;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "SingleValidationFilter", urlPatterns = "/app/*")
public class SingleLeagueValidation implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        String path = ((HttpServletRequest) req).getRequestURI();
        if(!path.startsWith("/app/start") || path.startsWith("/app/startAll")){
            filterChain.doFilter(req, res);
        }

        String country = req.getParameter("country");
        String league = req.getParameter("league");

        UrlValidator urlValidator = UrlValidator.getInstance();
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html;charset=UTF-8");
        boolean url = urlValidator.isValid(req.getParameter("url"));


        RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
        if(StringUtils.isNotEmpty(country) && StringUtils.isNotEmpty(league)){
            if(url){
                filterChain.doFilter(req, res);
            }else {
                pw.println("unesite ispravne url adrese");
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
