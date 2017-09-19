package servlets;

import accounts.AccountService;
import accounts.LotProfile;
import accounts.LotService;
import accounts.UserProfile;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class mainPage extends HttpServlet {
    private final AccountService accountService;
    private final LotService lotsService;

    public mainPage(AccountService accountService, LotService lotService) {
        this.accountService = accountService;
        this.lotsService=lotService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        Map<String, Object> pageVariables = new HashMap<>();
        if (profile==null){
            response.getWriter().println(PageGenerator.instance().getPage("main.html", pageVariables));
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        pageVariables.put("user",profile.getLogin());
        try{
            List<LotProfile> lots=lotsService.getAllLots();
            pageVariables.put("lots", lots);
        }
        catch (dbLots.DBException e){
            java.util.logging.Logger.getGlobal().info("LotsBD error of giving values");
        }

        response.getWriter().println(PageGenerator.instance().getPage("AuthtorizedMain.html", pageVariables));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }
}
