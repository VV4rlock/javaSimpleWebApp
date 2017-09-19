package servlets;

import accounts.AccountService;
import accounts.LotProfile;
import accounts.LotService;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by warlock on 15.05.17.
 */
public class AdminPage extends HttpServlet {
    private final String PASSWORD="123321";
    private static String sessionIdOfAdnim="1354534384345435313213";//prosto znach
    private final AccountService accountService;
    private final LotService lotsService;

    public static String getSessionIdOfAdnim(){
        return sessionIdOfAdnim;
    }

    public AdminPage(AccountService accountService, LotService lotService) {
        this.accountService = accountService;
        this.lotsService=lotService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        String pass=req.getParameter("password");
        if (pass==null) {
            resp.getWriter().println("NONONO((");
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        java.util.logging.Logger.getGlobal().info("somebode try to enter in admin page with pass "+pass);
        if (PASSWORD.compareTo(pass)!=0) {
            resp.getWriter().println("Wrong pass((");
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        try {
            java.util.logging.Logger.getGlobal().info("admin login");
            sessionIdOfAdnim=req.getSession().getId();
            List<LotProfile> activeLots= lotsService.getAllLots();
            List<LotProfile> complitedLots= lotsService.getAllComplitedLots();
            pageVariables.put("activeLots", activeLots);
            pageVariables.put("complitedLots", complitedLots);


            resp.getWriter().println(PageGenerator.instance().getPage("adminPage.html", pageVariables));
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (dbLots.DBException e)
        {
            resp.getWriter().println("Something went wrong((");
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
            e.printStackTrace();
        }
    }
}
