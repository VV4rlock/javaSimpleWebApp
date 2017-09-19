package servlets;

import accounts.AccountService;
import accounts.LotProfile;
import accounts.LotService;
import accounts.UserProfile;
import dbUsers.DBException;
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
 * Created by warlock on 14.05.17.
 */
public class personal extends HttpServlet {
    private final AccountService accountService;
    private final LotService lotsService;

    public personal(AccountService accountService, LotService lotService) {
        this.accountService = accountService;
        this.lotsService=lotService;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        Map<String, Object> pageVariables = new HashMap<>();
        if (profile==null){
            resp.getWriter().println("you are not prepared((");
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        try {
            List<LotProfile> personalLots= lotsService.getLotsByLoginOfCreater(profile.getLogin());
            List<LotProfile> complitedLots= lotsService.getComplitedLotsByLogin(profile.getLogin());
            pageVariables.put("activeLots", personalLots);
            pageVariables.put("complitedLots", complitedLots);
            pageVariables.put("user", profile);

            resp.getWriter().println(PageGenerator.instance().getPage("personal.html", pageVariables));
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
