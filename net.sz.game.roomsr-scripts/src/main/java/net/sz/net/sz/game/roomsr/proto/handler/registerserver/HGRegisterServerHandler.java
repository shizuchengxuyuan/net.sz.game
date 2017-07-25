package net.sz.net.sz.game.roomsr.proto.handler.registerserver;

import net.sz.framework.nio.tcp.NettyCoder;
import net.sz.framework.nio.tcp.NettyTcpHandler;
import net.sz.framework.scripts.IInitBaseScript;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.szthread.ThreadPool;
import net.sz.framework.utils.GlobalUtil;
import net.sz.game.message.proto.RegisterServerMessage;
import net.sz.game.roomsr.server.ServerManager;

/**
 *
 * <br>
 * author 失足程序员<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public final class HGRegisterServerHandler extends NettyTcpHandler implements IInitBaseScript {

    private static final SzLogger log = SzLogger.getLogger();
    private static final long serialVersionUID = 58738637798409155L;

    @Override
    public void _init() {
        NettyCoder.register(
                RegisterServerMessage.HGRegisterServerMessage.newBuilder(),/*消息体*/
                ThreadPool.GlobalThread.getTid(),/*线程id*/
                this, /*消息执行的handler*/
                null /*消息队列*/
        );
    }

    public HGRegisterServerHandler() {

    }

    @Override
    public void run() {
        // TODO 处理RegisterHallSrMessage.HGRegisterHallSr消息
        RegisterServerMessage.HGRegisterServerMessage reqMessage = (RegisterServerMessage.HGRegisterServerMessage) getMessage();
        RegisterServerMessage.ServerConnectInfo serverInfo = reqMessage.getServerInfo(0);
        int serverId = serverInfo.getServerId();
        int tcpPort = serverInfo.getTcpPort();
        int httpPort = serverInfo.getHttpPort();
        GlobalUtil.setServerId(serverId);
        ServerManager.getInstance().initlisten(tcpPort, httpPort);

    }
}
