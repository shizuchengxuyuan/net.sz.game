package net.sz.net.sz.game.roomsr.proto.handler.registerserver;

import io.netty.channel.ChannelHandlerContext;
import java.util.List;
import net.sz.framework.nio.tcp.NettyTcpHandler;
import net.sz.framework.scripts.IInitBaseScript;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.nio.tcp.INettyHandler;
import net.sz.framework.nio.tcp.NettyCoder;
import net.sz.framework.nio.tcp.ServerSessionClient;
import net.sz.framework.szthread.ThreadPool;
import net.sz.framework.utils.GlobalUtil;
import net.sz.game.message.proto.RegisterServerMessage;
import net.sz.game.roomsr.server.ServerManager;

/**
 *
 * <br>
 * author 失足程序员<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public final class HGGateServerInfoHandler extends NettyTcpHandler implements IInitBaseScript {

    private static final SzLogger log = SzLogger.getLogger();
    private static final long serialVersionUID = 1L;

    @Override
    public void _init() {
        //把消息自动注册到消息中心
        NettyCoder.register(
                RegisterServerMessage.HGGateServerInfoMessage.newBuilder(),/*消息体*/
                ThreadPool.GlobalThread.getTid(),/*线程id*/
                this, /*消息执行的handler*/
                null /*消息队列*/
        );
    }

    public HGGateServerInfoHandler() {

    }

    @Override
    public void run() {
        // TODO 处理RegisterServerMessage.HGGateServerInfo消息
        RegisterServerMessage.HGGateServerInfoMessage reqMessage = (RegisterServerMessage.HGGateServerInfoMessage) getMessage();
        List<RegisterServerMessage.ServerConnectInfo> serverInfoList = reqMessage.getServerInfoList();
        for (RegisterServerMessage.ServerConnectInfo serverConnectInfo : serverInfoList) {
            if (!ServerManager.getInstance().getGateServerSessionClientMap().contains(serverConnectInfo.getServerId())) {

                ServerSessionClient serverSessionClient = new ServerSessionClient(serverConnectInfo.getTcpIp(),
                        serverConnectInfo.getTcpPort(), 1, 1, NettyCoder.getDefaultCoder(), new INettyHandler() {

                    @Override
                    public void channelActive(String channelId, ChannelHandlerContext session) {
                        RegisterServerMessage.RGRegisterServerMessage.Builder newBuilder = RegisterServerMessage.RGRegisterServerMessage.newBuilder();
                        RegisterServerMessage.ServerConnectInfo.Builder newBuilder1 = RegisterServerMessage.ServerConnectInfo.newBuilder();
                        newBuilder1.setServerId(GlobalUtil.getServerId());
                        newBuilder.setServerInfo(newBuilder1);

                        RegisterServerMessage.RGRegisterServerMessage build = newBuilder.build();
                        NettyCoder.getDefaultCoder().send(session, build);
                    }

                });

                ServerManager.getInstance().getGateServerSessionClientMap().put(serverConnectInfo.getServerId(), serverSessionClient);

            }
        }
    }
}
