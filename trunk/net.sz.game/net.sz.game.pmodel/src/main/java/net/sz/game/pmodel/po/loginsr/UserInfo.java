package net.sz.game.pmodel.po.loginsr;

import java.io.Serializable;
import net.sz.game.pmodel.UserKey;
import net.sz.framework.db.AttColumn;
import net.sz.framework.db.AttTable;

/**
 * 用户信息表
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
@AttTable(name = "UserInfo")
public class UserInfo extends UserKey implements Serializable {

    private static final long serialVersionUID = -8907709646630947645L;
    @AttColumn(key = true, nullable = false, unique = true)
    private long id;
    /**
     *
     */
    @AttColumn(nullable = false, unique = true)
    private String userName;
    /**
     *
     */
    @AttColumn(nullable = false, unique = true)
    private String userNameLowerCase;
    /**
     *
     */
    @AttColumn(nullable = false)
    private String userPwd;
    /**
     *
     */
    @AttColumn(nullable = false)
    private String userPhone;
    /**
     *
     */
    @AttColumn(nullable = false)
    private String userMail;
    /**
     *
     */
    @AttColumn(nullable = false)
    private long createTime;
    @AttColumn(nullable = false)
    private long createIP;
    /**
     *
     */
    @AttColumn(nullable = false)
    private long lastLoginTime;
    /**
     *
     */
    @AttColumn(nullable = false)
    private long lastLoginIP;
    /*系统信息*/
    private String osString;
    /*推广信息*/
    private String frString;

    /**
     * 状态，1正常，2表示不可登录
     */
    @AttColumn(nullable = false)
    private int Status;
    //登录后生成的
    private transient String token;
    //生成token 的时间
    private transient long tokenTime;

    public UserInfo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNameLowerCase() {
        return userNameLowerCase;
    }

    public void setUserNameLowerCase(String userNameLowerCase) {
        this.userNameLowerCase = userNameLowerCase;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTokenTime() {
        return tokenTime;
    }

    public void setTokenTime(long tokenTime) {
        this.tokenTime = tokenTime;
    }

    public long getCreateIP() {
        return createIP;
    }

    public void setCreateIP(long createIP) {
        this.createIP = createIP;
    }

    public long getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(long lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public String getOsString() {
        return osString;
    }

    public void setOsString(String osString) {
        this.osString = osString;
    }

    public String getFrString() {
        return frString;
    }

    public void setFrString(String frString) {
        this.frString = frString;
    }

    @Override
    public String toString() {
        return "UserInfo{" + "id=" + id + ", ZoneID=" + this.getPlatformId() + ", channelID=" + this.getChannelId() + ", userName=" + userName
                + ", userPwd=" + userPwd + ", userPhone=" + userPhone + ", userMail=" + userMail + ", createTime="
                + createTime + ", lastLoginTime=" + lastLoginTime + ", Status=" + Status + ", token=" + token
                + ", tokenTime=" + tokenTime + '}';
    }

}
