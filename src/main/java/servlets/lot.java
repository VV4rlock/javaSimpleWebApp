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
import java.util.Map;

/**
 * Created by warlock on 14.05.17.
 */
public class lot extends HttpServlet {
    private final AccountService accountService;
    private final LotService lotsService;

    public lot(AccountService accountService, LotService lotService) {
        this.accountService = accountService;
        this.lotsService=lotService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        Map<String, Object> pageVariables = new HashMap<>();
        if (profile==null){
            resp.getWriter().println("You are unknown((");
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        try {
            Long id = Long.valueOf(req.getParameter("id"));
            LotProfile lot = lotsService.getLotById(id);
            pageVariables.put("lot",lot);
            resp.getWriter().println(PageGenerator.instance().getPage("lot.html", pageVariables));
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }catch (Exception e){
            resp.getWriter().println("you are not prepared");
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        Map<String, Object> pageVariables = new HashMap<>();
        if (profile==null){
            resp.getWriter().println("You are unknown((");
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        try {
            Long id = Long.valueOf(req.getParameter("id"));
            Integer price=Integer.valueOf(req.getParameter("price"));
            LotProfile lot = lotsService.getLotById(id);
            if (price < lot.getLastPrice()) {
                resp.getWriter().println("We need more money. Last price: "+ lot.getLastPrice());
                resp.setContentType("text/html;charset=utf-8");
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            }
            if (System.currentTimeMillis()>lot.getTimeAlive()+lot.getDateOfCreation()){
                resp.getWriter().println("Sorry, but you late");
                resp.setContentType("text/html;charset=utf-8");
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            }

            lotsService.updateLotById(id, profile.getLogin(), price);
            resp.getWriter().println("raise successful. The price on lot\""+lot.getName() +"\" is "+ price);
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e){
            resp.getWriter().println("sorry, sth go wrong");
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
        }

    }
}
