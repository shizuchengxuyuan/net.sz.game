package net.sz.game.pmodel.struct;

import java.io.Serializable;
import java.util.ArrayList;
import net.sz.game.pmodel.ServerTypeEnum;

/**
 * 服务器信息
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class ServerInfo implements Serializable, Cloneable {

    private static final long serialVersionUID = 9127165777967165076L;

    private int serverId;
    /*服务器类型*/
    private ServerTypeEnum serverTypeEnum;
    //最大在线人数
    private Integer maxCount;

    private String serverName;
    /**
     * 判断链接是否通畅
     */
    private boolean serverConnect = false;
    private ArrayList<SocketListenInfo> listenInfos = new ArrayList<>();
    /**
     * 服务器是否启动完毕
     */
    private boolean serverEnd = false;
    /*标识服务器需要结束，不在继续分配游戏或人员*/
    private boolean serverOver = false;
    //服务器程序当前版本
    private int serverCode;
    //服务器最后一次同步
    private transient long lastUpdatetimer;
    //服务器最后一次同步链接量
    private transient int lastUpdatecount;
    /*服务器延迟*/
    private transient long yanci;
    private transient String sessionId;

    public ServerInfo() {
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public ServerTypeEnum getServerTypeEnum() {
        return serverTypeEnum;
    }

    public void setServerTypeEnum(ServerTypeEnum serverTypeEnum) {
        this.serverTypeEnum = serverTypeEnum;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public ArrayList<SocketListenInfo> getListenInfos() {
        return listenInfos;
    }

    public void setListenInfos(ArrayList<SocketListenInfo> listenInfos) {
        this.listenInfos = listenInfos;
    }

    public boolean isServerEnd() {
        return serverEnd;
    }

    public void setServerEnd(boolean serverEnd) {
        this.serverEnd = serverEnd;
    }

    public boolean isServerOver() {
        return serverOver;
    }

    public void setServerOver(boolean serverOver) {
        this.serverOver = serverOver;
    }

    public int getServerCode() {
        return serverCode;
    }

    public void setServerCode(int serverCode) {
        this.serverCode = serverCode;
    }

    public long getLastUpdatetimer() {
        return lastUpdatetimer;
    }

    public void setLastUpdatetimer(long lastUpdatetimer) {
        this.lastUpdatetimer = lastUpdatetimer;
    }

    public int getLastUpdatecount() {
        return lastUpdatecount;
    }

    public void setLastUpdatecount(int lastUpdatecount) {
        this.lastUpdatecount = lastUpdatecount;
    }

    public long getYanci() {
        return yanci;
    }

    public void setYanci(long yanci) {
        this.yanci = yanci;
    }

    public boolean isServerConnect() {
        return serverConnect;
    }

    public void setServerConnect(boolean serverConnect) {
        this.serverConnect = serverConnect;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        String listenString = "";
        for (SocketListenInfo listenInfo : listenInfos) {
            listenString += listenInfo.toString();
        }
        return "ServerInfo{" + "serverId=" + serverId + ", serverTypeEnum=" + serverTypeEnum + ", listenInfos=" + listenString + ", serverEnd=" + serverEnd + ", serverCode=" + serverCode + ", lastUpdatetimer=" + lastUpdatetimer + ", yanci=" + yanci + ", sessionId=" + sessionId + '}';
    }

}
