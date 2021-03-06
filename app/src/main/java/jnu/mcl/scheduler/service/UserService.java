package jnu.mcl.scheduler.service;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import jnu.mcl.scheduler.connector.DBConnector;
import jnu.mcl.scheduler.listener.UserServiceListener;
import jnu.mcl.scheduler.model.UserModel;

/**
 * Created by 김 on 2015-11-27.
 */
public class UserService {

    private ArrayList<UserServiceListener> userServiceListeners;
    private static UserService newInstance;
    private DBConnector dbConnector;

    private UserService() {
        dbConnector = DBConnector.getInstance();
        userServiceListeners = new ArrayList<UserServiceListener>();
    }

    public static UserService getInstance() {
        if (newInstance == null) {
            newInstance = new UserService();
        }
        return newInstance;
    }

    public ArrayList<UserModel> getUserList() {
        ArrayList<UserModel> userModelArrayList = new ArrayList<UserModel>();
        Connection conn = dbConnector.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from t_user");
            while (resultSet.next()) {
                UserModel userModel = new UserModel();
                userModel.setNo(resultSet.getInt(1));
                userModel.setId(resultSet.getString(2));
                userModel.setNickname(resultSet.getString(3));
                userModel.setDescription(resultSet.getString(5));
                userModelArrayList.add(userModel);
            }
            conn.close();
        } catch (Exception e) {
            Log.w("Error connection", e);
        }
        return userModelArrayList;
    }

    public UserModel getUser(String id) {
        UserModel userModel = null;
        Connection conn = dbConnector.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from t_user where id='" + id + "'");
            if (resultSet.next()) {
                userModel = new UserModel();
                userModel.setNo(resultSet.getInt(1));
                userModel.setId(resultSet.getString(2));
                userModel.setNickname(resultSet.getString(3));
                userModel.setDescription(resultSet.getString(5));
            }
            conn.close();
        } catch (Exception e) {
            Log.w("Error connection", e);
        }
        return userModel;
    }

    public UserModel getUser(String id, String nickname) {
        UserModel userModel = null;
        Connection conn = dbConnector.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from t_user where id='" + id + "' and nickname='" + nickname + "'");
            if (resultSet.next()) {
                userModel = new UserModel();
                userModel.setNo(resultSet.getInt(1));
                userModel.setId(resultSet.getString(2));
                userModel.setNickname(resultSet.getString(3));
                userModel.setDescription(resultSet.getString(5));
            }
            conn.close();
        } catch (Exception e) {
            Log.w("Error connection", e);
        }
        return userModel;
    }

    public void addUser(String id, String nickname) {
        Connection conn = dbConnector.getConnection();
        try {
            String query = "insert into t_user (id, nickname) values (?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, nickname);
            preparedStatement.executeUpdate();
            conn.close();
        } catch (Exception e) {
            Log.w("Error connection", e);
        }
    }

    public void updateUser(String id, String nickname, String description) {
        Connection conn = dbConnector.getConnection();
        try {
            String query = "update t_user set nickname='" + nickname + "', description='" + description + "' where id='" + id + "'";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.executeUpdate();
            conn.close();
        } catch (Exception e) {
            Log.w("Error connection", e);
        }

        notifyUserUpdate();
    }

    public void addUserServiceListener(UserServiceListener userServiceListener) {
        if (!userServiceListeners.contains(userServiceListener)) {
            userServiceListeners.add(userServiceListener);
        }
    }

    public void notifyUserUpdate() {
        for (UserServiceListener userServiceListener : userServiceListeners) {
            userServiceListener.onUserUpdate();
        }
    }

}
