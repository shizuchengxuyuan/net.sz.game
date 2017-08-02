package net.sz.game.gatesr.server;

import io.netty.channel.ChannelHandlerContext;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import net.sz.game.gatesr.server.iscript.IServerManagerScript;
import net.sz.game.gatesr.server.timer.StopServerTimer;
import net.sz.framework.nio.NettyPool;
import net.sz.framework.nio.http.NettyHttpServer;
import net.sz.framework.nio.http.NioHttpRequest;
import net.sz.framework.nio.http.handler.IHttpHandler;
import net.sz.framework.nio.tcp.INettyHandler;
import net.sz.framework.nio.tcp.NettyCoder;
import net.sz.framework.nio.tcp.NettyCoder0;
import net.sz.framework.nio.tcp.NettyTcpServer;
import net.sz.framework.nio.tcp.ServerSessionClient;
import net.sz.framework.scripts.manager.ScriptManager;
import net.sz.framework.struct.thread.BaseThreadRunnable;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.szthread.ThreadPool;
import net.sz.framework.utils.GlobalUtil;
import net.sz.game.gatesr.server.httpserver.httpapi.IHttpAPIScript;
import net.sz.game.pmodel.ServerTypeEnum;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/shizuchengxuyuan/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class ServerManager {

    private static final SzLogger log = SzLogger.getLogger();

    private static final ServerManager IN_ME = new ServerManager();

    public static ServerManager getInstance() {
        return IN_ME;
    }

    private long stopServerTime = 0L;
    private final ServerTypeEnum serverType = ServerTypeEnum.GateServer;
    private int serverCode = 1;

    private BaseThreadRunnable clientMsgThread = ThreadPool.addThread("client-Msg-Thread");

    private BaseThreadRunnable serverMsgThread = ThreadPool.addThread("server-Msg-Thread");
    private String loginConnectString = null;

    /*内网监听，服务器与服务器之间的通信*/
    NettyTcpServer nettyTcpServer1;

    /*外网监听，服务器与客户端*/
    NettyTcpServer nettyTcpServer2;

    /*http协议监听*/
    NettyHttpServer nettyHttpServer1;

    INettyHandler serverHandler;
    INettyHandler clientHandler;

    NettyCoder0 clientNettyCoder0 = new NettyCoder0();

    private IServerManagerScript iServerManagerScript;
    /*网管服务器链接*/
    private ServerSessionClient hallServerSessionClient;
    /*游戏房间服务器请求过来的链接*/
    private ConcurrentHashMap<Integer, ChannelHandlerContext> roomServerSessionMap = new ConcurrentHashMap<>();

    private ServerManager() {
    }

    public String getLoginConnectString() {
        return loginConnectString;
    }

    public void setLoginConnectString(String loginConnectString) {
        this.loginConnectString = loginConnectString;
    }

    public BaseThreadRunnable getClientMsgThread() {
        return clientMsgThread;
    }

    public BaseThreadRunnable getServerMsgThread() {
        return serverMsgThread;
    }

    public INettyHandler getServerHandler() {
        return serverHandler;
    }

    public void setServerHandler(INettyHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

    public INettyHandler getClientHandler() {
        return clientHandler;
    }

    public void setClientHandler(INettyHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public ServerTypeEnum getServerType() {
        return serverType;
    }

    public NettyCoder0 getClientNettyCoder0() {
        return clientNettyCoder0;
    }

    /**
     * 内网监听，服务器与服务器之间的通信
     *
     * @return
     */
    public NettyTcpServer getNettyTcpServer1() {
        return nettyTcpServer1;
    }

    /**
     * 外网监听，服务器与客户端
     *
     * @return
     */
    public NettyTcpServer getNettyTcpServer2() {
        return nettyTcpServer2;
    }

    public NettyHttpServer getNettyHttpServer1() {
        return nettyHttpServer1;
    }

    public int getServerCode() {
        return serverCode;
    }

    public void setServerCode(int serverCode) {
        this.serverCode = serverCode;
    }

    /**
     * 网管服务器链接
     *
     * @return
     */
    public ServerSessionClient getHallServerSessionClient() {
        return hallServerSessionClient;
    }

    /**
     * 游戏房间服务器请求过来的链接
     *
     * @return
     */
    public ConcurrentHashMap<Integer, ChannelHandlerContext> getRoomServerSessionMap() {
        return roomServerSessionMap;
    }

    public IServerManagerScript getiServerManagerScript() {
        return iServerManagerScript;
    }

    public void setiServerManagerScript(IServerManagerScript iServerManagerScript) {
        this.iServerManagerScript = iServerManagerScript;
    }

    public long getStopServerTime() {
        return stopServerTime;
    }

    public void setStopServerTime(long stopServerTime) {
        this.stopServerTime = stopServerTime;
    }

    /**
     * 链接到大厅服务器
     *
     * @param hostName
     * @param port
     */
    public void connectHallServer(String hostName, int port) {

        hallServerSessionClient = new ServerSessionClient(hostName, port, 1, 1, NettyCoder.getDefaultCoder(), new INettyHandler() {

            @Override
            public void channelActive(String channelId, ChannelHandlerContext session) {
                ServerManager.getInstance().getiServerManagerScript().sendServerInfoToHall();
            }

        });
    }

    /**
     *
     * @param tcpPort1 监听端口，内网通信
     * @param httpPort1
     * @param tcpPort2 外网通信，客户端通信
     */
    public void startEnd(int tcpPort1, int httpPort1, int tcpPort2) {
        synchronized (GlobalUtil.SERVERSTARTEND) {
            if (!GlobalUtil.SERVERSTARTEND) {

                ThreadPool.GlobalThread.addTimerTask(new StopServerTimer(5000));

                /*服务器之间通信*/
                nettyTcpServer1 = NettyPool.getInstance().addBindTcpServer("0.0.0.0", tcpPort1, NettyCoder.getDefaultCoder(), new INettyHandler() {

                    @Override
                    public void channelActive(String channelId, ChannelHandlerContext session) {
                        if (ServerManager.this.serverHandler != null) {
                            ServerManager.this.serverHandler.channelActive(channelId, session);
                        }
                    }

                    @Override
                    public void closeSession(String channelId, ChannelHandlerContext session) {
                        if (ServerManager.this.serverHandler != null) {
                            ServerManager.this.serverHandler.closeSession(channelId, session);
                        }
                    }
                });

                nettyTcpServer1.start(4);

                /*客户端通信*/
                nettyTcpServer2 = NettyPool.getInstance().addBindTcpServer("0.0.0.0", tcpPort2, clientNettyCoder0, new INettyHandler() {

                    @Override
                    public void channelActive(String channelId, ChannelHandlerContext session) {
                        if (ServerManager.this.clientHandler != null) {
                            ServerManager.this.clientHandler.channelActive(channelId, session);
                        }
                    }

                    @Override
                    public void closeSession(String channelId, ChannelHandlerContext session) {
                        if (ServerManager.this.clientHandler != null) {
                            ServerManager.this.clientHandler.closeSession(channelId, session);
                        }
                    }

                });

                nettyTcpServer2.start(4);

                nettyHttpServer1 = NettyPool.getInstance().addBindHttpServer("0.0.0.0", httpPort1);

                nettyHttpServer1.start(1);

                nettyHttpServer1.addHttpBind(new IHttpHandler() {

                    @Override
                    public void run(String url, NioHttpRequest request) {
                        ArrayList<IHttpAPIScript> evts = ScriptManager.getInstance().getBaseScriptEntry().getEvts(IHttpAPIScript.class);
                        for (int i = 0; i < evts.size(); i++) {
                            IHttpAPIScript get = evts.get(i);
                            /*判断监听*/
                            if (get.checkStringScriptId1(url)) {
                                /*处理监听*/
                                get.run(url, request);
                                break;
                            }
                        }

                    }

                }, 1, "*");

                ScriptManager.getInstance().startScriptTimer();

                GlobalUtil.SERVERSTARTEND = true;

                NettyCoder.initMessageDb();

                /*服务器启动完成再次注册服务器信息*/
                this.getiServerManagerScript().sendServerInfoToHall();

                log.error("网关服务器 Id：" + GlobalUtil.getServerId() + " 启动完成");

            }
        }
    }

}
