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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by warlock on 27.04.17.
 */
public class signup extends HttpServlet {

    private final AccountService accountService;

    public signup(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        response.getWriter().println(PageGenerator.instance().getPage("registration.html", pageVariables));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        if (profile!=null){
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("you already login <br><a href=\"/main\">на главную</a>");
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        String login=req.getParameter("login");
        String password=req.getParameter("password");
        String purse=req.getParameter("purse");
        if (login==null || password==null || purse==null || login=="" || password=="" || purse==""){
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            if (accountService.getUserByLogin(login) != null) {
                resp.setContentType("text/html;charset=utf-8");
                resp.getWriter().println("пользователь с таким логином уже существует");
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            }
        }
        catch(DBException e){}
        password=AccountService.getHashFromString(password);
        UserProfile user=new UserProfile(login,password,purse);
        try {
            accountService.addNewUser(user);
            accountService.addSession(sessionId, user);
            java.util.logging.Logger.getGlobal().info(login+" signup");

            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("redirect","/main");
            resp.getWriter().println(PageGenerator.instance().getPage("redirect.html", pageVariables));

            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (DBException e) {
            e.printStackTrace();
        }


    }
}
