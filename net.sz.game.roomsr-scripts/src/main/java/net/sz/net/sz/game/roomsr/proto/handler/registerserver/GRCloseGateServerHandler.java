package net.sz.net.sz.game.roomsr.proto.handler.registerserver;

import net.sz.framework.nio.tcp.NettyCoder;
import net.sz.framework.nio.tcp.NettyTcpHandler;
import net.sz.framework.scripts.IInitBaseScript;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.nio.tcp.ServerSessionClient;
import net.sz.framework.szthread.ThreadPool;
import net.sz.game.message.proto.RegisterServerMessage;
import net.sz.game.roomsr.server.ServerManager;

/**
 *
 * <br>
 * author 失足程序员<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public final class GRCloseGateServerHandler extends NettyTcpHandler implements IInitBaseScript {

    private static final SzLogger log = SzLogger.getLogger();
    private static final long serialVersionUID = 1L;

    @Override
    public void _init() {
        //把消息自动注册到消息中心
        NettyCoder.register(
                RegisterServerMessage.GRCloseGateServerMessage.newBuilder(),/*消息体*/
                ThreadPool.GlobalThread.getTid(),/*线程id*/
                this, /*消息执行的handler*/
                null /*消息队列*/
        );
    }

    public GRCloseGateServerHandler() {

    }

    @Override
    public void run() {
        // TODO 处理RegisterServerMessage.GRCloseGateServer消息
        RegisterServerMessage.GRCloseGateServerMessage reqMessage = (RegisterServerMessage.GRCloseGateServerMessage) getMessage();
        int serverId = reqMessage.getServerInfo().getServerId();
        log.error("关闭网关服务器：" + serverId);
        ServerSessionClient serverSessionClient = ServerManager.getInstance().getGateServerSessionClientMap().remove(serverId);
        if (serverSessionClient != null) {
            serverSessionClient.close();
        }
    }
}
