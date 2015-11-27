package jnu.mcl.scheduler.model;

/**
 * Created by 김 on 2015-11-27.
 */
public class UserModel {

    private String id;
    private String nickname;

    public void setId(String id){
        this.id = id;
    }

    public String getID(){
        return id;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public String getNickname(){
        return nickname;
    }
}
