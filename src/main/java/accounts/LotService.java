package accounts;

import dbLots.DBException;
import dbLots.DBService;
import dbLots.dataSets.SignatureDataSet;

import java.util.ArrayList;
import java.util.List;


public class LotService {
    DBService lotsDB;
    DBService complitedDeal;

    public LotService(String dbname){
        lotsDB=new DBService(dbname);
        complitedDeal = new DBService("complitedDeals");
    }

    public void addNewLot(LotProfile lot) throws DBException {
        SignatureDataSet ds=new SignatureDataSet(lot.getName(), lot.getLoginOfCreater(), lot.getLoginOfLastRate(),
                lot.getLastPrice(),lot.getDescription(), lot.getDateOfCreation(), lot.getTimeAlive());
        lotsDB.addLot(ds);
    }

    public boolean deleteLot(LotProfile lot) throws DBException {
        SignatureDataSet ds = new SignatureDataSet(lot.getId(), lot.getName(),lot.getLoginOfCreater(),lot.getLoginOfLastRate(),
                lot.getLastPrice(), lot.getDescription(), lot.getDateOfCreation(), lot.getTimeAlive());
        return lotsDB.delelteLot(ds);
    }

    public LotProfile getLotById(long id) throws DBException {

        SignatureDataSet ds= lotsDB.getLot(id);
        return new LotProfile(ds.getId(), ds.getName(),ds.getLoginOfCreater(),ds.getLoginOfLastRate(),
                ds.getLastPrice(), ds.getDescription(), ds.getDateOfCreation(), ds.getTimeAlive());

    }

    public boolean updateLotById(long id, String loginOfLastRate,
                              int lastPrice) throws DBException {

        return lotsDB.updateLot(id,loginOfLastRate,lastPrice);

    }

    public List<LotProfile> getAllLots() throws DBException{
        List<SignatureDataSet> datasets=lotsDB.getAllLots();
        List<LotProfile> out=new ArrayList<LotProfile>();
        long timeNow=System.currentTimeMillis();
        for (SignatureDataSet i : datasets){
            if (i.getTimeAlive()+i.getDateOfCreation()<timeNow){
                lotsDB.delelteLot(i);
                complitedDeal.addLot(i);
                continue;
            }
            out.add(new LotProfile(i.getId(),i.getName(),i.getLoginOfCreater(),i.getLoginOfLastRate(),
                    i.getLastPrice(),i.getDescription(),i.getDateOfCreation(),i.getTimeAlive()));
        }
        return out;
    }

    public List<LotProfile> getAllComplitedLots() throws DBException{
        List<SignatureDataSet> datasets=complitedDeal.getAllLots();
        List<LotProfile> out=new ArrayList<LotProfile>();
        for (SignatureDataSet i : datasets){
            out.add(new LotProfile(i.getId(),i.getName(),i.getLoginOfCreater(),i.getLoginOfLastRate(),
                    i.getLastPrice(),i.getDescription(),i.getDateOfCreation(),i.getTimeAlive()));
        }
        return out;
    }

    public List<LotProfile> getLotsByLoginOfCreater(String login) throws DBException{
        List<SignatureDataSet> datasets=lotsDB.getAllLots();
        List<LotProfile> out=new ArrayList<LotProfile>();
        for (SignatureDataSet i : datasets){
            if (i.getLoginOfCreater()==login) {
                out.add(new LotProfile(i.getId(), i.getName(), i.getLoginOfCreater(), i.getLoginOfLastRate(),
                        i.getLastPrice(), i.getDescription(), i.getDateOfCreation(), i.getTimeAlive()));
            }
        }
        return out;
    }

    public List<LotProfile> getComplitedLotsByLogin(String login) throws DBException{
        List<SignatureDataSet> datasets=complitedDeal.getAllLots();
        List<LotProfile> out=new ArrayList<LotProfile>();
        for (SignatureDataSet i : datasets){
            if (i.getLoginOfCreater()==login || i.getLoginOfLastRate()==login) {
                out.add(new LotProfile(i.getId(), i.getName(), i.getLoginOfCreater(), i.getLoginOfLastRate(),
                        i.getLastPrice(), i.getDescription(), i.getDateOfCreation(), i.getTimeAlive()));
            }
        }
        return out;
    }

    public LotProfile getComplitedLotById(long id) throws DBException {
        SignatureDataSet ds= complitedDeal.getLot(id);
        return new LotProfile(ds.getId(), ds.getName(),ds.getLoginOfCreater(),ds.getLoginOfLastRate(),
                ds.getLastPrice(), ds.getDescription(), ds.getDateOfCreation(), ds.getTimeAlive());
    }

}
