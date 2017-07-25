package net.sz.game.hallsr.scripts.server;

import io.netty.channel.ChannelHandlerContext;
import net.sz.framework.nio.tcp.INettyHandler;
import net.sz.framework.nio.tcp.NettyCoder;
import net.sz.framework.scripts.IInitBaseScript;
import net.sz.framework.szlog.SzLogger;
import net.sz.game.hallsr.server.ServerManager;
import net.sz.game.hallsr.server.tcpserver.TcpServerManager;
import net.sz.game.pmodel.struct.ServerInfo;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class TcpHandlerScript implements INettyHandler, IInitBaseScript {

    private static final SzLogger log = SzLogger.getLogger();

    @Override
    public void _init() {
        TcpServerManager.getInstance().setiNettyHandler(this);
    }

    @Override
    public void channelActive(String channelId, ChannelHandlerContext session) {

    }

    @Override
    public void closeSession(String channelId, ChannelHandlerContext session) {

        ServerInfo sessionAttr = NettyCoder.getSessionAttr(session, "serverinfo", ServerInfo.class);
        if (sessionAttr != null) {
            sessionAttr.setServerEnd(false);
            /**
             * 删除链接缓存
             */
            ServerManager.getInstance().getGateServerSessionMap().remove(sessionAttr.getServerId());
            ServerManager.getInstance().getRoomServerSessionMap().remove(sessionAttr.getServerId());
        }
//                if (sessionAttr != null) {
//                    if (sessionAttr.getServerType() == ServerTypeEnum.GateServer) {
//                        ServerManager.getInstance().getGateIds().remove(sessionAttr.getServerId());
//                    } else if (sessionAttr.getServerType() == ServerTypeEnum.RoomServer) {
//                        ServerManager.getInstance().getGateIds().remove(sessionAttr.getServerId());
//                    }
//                }
    }

}
