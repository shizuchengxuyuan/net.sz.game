package net.sz.game.hallsr.server;

import io.netty.channel.ChannelHandlerContext;
import java.util.concurrent.ConcurrentHashMap;
import net.sz.game.hallsr.server.httpserver.HttpServerManager;
import net.sz.game.hallsr.server.tcpserver.TcpServerManager;
import net.sz.framework.nio.tcp.NettyCoder;
import net.sz.framework.struct.thread.BaseThreadRunnable;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.szthread.ThreadPool;
import net.sz.framework.util.IntegerSSId;
import net.sz.framework.util.concurrent.ConcurrentHashSet;
import net.sz.framework.utils.GlobalUtil;
import net.sz.game.pmodel.ServerTypeEnum;
import net.sz.game.pmodel.struct.ServerInfo;

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

    private BaseThreadRunnable clientMsgThread = ThreadPool.addThread("client-Msg-Thread");

    private BaseThreadRunnable serverMsgThread = ThreadPool.addThread("server-Msg-Thread");

    private final IntegerSSId serverids = new IntegerSSId();
    /**/
    private final ServerTypeEnum serverTypeEnum = ServerTypeEnum.HallServer;
    /*产生room服务器的id*/
    private final ConcurrentHashSet<Integer> roomIds = new ConcurrentHashSet<>();
    /*产生gate服务器的id*/
    private final ConcurrentHashSet<Integer> gateIds = new ConcurrentHashSet<>();

    private final ConcurrentHashMap<Integer, ChannelHandlerContext> gateServerSessionMap = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<Integer, ChannelHandlerContext> roomServerSessionMap = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<Integer, ServerInfo> gateServerInfoMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, ServerInfo> roomServerInfoMap = new ConcurrentHashMap<>();

    private ServerManager() {
    }

    public ServerTypeEnum getServerType() {
        return serverTypeEnum;
    }

    public BaseThreadRunnable getClientMsgThread() {
        return clientMsgThread;
    }

    public BaseThreadRunnable getServerMsgThread() {
        return serverMsgThread;
    }

    /**
     * 网关服务器链接
     *
     * @return
     */
    public ConcurrentHashMap<Integer, ChannelHandlerContext> getGateServerSessionMap() {
        return gateServerSessionMap;
    }

    /**
     * 游戏房间服务器请求过来的链接
     *
     * @return
     */
    public ConcurrentHashMap<Integer, ChannelHandlerContext> getRoomServerSessionMap() {
        return roomServerSessionMap;
    }

    /**
     *
     * @return
     */
    public ConcurrentHashMap<Integer, ServerInfo> getGateServerInfoMap() {
        return gateServerInfoMap;
    }

    public ConcurrentHashMap<Integer, ServerInfo> getRoomServerInfoMap() {
        return roomServerInfoMap;
    }

    public ConcurrentHashSet<Integer> getRoomIds() {
        return roomIds;
    }

    public ConcurrentHashSet<Integer> getGateIds() {
        return gateIds;
    }

    public IntegerSSId getServerids() {
        return serverids;
    }

    /**
     * 开启服务器监听
     *
     * @param tcpPort
     * @param httpPort
     */
    public void initlisten(int tcpPort, int httpPort) {
        /**/
        TcpServerManager.getInstance().startServer(tcpPort);
        /**/
        HttpServerManager.getInstance().startServer(httpPort);
        NettyCoder.initMessageDb();

        log.error("大厅服务器 Id：" + GlobalUtil.getServerId() + " 启动完成");

    }

    /**
     * 获取下一个房间id
     *
     * @return
     */
    public synchronized int getNextRoomId() {
        for (int i = 1; i < 500; i++) {
            if (!roomIds.contains(i)) {
                roomIds.add(i);
                if (!getRoomServerInfoMap().containsKey(i)) {
                    return i;
                }
            }
        }
        throw new UnsupportedOperationException("生产id失败");
    }

    /**
     * 获取下一个网关服务器id
     *
     * @return
     */
    public synchronized int getNextGateId() {
        for (int i = 1; i < 500; i++) {
            if (!gateIds.contains(i)) {
                gateIds.add(i);
                if (!getGateServerInfoMap().containsKey(i)) {
                    return i;
                }
            }
        }
        throw new UnsupportedOperationException("生产id失败");
    }

    public static void main(String[] args) {
        ServerManager.getInstance().getGateServerInfoMap().put(1, new ServerInfo());

        log.error(ServerManager.getInstance().getGateServerInfoMap().size());
    }
}
