package net.sz.game.hallsr.scripts.timer;

import io.netty.channel.ChannelHandlerContext;
import net.sz.framework.nio.NettyPool;
import net.sz.framework.nio.tcp.NettyCoder;
import net.sz.framework.scripts.timer.iscript.ISecondsEventTimerScript;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.utils.TimeUtil;
import net.sz.game.hallsr.server.ServerManager;
import net.sz.game.message.proto.RegisterServerMessage;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class ServerHeartScript implements ISecondsEventTimerScript {

    private static final SzLogger log = SzLogger.getLogger();
    ChannelHandlerContext[] name = new ChannelHandlerContext[0];

    @Override
    public void run(int sec) {

        if (sec % 5 == 0) {

            RegisterServerMessage.GGServerHeartMessage.Builder serverheart = RegisterServerMessage.GGServerHeartMessage.newBuilder();
            serverheart.setServerType(ServerManager.getInstance().getServerType().getStype());
            serverheart.setTime(TimeUtil.currentTimeMillis());
            RegisterServerMessage.GGServerHeartMessage build = serverheart.build();

            byte[] bytesFormMessage = NettyCoder.getDefaultCoder().getBytesFormMessage(build);

//            ByteBuf buffer = Unpooled.buffer();
//            buffer.writeBytes(bytesFormMessage);
//            buffer.writeBytes(bytesFormMessage);
//            buffer.writeBytes(bytesFormMessage);
//            buffer.writeBytes(bytesFormMessage);
//            byte[] byteBufFormBytes = NettyCoder.getDefaultCoder().getByteBufFormBytes(buffer);

            ChannelHandlerContext[] toArray = NettyPool.getInstance().getSessions().values().toArray(name);

            for (ChannelHandlerContext channelHandlerContext : toArray) {
                NettyCoder.getDefaultCoder().send(channelHandlerContext, bytesFormMessage);
            }
        }
    }
}
