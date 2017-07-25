package net.sz.game.roomsr.server;

import io.netty.channel.ChannelHandlerContext;
import java.util.concurrent.ConcurrentHashMap;
import net.sz.game.roomsr.server.httpserver.HttpServerManager;
import net.sz.game.roomsr.server.iscript.IServerManagerScript;
import net.sz.game.roomsr.server.tcpserver.TcpServerManager;
import net.sz.framework.nio.tcp.INettyHandler;
import net.sz.framework.nio.tcp.NettyCoder;
import net.sz.framework.nio.tcp.ServerSessionClient;
import net.sz.framework.scripts.manager.ScriptManager;
import net.sz.framework.struct.thread.BaseThreadRunnable;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.szthread.ThreadPool;
import net.sz.framework.utils.GlobalUtil;
import net.sz.game.pmodel.ServerTypeEnum;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class ServerManager {

    private static final SzLogger log = SzLogger.getLogger();

    private static final ServerManager IN_ME = new ServerManager();

    public static ServerManager getInstance() {
        return IN_ME;
    }

    private BaseThreadRunnable clientMsgThread = ThreadPool.addThread("client-Msg-Thread");

    private BaseThreadRunnable serverMsgThread = ThreadPool.addThread("server-Msg-Thread");

    private ServerTypeEnum serverType = ServerTypeEnum.RoomServer;
    private int serverCode = 1;
    /*网管服务器链接*/
    private ServerSessionClient hallServerSessionClient;
    /*链接到网关服务器请求过来的链接*/
    private ConcurrentHashMap<Integer, ServerSessionClient> gateServerSessionClientMap = new ConcurrentHashMap<>();

    private IServerManagerScript iServerManagerScript;

    private ServerManager() {
    }

    public BaseThreadRunnable getClientMsgThread() {
        return clientMsgThread;
    }

    public BaseThreadRunnable getServerMsgThread() {
        return serverMsgThread;
    }

    public IServerManagerScript getiServerManagerScript() {
        return iServerManagerScript;
    }

    public void setiServerManagerScript(IServerManagerScript iServerManagerScript) {
        this.iServerManagerScript = iServerManagerScript;
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
     * 链接到网关服务器请求过来的链接
     *
     * @return
     */
    public ConcurrentHashMap<Integer, ServerSessionClient> getGateServerSessionClientMap() {
        return gateServerSessionClientMap;
    }

    public ServerTypeEnum getServerType() {
        return serverType;
    }

    public int getServerCode() {
        return serverCode;
    }

    public void setServerCode(int serverCode) {
        this.serverCode = serverCode;
    }

    /**
     * 链接到大厅服务器
     *
     * @param hostName
     * @param port
     */
    public void connectHallServer(String hostName, int port) {

        hallServerSessionClient = new ServerSessionClient(hostName, port, 1, 1, NettyCoder.getDefaultCoder(), new INettyHandler() {
            /**
             * 链接接通
             *
             * @param channelId
             * @param session
             */
            @Override
            public void channelActive(String channelId, ChannelHandlerContext session) {
                ServerManager.getInstance().getiServerManagerScript().sendServerInfoToHall();
            }

            @Override
            public void closeSession(String channelId, ChannelHandlerContext session) {

            }

        });
    }

    /**
     * 开启服务器监听
     *
     * @param tcpPort
     * @param httpPort
     */
    public void initlisten(int tcpPort, int httpPort) {
        synchronized (GlobalUtil.SERVERSTARTEND) {
            if (!GlobalUtil.SERVERSTARTEND) {

                /**/
                TcpServerManager.getInstance().startServer(tcpPort);
                /**/
                HttpServerManager.getInstance().startServer(httpPort);

                ScriptManager.getInstance().startScriptTimer();

                GlobalUtil.SERVERSTARTEND = true;

                NettyCoder.initMessageDb();

                /*服务器启动完成再次注册服务器信息*/
                this.getiServerManagerScript().sendServerInfoToHall();

                log.error("房间服务器 Id：" + GlobalUtil.getServerId() + " 启动完成");

            }
        }
    }
}
