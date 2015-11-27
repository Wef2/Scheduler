package jnu.mcl.scheduler.model;

/**
 * Created by Kim on 2015-11-27.
 */
public class FriendModel {

    private int no;
    private String nickname;
    private String description;

    public void setNo(int no){
        this.no = no;
    }

    public int getNo(){
        return no;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
