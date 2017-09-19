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
 * Created by warlock on 14.05.17.
 */
public class deleteLot extends HttpServlet {
    private final AccountService accountService;
    private final LotService lotsService;

    public deleteLot(AccountService accountService, LotService lotService) {
        this.accountService = accountService;
        this.lotsService=lotService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        Map<String, Object> pageVariables = new HashMap<>();
        try {
            if (sessionId == AdminPage.getSessionIdOfAdnim()) {
                Long id = Long.valueOf(req.getParameter("id"));
                LotProfile lot = lotsService.getLotById(id);
                lotsService.deleteLot(lot);
                pageVariables.put("redirect", "/adminPage");
                resp.getWriter().println(PageGenerator.instance().getPage("redirect.html", pageVariables));
                resp.setContentType("text/html;charset=utf-8");
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        }catch (DBException e){
            e.printStackTrace();
        }
        if (profile==null){
            resp.getWriter().println("you are not prepared((");
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        try {
            Long id = Long.valueOf(req.getParameter("id"));
            LotProfile lot = lotsService.getLotById(id);
            if (sessionId== AdminPage.getSessionIdOfAdnim()){
                lotsService.deleteLot(lot);
                pageVariables.put("redirect","/admin");
                resp.getWriter().println(PageGenerator.instance().getPage("redirect.html", pageVariables));
                resp.setContentType("text/html;charset=utf-8");
                resp.setStatus(HttpServletResponse.SC_OK);
            }
            if (lot.getLoginOfCreater()!=profile.getLogin()){
                resp.getWriter().println("Permission denied((");
                resp.setContentType("text/html;charset=utf-8");
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            }
            if (lot.getDateOfCreation()+lot.getTimeAlive()<System.currentTimeMillis()){
                pageVariables.put("redirect","/personal");
                resp.getWriter().println(PageGenerator.instance().getPage("redirect.html", pageVariables));
                resp.setContentType("text/html;charset=utf-8");
                resp.setStatus(HttpServletResponse.SC_OK);
            }
            lotsService.deleteLot(lot);
            pageVariables.put("redirect","/personal");
            resp.getWriter().println(PageGenerator.instance().getPage("redirect.html", pageVariables));
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
