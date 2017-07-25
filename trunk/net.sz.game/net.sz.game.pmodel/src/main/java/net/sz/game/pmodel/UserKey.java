package net.sz.game.pmodel;

import java.io.Serializable;

import net.sz.framework.szlog.SzLogger;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class UserKey implements Serializable {

    private static final SzLogger log = SzLogger.getLogger();
    private static final long serialVersionUID = -7548065053806592425L;

    /**
     * 游戏ID
     */
    private String gameId;
    /**
     * 平台ID
     */
    private String platformId;
    /**
     * 渠道ID
     */
    private String channelId;
    /**
     * 服务器ID
     */
    private int serverId;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

}
