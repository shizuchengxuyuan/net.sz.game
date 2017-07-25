package net.sz.game.gatesr.scripts.server;

import io.netty.channel.ChannelHandlerContext;
import net.sz.framework.nio.tcp.NettyCoder;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.utils.GlobalUtil;
import net.sz.framework.utils.TimeUtil;
import net.sz.game.gatesr.server.ServerManager;
import net.sz.game.gatesr.server.iscript.IStopServerScript;
import net.sz.game.message.proto.RegisterServerMessage;

/**
 * 停止服务器定时器
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class StopServerScript implements IStopServerScript {

    private static final SzLogger log = SzLogger.getLogger();

    @Override
    public void checkStopServer() {
        if (ServerManager.getInstance().getStopServerTime() <= 0) {
            return;
        }
        long stoptimer = TimeUtil.currentTimeMillis() - ServerManager.getInstance().getStopServerTime();
        if (stoptimer <= 0) {
            log.error("网关服务器即将关闭");
            return;
        }

        RegisterServerMessage.GRCloseGateServerMessage.Builder newBuilder = RegisterServerMessage.GRCloseGateServerMessage.newBuilder();
        RegisterServerMessage.ServerConnectInfo.Builder newBuilder1 = RegisterServerMessage.ServerConnectInfo.newBuilder();
        newBuilder1.setServerId(GlobalUtil.getServerId());
        newBuilder.setServerInfo(newBuilder1);
        RegisterServerMessage.GRCloseGateServerMessage build = newBuilder.build();

        ChannelHandlerContext[] toArray = ServerManager.getInstance().getRoomServerSessionMap().values().toArray(new ChannelHandlerContext[0]);
        for (ChannelHandlerContext channelHandlerContext : toArray) {
            NettyCoder.getDefaultCoder().send(channelHandlerContext, build);
        }

        if (stoptimer > 60000) {
            System.exit(0);
        }

    }

}
