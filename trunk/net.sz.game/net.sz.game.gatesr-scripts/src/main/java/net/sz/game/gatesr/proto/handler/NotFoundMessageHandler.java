package net.sz.game.gatesr.proto.handler;

import io.netty.channel.ChannelHandlerContext;
import net.sz.framework.nio.tcp.IHandler.INotFoundMessageHandler;
import net.sz.framework.nio.tcp.NettyCoder;
import net.sz.framework.szlog.SzLogger;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class NotFoundMessageHandler implements INotFoundMessageHandler {

    private static final SzLogger log = SzLogger.getLogger();
    private static final long serialVersionUID = -6398697063304776509L;

    @Override
    public void _init() {
        NettyCoder.setiNotFoundMessageHandler(this);
    }

    @Override
    public void notFoundHandler(ChannelHandlerContext ctx, int msgId, byte[] bytebuf) {

    }

}
