package net.sz.game.hallsr.scripts.timer;

import io.netty.channel.ChannelHandlerContext;
import net.sz.framework.nio.tcp.NettyCoder;
import net.sz.framework.scripts.timer.iscript.ISecondsEventTimerScript;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.utils.GlobalUtil;
import net.sz.framework.utils.TimeUtil;
import net.sz.game.hallsr.server.ServerManager;
import net.sz.game.message.proto.RegisterServerMessage;
import net.sz.game.pmodel.struct.ServerInfo;

/**
 * 清理 ServerInfo
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class ClearServerInfoScript implements ISecondsEventTimerScript {

    private static final SzLogger log = SzLogger.getLogger();

    @Override
    public void run(int sec) {
        /*TODO 删除服务器的时候，记得通知对应的服务器关闭链接*/
        if (sec % 10 == 0) {

            {
                ServerInfo[] toArray = ServerManager.getInstance().getRoomServerInfoMap().values().toArray(new ServerInfo[0]);
                for (ServerInfo serverInfo : toArray) {
                    if (TimeUtil.currentTimeMillis() - serverInfo.getLastUpdatetimer() > 30000) {
                        ServerManager.getInstance().getRoomServerInfoMap().remove(serverInfo.getServerId());
                        ServerManager.getInstance().getRoomIds().remove(serverInfo.getServerId());
                    }
                }
            }

            {
                ServerInfo[] toArray = ServerManager.getInstance().getGateServerInfoMap().values().toArray(new ServerInfo[0]);
                for (ServerInfo serverInfo : toArray) {
                    if (TimeUtil.currentTimeMillis() - serverInfo.getLastUpdatetimer() > 30000) {
                        ServerManager.getInstance().getGateServerInfoMap().remove(serverInfo.getServerId());
                        RegisterServerMessage.GRCloseGateServerMessage.Builder newBuilder = RegisterServerMessage.GRCloseGateServerMessage.newBuilder();
                        RegisterServerMessage.ServerConnectInfo.Builder newBuilder1 = RegisterServerMessage.ServerConnectInfo.newBuilder();
                        newBuilder1.setServerId(GlobalUtil.getServerId());
                        newBuilder.setServerInfo(newBuilder1);
                        RegisterServerMessage.GRCloseGateServerMessage build = newBuilder.build();

                        ChannelHandlerContext[] toArray1s = ServerManager.getInstance().getRoomServerSessionMap().values().toArray(new ChannelHandlerContext[0]);

                        for (ChannelHandlerContext channelHandlerContext : toArray1s) {
                            NettyCoder.getDefaultCoder().send(channelHandlerContext, build);
                        }
                    }
                }
            }

        }

    }

}
