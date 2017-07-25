package net.sz.game.gatesr.scripts.timer;

import io.netty.channel.ChannelHandlerContext;
import net.sz.framework.nio.tcp.NettyCoder;
import net.sz.framework.scripts.timer.iscript.ISecondsEventTimerScript;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.utils.TimeUtil;
import net.sz.game.gatesr.server.ServerManager;
import net.sz.game.message.proto.RegisterServerMessage;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class SendGateHartScript implements ISecondsEventTimerScript {

    private static final SzLogger log = SzLogger.getLogger();
    ChannelHandlerContext[] name = new ChannelHandlerContext[0];

    @Override
    public void run(int sec) {
        if (sec % 5 == 0) {

            RegisterServerMessage.GGServerHeartMessage.Builder newBuilder = RegisterServerMessage.GGServerHeartMessage.newBuilder();
            newBuilder.setTime(TimeUtil.currentTimeMillis());
            RegisterServerMessage.GGServerHeartMessage build = newBuilder.build();
            ChannelHandlerContext[] toArray = ServerManager.getInstance().getRoomServerSessionMap().values().toArray(name);

            for (ChannelHandlerContext channelHandlerContext : toArray) {
                NettyCoder.getDefaultCoder().send(channelHandlerContext, build);
            }

        }
    }

}
