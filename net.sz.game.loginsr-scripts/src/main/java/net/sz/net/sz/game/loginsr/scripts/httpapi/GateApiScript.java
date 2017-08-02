package net.sz.net.sz.game.loginsr.scripts.httpapi;

import net.sz.framework.nio.http.NioHttpRequest;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.utils.TimeUtil;
import net.sz.game.loginsr.server.ServerManager;
import net.sz.game.loginsr.server.httpserver.httpapi.IHttpAPIScript;
import net.sz.game.pmodel.struct.ServerInfo;
import net.sz.game.pmodel.struct.SocketListenInfo;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/shizuchengxuyuan/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class GateApiScript implements IHttpAPIScript {

    private static final SzLogger log = SzLogger.getLogger();

    @Override
    public boolean checkStringScriptId1(String url) {
        return "gateapi".equalsIgnoreCase(url);
    }

    @Override
    public void run(String url, NioHttpRequest request) {
        Integer serverid = request.getParams().get("serverid", int.class);
        Integer servercode = request.getParams().get("servercode", int.class);
        Integer tcpport = request.getParams().get("tcpport", int.class);
        Integer tcpcount = request.getParams().get("tcpcount", int.class);
        if (log.isDebugEnabled()) {
            log.debug("网管服务器：" + serverid + "承载量：" + tcpcount);
        }

        ServerInfo serverInfo = ServerManager.getInstance().getGateInfoMap().get(serverid);
        if (serverInfo == null) {
            serverInfo = new ServerInfo();
            ServerManager.getInstance().getGateInfoMap().put(serverid, serverInfo);
            serverInfo.getListenInfos().add(new SocketListenInfo());
        }

        SocketListenInfo sli = serverInfo.getListenInfos().get(0);

        sli.setTcpIp(request.getIp());
        sli.setTcpPort(tcpport);
        serverInfo.getListenInfos().add(sli);

        serverInfo.setServerId(serverid);
        serverInfo.setServerCode(servercode);
        serverInfo.setLastUpdatecount(tcpcount);
        serverInfo.setLastUpdatetimer(TimeUtil.currentTimeMillis());

        request.addContent("ok");
    }

}
