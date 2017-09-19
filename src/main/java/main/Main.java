package main;

import accounts.AccountService;
import accounts.LotService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.*;

import java.security.MessageDigest;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class Main {
    public static void main(String[] args) throws Exception {
        AccountService accountService = new AccountService();
        LotService lotService=new LotService("ActiveLots");


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new AllRequestsServlet()), "/*");
        context.addServlet(new ServletHolder(new logIn(accountService)),"/login");
        context.addServlet(new ServletHolder(new logout(accountService)),"/logout");
        context.addServlet(new ServletHolder(new mainPage(accountService,lotService)),"/main");
        context.addServlet(new ServletHolder(new signup(accountService)),"/singup");
        context.addServlet(new ServletHolder(new createLot(accountService,lotService)),"/createLot");
        context.addServlet(new ServletHolder(new lot(accountService,lotService)),"/lot");
        context.addServlet(new ServletHolder(new personal(accountService,lotService)),"/personal");
        context.addServlet(new ServletHolder(new bill(accountService,lotService)),"/bill");
        context.addServlet(new ServletHolder(new deleteLot(accountService,lotService)),"/delete");
        context.addServlet(new ServletHolder(new AdminLogin()),"/admin");
        context.addServlet(new ServletHolder(new AdminPage(accountService,lotService)),"/adminPage");


        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        Server server = new Server(8080);
        server.setHandler(handlers);

        server.start();
        java.util.logging.Logger.getGlobal().info("Server started");
        server.join();
    }
    /*
    DBService dbUsers = new DBService();
        dbUsers.printConnectInfo();

        try {
            long userId = dbUsers.addUser("tully");
            System.out.println("Added user id: " + userId);

            LotDataSet dataSet = dbUsers.getUser(userId);
            System.out.println("User data set: " + dataSet);

        } catch (DBException e) {
            e.printStackTrace();
        }

     */
}
