package net.sz.game.gatesr.proto.handler.registerserver;

import net.sz.framework.nio.NettyPool;
import net.sz.framework.nio.tcp.NettyCoder;
import net.sz.framework.nio.tcp.NettyTcpHandler;
import net.sz.framework.scripts.IInitBaseScript;
import net.sz.framework.szlog.SzLogger;
import net.sz.game.gatesr.server.ServerManager;
import net.sz.game.message.proto.RegisterServerMessage;

/**
 *
 * <br>
 * author 失足程序员<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public final class GGServerToServerHandler extends NettyTcpHandler implements IInitBaseScript {

    private static final SzLogger log = SzLogger.getLogger();
    private static final long serialVersionUID = 1L;

    public GGServerToServerHandler() {

    }

    @Override
    public void _init() {
        //把消息自动注册到消息中心
        NettyCoder.register(
                RegisterServerMessage.GGServerToServerMessage.newBuilder(),/*消息体*/
                ServerManager.getInstance().getClientMsgThread().getTid(),/*线程id*/
                this, /*消息执行的handler*/
                null /*消息队列*/
        );
    }

    @Override
    public void run() {
        // TODO 处理RegisterServerMessage.GGServerToServer消息
        RegisterServerMessage.GGServerToServerMessage reqMessage = (RegisterServerMessage.GGServerToServerMessage) getMessage();

    }
}
