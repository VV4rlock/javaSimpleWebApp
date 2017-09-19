package accounts;

import dbUsers.DBException;
import dbUsers.DBService;
import dbUsers.dataSets.UsersDataSet;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class AccountService {

    private final Map<String, UserProfile> sessionIdToProfile;
    DBService profileDB;
    private static final  String SALT="048c7ac3320da7ef4a820fe79c1da11d";


    public AccountService() {

        sessionIdToProfile = new HashMap<>();
        profileDB = new DBService("Profiles");
    }

    public static String getHashFromString(String input){

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((input+SALT).getBytes());
            return new String(md.digest());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Неверный алгоритм");
            e.printStackTrace();
            return "";
        }

    }


    public void addNewUser(UserProfile userProfile) throws DBException {
        profileDB.addUser(userProfile.getLogin(), userProfile.getPass(), userProfile.getPurse());
    }

    public UserProfile getUserByLogin(String login) throws DBException {

        UsersDataSet dataSet= profileDB.getUser(login);
        return new UserProfile(dataSet.getId(),dataSet.getName(),dataSet.getPassword(),dataSet.getPurse());

    }

    public UserProfile getUserById(long id) throws DBException {

        UsersDataSet dataSet= profileDB.getUser(id);
        return new UserProfile(dataSet.getId(),dataSet.getName(),dataSet.getPassword(),dataSet.getPurse());

    }

    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
