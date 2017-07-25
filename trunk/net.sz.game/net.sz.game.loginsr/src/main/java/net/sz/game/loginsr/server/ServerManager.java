package net.sz.game.loginsr.server;

import java.util.concurrent.ConcurrentHashMap;
import net.sz.game.loginsr.server.iscript.IServerManagerScript;
import net.sz.framework.szlog.SzLogger;
import net.sz.game.pmodel.struct.ServerInfo;

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
    private int serverCode = 1;
    private IServerManagerScript iServerManagerScript;

    private final ConcurrentHashMap<Integer, ServerInfo> gateInfoMap = new ConcurrentHashMap<>();

    private ServerManager() {
    }

    public IServerManagerScript getiServerManagerScript() {
        return iServerManagerScript;
    }

    public void setiServerManagerScript(IServerManagerScript iServerManagerScript) {
        this.iServerManagerScript = iServerManagerScript;
    }

    public ConcurrentHashMap<Integer, ServerInfo> getGateInfoMap() {
        return gateInfoMap;
    }

    public int getServerCode() {
        return serverCode;
    }

    public void setServerCode(int serverCode) {
        this.serverCode = serverCode;
    }

}
