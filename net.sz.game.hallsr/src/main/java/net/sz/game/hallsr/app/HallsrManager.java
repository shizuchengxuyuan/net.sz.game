package net.sz.game.hallsr.app;

import java.util.ArrayList;
import net.sz.game.hallsr.server.ServerManager;
import net.sz.framework.scripts.manager.ScriptManager;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.utils.GlobalUtil;

/**
 * 大厅管理器 监听端口6001开始
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/shizuchengxuyuan/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class HallsrManager {

    private static final SzLogger log = SzLogger.getLogger();
    private static final HallsrManager IN_ME = new HallsrManager();

    public static HallsrManager getInstance() {
        return IN_ME;
    }

    private HallsrManager() {
    }

    /**
     * 主函数
     *
     * @param args
     */
    public static void main(String[] args) {
        if (log.isDebugEnabled()) {
            log.debug("游戏大厅服务器开始启动");
        }

        GlobalUtil.setGameId("100");
        GlobalUtil.setPlatformId("100");
        GlobalUtil.setServerId(101);
        GlobalUtil.setServerName("hallsr");

        ArrayList<String> loadJavaHandlers = ScriptManager.getInstance().loadJava();
        if (loadJavaHandlers != null && !loadJavaHandlers.isEmpty()) {
            log.error("服务器启动失败！！！！！");
            System.exit(0);
        }

        ScriptManager.getInstance().startScriptTimer();

        ServerManager.getInstance().initlisten(6001, 6501);

        GlobalUtil.SERVERSTARTEND = true;

        log.error("游戏大厅服务器启动完成");
    }

}
