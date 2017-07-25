package net.sz.game.loginsr.server.tcpserver;

import io.netty.channel.ChannelHandlerContext;
import net.sz.framework.nio.NettyPool;
import net.sz.framework.nio.tcp.INettyHandler;
import net.sz.framework.nio.tcp.NettyCoder;
import net.sz.framework.nio.tcp.NettyTcpServer;
import net.sz.framework.szlog.SzLogger;

/**
 * socket tcp 监听中心
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class TcpServerManager {

    private static final SzLogger log = SzLogger.getLogger();

    private static final TcpServerManager IN_ME = new TcpServerManager();

    public static TcpServerManager getInstance() {
        return IN_ME;
    }

    NettyTcpServer addBindTcpServer;

    private TcpServerManager() {
    }

    public void startServer(int port) {

        addBindTcpServer = NettyPool.getInstance().addBindTcpServer("0.0.0.0", port, NettyCoder.getDefaultCoder(), new INettyHandler() {

        });

        addBindTcpServer.start(4);
    }

    /**
     *
     * @deprecated
     */
    @Deprecated
    public void stopServer() {
        if (addBindTcpServer != null) {
            addBindTcpServer.close();
        }
    }
}
