package dbLots;

import dbLots.dao.LotsDAO;
import dbLots.dataSets.SignatureDataSet;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class DBService {
    private static final String hibernate_show_sql = "false";
    private static final String hibernate_hbm2ddl_auto = "create"; //create,update, validate, create-drop

    private final SessionFactory sessionFactory;

    public DBService(String dbName) {
        Configuration configuration = getH2Configuration(dbName);
        sessionFactory = createSessionFactory(configuration);
    }


    private Configuration getH2Configuration(String dbName) {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(SignatureDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./"+dbName);
        configuration.setProperty("hibernate.connection.username", "tully");
        configuration.setProperty("hibernate.connection.password", "tully");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }


    public SignatureDataSet getLot(long id) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            LotsDAO dao = new LotsDAO(session);
            SignatureDataSet dataSet = dao.get(id);
            session.close();
            return dataSet;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public SignatureDataSet getLot(String name) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            LotsDAO dao = new LotsDAO(session);
            SignatureDataSet dataSet = dao.get(dao.getLotId(name));
            session.close();
            return dataSet;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public boolean updateLot(long id, String loginOfLastRate,
                                int lastPrice) throws DBException {
        boolean out=false;
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            LotsDAO dao = new LotsDAO(session);
            SignatureDataSet dataSet = dao.get(id);
            dataSet.setLoginOfLastRate(loginOfLastRate);
            dataSet.setLastPrice(lastPrice);
            out=dao.updateLot(dataSet);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new DBException(e);
        } finally {
            return out;
        }
    }


    public long addLot(SignatureDataSet lot) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            LotsDAO dao = new LotsDAO(session);
            long id = dao.insertLot(lot);
            transaction.commit();
            session.close();
            return id;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public boolean delelteLot(SignatureDataSet lot) throws DBException {
        boolean flag=false;
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            LotsDAO dao = new LotsDAO(session);
            flag=dao.deleteLot(lot);
            transaction.commit();
            session.close();
            return flag;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public List<SignatureDataSet> getAllLots() throws DBException {
        try {
            Session session = sessionFactory.openSession();
            LotsDAO dao = new LotsDAO(session);
            List<SignatureDataSet> dataSets = dao.getAllLots();
            session.close();
            return dataSets;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public void printConnectInfo() {
        try {
            SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sessionFactory;
            Connection connection = sessionFactoryImpl.getConnectionProvider().getConnection();
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
