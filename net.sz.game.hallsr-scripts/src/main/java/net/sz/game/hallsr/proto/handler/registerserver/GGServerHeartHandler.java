package net.sz.game.hallsr.proto.handler.registerserver;

import net.sz.framework.nio.tcp.NettyCoder;
import net.sz.framework.nio.tcp.NettyTcpHandler;
import net.sz.framework.scripts.IInitBaseScript;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.utils.TimeUtil;
import net.sz.game.hallsr.server.ServerManager;
import net.sz.game.message.proto.RegisterServerMessage;
import net.sz.game.pmodel.struct.ServerInfo;

/**
 *
 * <br>
 * author 失足程序员<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public final class GGServerHeartHandler extends NettyTcpHandler implements IInitBaseScript {

    private static final SzLogger log = SzLogger.getLogger();
    private static final long serialVersionUID = 1L;

    public GGServerHeartHandler() {

    }

    @Override
    public void _init() {
        NettyCoder.register(
                RegisterServerMessage.GGServerHeartMessage.newBuilder(),/*消息体*/
                ServerManager.getInstance().getServerMsgThread().getTid(),/*线程id*/
                this, /*消息执行的handler*/
                null /*消息队列*/
        );
    }

    @Override
    public void run() {
        // TODO 处理RegisterMessage.GHServerHeart消息
        RegisterServerMessage.GGServerHeartMessage reqMessage = (RegisterServerMessage.GGServerHeartMessage) getMessage();
        ServerInfo sessionAttr = getSessionAttr("serverinfo", ServerInfo.class);
        long time = TimeUtil.currentTimeMillis() - reqMessage.getTime();
        sessionAttr.setYanci(time);
        sessionAttr.setLastUpdatetimer(TimeUtil.currentTimeMillis());
        log.error(sessionAttr);

    }
}
