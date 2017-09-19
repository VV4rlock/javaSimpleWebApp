package servlets;

import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by warlock on 15.05.17.
 */
public class AdminLogin extends HttpServlet {

    public AdminLogin(){}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        if (req.getSession().getId()==AdminPage.getSessionIdOfAdnim()){
            pageVariables.put("redirect","/adminPage");
            resp.getWriter().println(PageGenerator.instance().getPage("redirect.html", pageVariables));
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
        }
        resp.getWriter().println(PageGenerator.instance().getPage("adminLogin.html", pageVariables));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
