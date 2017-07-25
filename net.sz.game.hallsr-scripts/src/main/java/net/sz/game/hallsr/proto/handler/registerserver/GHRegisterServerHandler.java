package net.sz.game.hallsr.proto.handler.registerserver;

import io.netty.channel.ChannelHandlerContext;
import net.sz.framework.nio.tcp.NettyCoder;
import net.sz.framework.nio.tcp.NettyTcpHandler;
import net.sz.framework.scripts.IInitBaseScript;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.utils.TimeUtil;
import net.sz.game.hallsr.server.ServerManager;
import net.sz.game.message.proto.RegisterServerMessage;
import net.sz.game.pmodel.ServerTypeEnum;
import static net.sz.game.pmodel.ServerTypeEnum.GateServer;
import static net.sz.game.pmodel.ServerTypeEnum.HallServer;
import static net.sz.game.pmodel.ServerTypeEnum.LoginServer;
import static net.sz.game.pmodel.ServerTypeEnum.RoomServer;
import net.sz.game.pmodel.struct.ServerInfo;
import net.sz.game.pmodel.struct.SocketListenInfo;

/**
 *
 * <br>
 * author 失足程序员<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public final class GHRegisterServerHandler extends NettyTcpHandler implements IInitBaseScript {

    private static final SzLogger log = SzLogger.getLogger();
    private static final long serialVersionUID = 1L;

    public GHRegisterServerHandler() {

    }

    @Override
    public void _init() {
        NettyCoder.register(
                RegisterServerMessage.GHRegisterServerMessage.newBuilder(),/*消息体*/
                ServerManager.getInstance().getServerMsgThread().getTid(),/*线程id*/
                this, /*消息执行的handler*/
                null /*消息队列*/
        );
    }

    @Override
    public void run() {
        // TODO 处理RegisterHallSrMessage.GHRegisterHallSr消息
        RegisterServerMessage.GHRegisterServerMessage reqMessage = (RegisterServerMessage.GHRegisterServerMessage) getMessage();

        int serverCode = reqMessage.getServerInfo().getServerCode();
        int serverType = reqMessage.getServerInfo().getServerType();
        int serverId = reqMessage.getServerInfo().getServerId();
        boolean serverEnd = reqMessage.getServerInfo().getServerEnd();

        ServerTypeEnum serverTypeEnum = ServerTypeEnum.getServerType(serverType);

        ServerInfo serverInfo = null;
        if (serverTypeEnum == ServerTypeEnum.GateServer) {
            serverInfo = ServerManager.getInstance().getGateServerInfoMap().get(serverId);
        } else if (serverTypeEnum == ServerTypeEnum.RoomServer) {
            serverInfo = ServerManager.getInstance().getRoomServerInfoMap().get(serverId);
        } else {
            return;
        }

        if (serverInfo == null) {
            serverInfo = new ServerInfo();
            if (serverId != 0) {
                serverInfo.setServerId(serverId);
            }
            serverInfo.setServerCode(serverCode);
            serverInfo.setServerTypeEnum(serverTypeEnum);
            serverInfo.setServerName("");

            String ip = NettyCoder.getIP(getSession());

            SocketListenInfo socketListenInfo = new SocketListenInfo();

            socketListenInfo.setHttpIP(ip);
            socketListenInfo.setTcpIp(ip);

            serverInfo.getListenInfos().add(socketListenInfo);

            switch (serverTypeEnum) {
                case GateServer:

                    if (serverId == 0) {
                        serverInfo.setServerId(ServerManager.getInstance().getNextGateId());
                    }
                    socketListenInfo.setTcpPort(7000 + serverInfo.getServerId());
                    socketListenInfo.setHttpPort(7500 + serverInfo.getServerId());

                    SocketListenInfo socketListenInfo1 = new SocketListenInfo();

                    socketListenInfo1.setHttpIP(ip);
                    socketListenInfo1.setTcpIp(ip);

                    socketListenInfo1.setTcpPort(9000 + serverInfo.getServerId());
                    socketListenInfo1.setHttpPort(9500 + serverInfo.getServerId());

                    serverInfo.getListenInfos().add(socketListenInfo1);

                    break;
                case RoomServer:

                    if (serverId == 0) {
                        serverInfo.setServerId(ServerManager.getInstance().getNextRoomId());
                    }

                    socketListenInfo.setTcpPort(8000 + serverInfo.getServerId());
                    socketListenInfo.setHttpPort(8500 + serverInfo.getServerId());

                    break;
                case LoginServer:
                    break;
                case HallServer:
                    break;
            }
        }

        serverInfo.setLastUpdatetimer(TimeUtil.currentTimeMillis());

        serverInfo.setServerEnd(serverEnd);

//        if (serverEnd) {
        this.setSessionAttr("serverinfo", serverInfo);
//        }

        log.error("收到服务器注册信息：" + serverInfo);

        if (serverTypeEnum == ServerTypeEnum.GateServer) {

            ServerManager.getInstance().getGateServerInfoMap().put(serverInfo.getServerId(), serverInfo);
            ServerManager.getInstance().getGateServerSessionMap().put(serverInfo.getServerId(), getSession());

            if (serverEnd) {

                RegisterServerMessage.HGGateServerInfoMessage.Builder gateServerInfoBuilder = RegisterServerMessage.HGGateServerInfoMessage.newBuilder();
                RegisterServerMessage.ServerConnectInfo.Builder connectBuilder = RegisterServerMessage.ServerConnectInfo.newBuilder();
                connectBuilder.setServerId(serverInfo.getServerId());

                SocketListenInfo socketListenInfo = serverInfo.getListenInfos().get(0);

                connectBuilder.setTcpIp(socketListenInfo.getTcpIp());
                connectBuilder.setTcpPort(socketListenInfo.getTcpPort());
                connectBuilder.setHttpIp(socketListenInfo.getTcpIp());
                connectBuilder.setHttpPort(socketListenInfo.getHttpPort());

                gateServerInfoBuilder.addServerInfo(connectBuilder);
                RegisterServerMessage.HGGateServerInfoMessage build = gateServerInfoBuilder.build();

                ChannelHandlerContext[] toArray = ServerManager.getInstance().getRoomServerSessionMap().values().toArray(new ChannelHandlerContext[0]);

                for (ChannelHandlerContext channelHandlerContext : toArray) {
                    NettyCoder.getDefaultCoder().send(channelHandlerContext, build);
                }
            }

        } else if (serverTypeEnum == ServerTypeEnum.RoomServer) {

            ServerManager.getInstance().getRoomServerSessionMap().put(serverInfo.getServerId(), getSession());
            ServerManager.getInstance().getRoomServerInfoMap().put(serverInfo.getServerId(), serverInfo);

            if (serverEnd) {

                ServerInfo[] toArray = ServerManager.getInstance().getGateServerInfoMap().values().toArray(new ServerInfo[0]);
                for (ServerInfo serverInfo1 : toArray) {
                    if (serverInfo1.isServerEnd()) {

                        RegisterServerMessage.HGGateServerInfoMessage.Builder gateServerInfoBuilder = RegisterServerMessage.HGGateServerInfoMessage.newBuilder();

                        RegisterServerMessage.ServerConnectInfo.Builder connectBuilder = RegisterServerMessage.ServerConnectInfo.newBuilder();
                        SocketListenInfo socketListenInfo = serverInfo1.getListenInfos().get(0);
                        connectBuilder.setServerId(serverInfo1.getServerId());
                        connectBuilder.setTcpIp(socketListenInfo.getTcpIp());
                        connectBuilder.setTcpPort(socketListenInfo.getTcpPort());
                        connectBuilder.setHttpIp(socketListenInfo.getTcpIp());
                        connectBuilder.setHttpPort(socketListenInfo.getHttpPort());

                        gateServerInfoBuilder.addServerInfo(connectBuilder);

                        RegisterServerMessage.HGGateServerInfoMessage build = gateServerInfoBuilder.build();

                        NettyCoder.getDefaultCoder().send(this.getSession(), build);
                    }
                }
            }
        }

        this.setSessionAttr(NettyCoder.SessionLoginTime, TimeUtil.currentTimeMillis());

        /*回复服务器*/
        RegisterServerMessage.HGRegisterServerMessage.Builder newBuilder = RegisterServerMessage.HGRegisterServerMessage.newBuilder();
        for (SocketListenInfo listenInfo : serverInfo.getListenInfos()) {
            RegisterServerMessage.ServerConnectInfo.Builder connectInfo = RegisterServerMessage.ServerConnectInfo.newBuilder();
            connectInfo.setServerId(serverInfo.getServerId());
            connectInfo.setTcpPort(listenInfo.getTcpPort());
            connectInfo.setHttpPort(listenInfo.getHttpPort());
            newBuilder.addServerInfo(connectInfo);
        }

        NettyCoder.getDefaultCoder().send(getSession(), newBuilder);

    }
}
