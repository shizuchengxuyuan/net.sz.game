package net.sz.game.hallsr.proto.handler;

import io.netty.channel.ChannelHandlerContext;
import net.sz.framework.nio.tcp.IHandler.INotFoundMessageHandler;
import net.sz.framework.nio.tcp.NettyCoder;
import net.sz.framework.szlog.SzLogger;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/shizuchengxuyuan/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class NotFoundMessageHandler implements INotFoundMessageHandler {

    private static final SzLogger log = SzLogger.getLogger();

    @Override
    public void _init() {
        NettyCoder.setiNotFoundMessageHandler(this);
    }

    @Override
    public void notFoundHandler(ChannelHandlerContext ctx, int msgId, byte[] bytebuf) {

    }

}
