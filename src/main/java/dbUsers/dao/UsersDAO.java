package dbUsers.dao;

import dbUsers.DBException;
import dbUsers.dataSets.UsersDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class UsersDAO {

    private Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }

    public UsersDataSet get(long id) throws HibernateException {
        return (UsersDataSet) session.get(UsersDataSet.class, id);
    }

    public long getUserId(String login) throws HibernateException, DBException {
        Criteria criteria = session.createCriteria(UsersDataSet.class);
        try {
            return ((UsersDataSet) criteria.add(Restrictions.eq("name", login)).uniqueResult()).getId();
        }
        catch (NullPointerException e) {
            throw new DBException(e);
        }
    }

    public long insertUser(String name,String pass, String purse) throws HibernateException {
        return (Long) session.save(new UsersDataSet(name, pass, purse));
    }
}
