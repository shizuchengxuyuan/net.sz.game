package net.sz.game.gatesr.proto.handler.registerserver;

import net.sz.framework.nio.NettyPool;
import net.sz.framework.nio.tcp.NettyTcpHandler;
import net.sz.framework.scripts.IInitBaseScript;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.nio.tcp.NettyCoder;
import net.sz.framework.szthread.ThreadPool;
import net.sz.framework.utils.GlobalUtil;
import net.sz.framework.utils.TimeUtil;
import net.sz.game.message.proto.RegisterServerMessage;
import net.sz.game.pmodel.ServerTypeEnum;

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
                net.sz.game.message.proto.RegisterServerMessage.GGServerHeartMessage.newBuilder(),/*消息体*/
                ThreadPool.GlobalThread.getTid(),/*线程id*/
                this, /*消息执行的handler*/
                null /*消息队列*/
        );
    }

    @Override
    public void run() {
        // TODO 处理RegisterMessage.HGServerHeart消息
        RegisterServerMessage.GGServerHeartMessage reqMessage = (RegisterServerMessage.GGServerHeartMessage) getMessage();

        ServerTypeEnum serverTypeEnum = ServerTypeEnum.getServerType(reqMessage.getServerType());

        if (serverTypeEnum == ServerTypeEnum.RoomServer) {
            Integer gateserverid = this.getSessionAttr("gateserverid", int.class);
            long time = TimeUtil.currentTimeMillis() - reqMessage.getTime();
            log.error("房间服务器：" + gateserverid + " 延迟：" + time);
        } else {
            if (GlobalUtil.SERVERSTARTEND) {
                RegisterServerMessage.GGServerHeartMessage.Builder serverheart = RegisterServerMessage.GGServerHeartMessage.newBuilder();
                serverheart.setTime(reqMessage.getTime());
                serverheart.setPlayerCount(NettyPool.getInstance().getSessions().size());
                NettyCoder.getDefaultCoder().send(getSession(), serverheart);
            }
        }

    }
}
