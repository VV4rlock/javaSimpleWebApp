package servlets;

import accounts.AccountService;
import accounts.LotProfile;
import accounts.LotService;
import accounts.UserProfile;
import dbLots.DBException;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by warlock on 13.05.17.
 */
public class createLot extends HttpServlet {

    private final AccountService accountService;
    private final LotService lotsService;

    public createLot(AccountService accountService, LotService lotService) {
        this.accountService = accountService;
        this.lotsService=lotService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        Map<String, Object> pageVariables = new HashMap<>();
        if (profile==null){
            resp.getWriter().println("вы не авторизованы((");
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        resp.getWriter().println(PageGenerator.instance().getPage("createLot.html", pageVariables));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        Map<String, Object> pageVariables = new HashMap<>();
        if (profile==null){
            resp.getWriter().println("вы не авторизованы((");
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String name=req.getParameter("name");
        Long timeAlive=Long.valueOf(req.getParameter("timeAlive"));
        Integer lastPrice=Integer.valueOf(req.getParameter("price"));
        String description=req.getParameter("description");

        if (name==null || timeAlive==null){
            resp.getWriter().println("неверные данные");
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        LotProfile lot=new LotProfile(name, profile.getLogin(), profile.getLogin(),
                lastPrice, description, System.currentTimeMillis(), timeAlive);

        try {
            lotsService.addNewLot(lot);
        } catch (DBException e) {
            e.printStackTrace();
        }

        pageVariables.put("redirect","/main");
        resp.getWriter().println(PageGenerator.instance().getPage("redirect.html", pageVariables));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);

    }

}
