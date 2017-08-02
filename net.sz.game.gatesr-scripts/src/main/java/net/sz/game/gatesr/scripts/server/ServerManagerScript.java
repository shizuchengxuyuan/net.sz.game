package net.sz.game.gatesr.scripts.server;

import net.sz.framework.szlog.SzLogger;
import net.sz.framework.utils.GlobalUtil;
import net.sz.game.gatesr.server.ServerManager;
import net.sz.game.gatesr.server.iscript.IServerManagerScript;
import net.sz.game.message.proto.RegisterServerMessage;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/shizuchengxuyuan/<br>
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
    public void sendServerInfoToHall() {
        RegisterServerMessage.GHRegisterServerMessage.Builder newBuilder = RegisterServerMessage.GHRegisterServerMessage.newBuilder();
        RegisterServerMessage.ServerInfo.Builder serverBuilder = RegisterServerMessage.ServerInfo.newBuilder();
        serverBuilder.setServerId(GlobalUtil.getServerId());
        serverBuilder.setServerType(ServerManager.getInstance().getServerType().getStype());
        serverBuilder.setServerCode(ServerManager.getInstance().getServerCode());
        serverBuilder.setMsgType(GlobalUtil.SERVERSTARTEND ? 2 : 1);
        serverBuilder.setServerEnd(GlobalUtil.SERVERSTARTEND);
        newBuilder.setServerInfo(serverBuilder);
        ServerManager.getInstance().getHallServerSessionClient().addMessage(newBuilder);
    }

}
