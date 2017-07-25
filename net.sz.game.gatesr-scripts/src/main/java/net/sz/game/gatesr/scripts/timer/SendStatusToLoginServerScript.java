package net.sz.game.gatesr.scripts.timer;

import net.sz.framework.nio.NettyPool;
import net.sz.framework.scripts.timer.iscript.ISecondsEventTimerScript;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.utils.GlobalUtil;
import net.sz.framework.utils.HttpAsyncUtil;
import net.sz.framework.utils.HttpUtil;
import net.sz.game.gatesr.server.ServerManager;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class SendStatusToLoginServerScript implements ISecondsEventTimerScript {

    private static final SzLogger log = SzLogger.getLogger();

    @Override
    public void run(int sec) {
        if (sec % 2 == 0) {
            String param = "serverid=" + GlobalUtil.getServerId() + "&servercode=" + ServerManager.getInstance().getServerCode() + "&tcpport=" + ServerManager.getInstance().getNettyTcpServer2().getPort() + "&tcpcount=" + NettyPool.getInstance().getSessions().size();

            HttpAsyncUtil.urlPostAsync(ServerManager.getInstance().getLoginConnectString() + "gateapi", param, new HttpAsyncUtil.AsyncCallBack() {
                
                @Override
                public Object completed(String respBody) {
                    if (!"ok".equalsIgnoreCase(respBody)) {
                        log.error("SendStatusToLoginServerScript：" + respBody);
                    }
                    return null;
                }

            });

        }
    }

}
