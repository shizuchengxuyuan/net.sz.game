package net.sz.chess.roomsr.proto.handler.registerserver;

import net.sz.framework.nio.tcp.NettyCoder;
import net.sz.framework.nio.tcp.NettyTcpHandler;
import net.sz.framework.scripts.IInitBaseScript;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.szthread.ThreadPool;
import net.sz.game.message.proto.RegisterServerMessage;

/**
 *
 * <br>
 * author 失足程序员<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public final class HGServerAllInfoHandler extends NettyTcpHandler implements IInitBaseScript {

    private static final SzLogger log = SzLogger.getLogger();
    private static final long serialVersionUID = 1L;

    @Override
    public void _init() {
        //把消息自动注册到消息中心
        NettyCoder.register(
                RegisterServerMessage.HGServerAllInfoMessage.newBuilder(),/*消息体*/
                ThreadPool.GlobalThread.getTid(),/*线程id*/
                this, /*消息执行的handler*/
                null /*消息队列*/
        );
    }

    public HGServerAllInfoHandler() {

    }

    @Override
    public void run() {
        // TODO 处理RegisterServerMessage.HGServerAllInfo消息
        RegisterServerMessage.HGServerAllInfoMessage reqMessage = (RegisterServerMessage.HGServerAllInfoMessage) getMessage();

    }
}
