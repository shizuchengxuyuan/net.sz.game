package net.sz.net.sz.game.loginsr.scripts.server;

import net.sz.framework.szlog.SzLogger;
import net.sz.game.loginsr.server.ServerManager;
import net.sz.game.loginsr.server.iscript.IServerManagerScript;
import net.sz.game.pmodel.struct.ServerInfo;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class ServerManagerScript implements IServerManagerScript {

    private static final SzLogger log = SzLogger.getLogger();

    @Override
    public void _init() {
        ServerManager.getInstance().setiServerManagerScript(this);
    }

    @Override
    public String getGateInfo() {

        ServerInfo[] toArray = ServerManager.getInstance().getGateInfoMap().values().toArray(new ServerInfo[0]);

        return "ip:192.168.0.235,port:7001";
    }

}
