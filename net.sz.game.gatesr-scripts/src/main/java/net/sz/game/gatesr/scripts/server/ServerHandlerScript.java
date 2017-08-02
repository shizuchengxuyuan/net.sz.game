package net.sz.game.gatesr.scripts.server;

import io.netty.channel.ChannelHandlerContext;
import net.sz.framework.nio.tcp.INettyHandler;
import net.sz.framework.scripts.IInitBaseScript;
import net.sz.framework.szlog.SzLogger;
import net.sz.game.gatesr.server.ServerManager;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/shizuchengxuyuan/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class ServerHandlerScript implements INettyHandler, IInitBaseScript {

    private static final SzLogger log = SzLogger.getLogger();

    @Override
    public void _init() {
        ServerManager.getInstance().setServerHandler(this);
    }

    @Override
    public void closeSession(String channelId, ChannelHandlerContext session) {
        INettyHandler.super.closeSession(channelId, session); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void channelActive(String channelId, ChannelHandlerContext session) {
        INettyHandler.super.channelActive(channelId, session); //To change body of generated methods, choose Tools | Templates.
    }

}
