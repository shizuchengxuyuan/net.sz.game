package net.sz.game.pmodel.struct;

import java.io.Serializable;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/shizuchengxuyuan/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class SocketListenInfo implements Serializable, Cloneable {

    //
    private String tcpIp;
    //
    private Integer tcpPort;
    //
    private String httpIP;
    //
    private Integer httpPort;

    public SocketListenInfo() {
    }

    public String getTcpIp() {
        return tcpIp;
    }

    public void setTcpIp(String tcpIp) {
        this.tcpIp = tcpIp;
    }

    public Integer getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(Integer tcpPort) {
        this.tcpPort = tcpPort;
    }

    public String getHttpIP() {
        return httpIP;
    }

    public void setHttpIP(String httpIP) {
        this.httpIP = httpIP;
    }

    public Integer getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(Integer httpPort) {
        this.httpPort = httpPort;
    }

    @Override
    public String toString() {
        return "SocketListenInfo{" + "tcpIp=" + tcpIp + ", tcpPort=" + tcpPort + ", httpIP=" + httpIP + ", httpPort=" + httpPort + '}';
    }

}
