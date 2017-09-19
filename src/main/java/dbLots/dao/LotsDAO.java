package dbLots.dao;

import dbLots.dataSets.SignatureDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class LotsDAO {

    private Session session;

    public LotsDAO(Session session) {
        this.session = session;
    }

    public SignatureDataSet get(long id) throws HibernateException {
        return (SignatureDataSet) session.get(SignatureDataSet.class, id);
    }

    public long getLotId(String name) throws HibernateException {
        Criteria criteria = session.createCriteria(SignatureDataSet.class);
        return ((SignatureDataSet) criteria.add(Restrictions.eq("name", name)).uniqueResult()).getId();
    }

    public boolean updateLot(SignatureDataSet lot){
        try{
            session.update(lot);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public List<SignatureDataSet> getAllLots() throws HibernateException {
        List<SignatureDataSet> lots=session.createCriteria(SignatureDataSet.class).list();
        return lots;
    }

    public long insertLot(SignatureDataSet lot) throws HibernateException {
        return (Long) session.save(lot);
    }

    public boolean deleteLot(SignatureDataSet lot) throws HibernateException {
        try {
            session.delete(lot);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
