package net.sz.game.gatesr.proto.handler.registerserver;

import net.sz.framework.nio.NettyPool;
import net.sz.framework.nio.tcp.NettyCoder;
import net.sz.framework.nio.tcp.NettyTcpHandler;
import net.sz.framework.scripts.IInitBaseScript;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.szthread.ThreadPool;
import net.sz.framework.utils.GlobalUtil;
import net.sz.game.gatesr.server.ServerManager;
import net.sz.game.message.proto.RegisterServerMessage;

/**
 *
 * <br>
 * author 失足程序员<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public final class HGRegisterServerHandler extends NettyTcpHandler implements IInitBaseScript {

    private static final SzLogger log = SzLogger.getLogger();
    private static final long serialVersionUID = 1L;

    public HGRegisterServerHandler() {

    }

    @Override
    public void _init() {
        NettyCoder.register(
                RegisterServerMessage.HGRegisterServerMessage.newBuilder(),/*消息体*/
                ThreadPool.GlobalThread.getTid(),/*线程id*/
                this, /*消息执行的handler*/
                null /*消息队列*/
        );
    }

    @Override
    public void run() {
        // TODO 处理RegisterMessage.HGRegister消息
        RegisterServerMessage.HGRegisterServerMessage reqMessage = (RegisterServerMessage.HGRegisterServerMessage) getMessage();
        /*内网监听*/
        RegisterServerMessage.ServerConnectInfo serverInfo0 = reqMessage.getServerInfo(0);
        int serverId1 = serverInfo0.getServerId();

        GlobalUtil.setServerId(serverId1);

        int tcpPort1 = serverInfo0.getTcpPort();
        int httpPort1 = serverInfo0.getHttpPort();

        /*外网监听，客户端监听*/
        RegisterServerMessage.ServerConnectInfo serverInfo1 = reqMessage.getServerInfo(1);
        int tcpPort2 = serverInfo1.getTcpPort();
        int httpPort2 = serverInfo1.getHttpPort();

        ServerManager.getInstance().startEnd(tcpPort1, httpPort1, tcpPort2);
    }
}
