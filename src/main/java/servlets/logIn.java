package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import dbUsers.DBException;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by warlock on 27.04.17.
 */
public class logIn extends HttpServlet {
    private final AccountService accountService;

    public logIn(AccountService accountService) {
        this.accountService = accountService;
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        if (profile!=null){
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("Вы уже вошли <br><a href=\"/main\">на главную</a>");
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        String login=req.getParameter("login");
        String password=req.getParameter("password");

        if (login==null || password==null){
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            UserProfile userProfile = accountService.getUserByLogin(login);
            Map<String, Object> pageVariables = new HashMap<>();
            if(AccountService.getHashFromString(password)!=userProfile.getPass()){
                pageVariables.put("redirect","/main");
                resp.getWriter().println(PageGenerator.instance().getPage("redirect.html", pageVariables));
                resp.setContentType("text/html;charset=utf-8");
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            resp.setContentType("text/html;charset=utf-8");
            accountService.addSession(req.getSession().getId(), userProfile);
            pageVariables.put("login", userProfile.getLogin());
            resp.getWriter().println(PageGenerator.instance().getPage("good_auth.html", pageVariables));

        }
        catch(DBException e){
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().println("Пользователя с таким именем не существует");
            return;
        }

    }
}
