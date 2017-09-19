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
public class bill extends HttpServlet{
    private final AccountService accountService;
    private final LotService lotsService;

    public bill(AccountService accountService, LotService lotService) {
        this.accountService = accountService;
        this.lotsService=lotService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        Map<String, Object> pageVariables = new HashMap<>();
        if (sessionId== AdminPage.getSessionIdOfAdnim()){
            try {
                Long id = Long.valueOf(req.getParameter("id"));
                LotProfile lot = lotsService.getComplitedLotById(id);
                UserProfile creator = accountService.getUserByLogin(lot.getLoginOfCreater());
                UserProfile buyer = accountService.getUserByLogin(lot.getLoginOfLastRate());
                pageVariables.put("lot", lot);
                pageVariables.put("creator", creator);
                pageVariables.put("buyer", buyer);

                resp.getWriter().println(PageGenerator.instance().getPage("bill.html", pageVariables));
                resp.setContentType("text/html;charset=utf-8");
                resp.setStatus(HttpServletResponse.SC_OK);
            } catch (DBException e) {
                e.printStackTrace();
            } catch (dbUsers.DBException e){
                e.printStackTrace();
            }
            return;
        }
        if (profile==null){
            resp.getWriter().println("you are not prepared((");
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        try {
            Long id = Long.valueOf(req.getParameter("id"));
            LotProfile lot = lotsService.getComplitedLotById(id);
            UserProfile creator= accountService.getUserByLogin(lot.getLoginOfCreater());
            UserProfile buyer= accountService.getUserByLogin(lot.getLoginOfLastRate());
            if (profile.getLogin()==creator.getLogin() || profile.getLogin()==buyer.getLogin()) {
                pageVariables.put("lot", lot);
                pageVariables.put("creator", creator);
                pageVariables.put("buyer", buyer);

                resp.getWriter().println(PageGenerator.instance().getPage("bill.html", pageVariables));
                resp.setContentType("text/html;charset=utf-8");
                resp.setStatus(HttpServletResponse.SC_OK);
            }
            else{
                resp.getWriter().println("NotBad, but I more smart");
                resp.setContentType("text/html;charset=utf-8");
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (DBException e) {
            e.printStackTrace();
        } catch (dbUsers.DBException e){
            e.printStackTrace();
        }

    }
}
