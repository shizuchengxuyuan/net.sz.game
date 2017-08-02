package net.sz.game.roomsr.app;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import net.sz.game.roomsr.server.ServerManager;
import net.sz.framework.scripts.manager.ScriptManager;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.utils.GlobalUtil;

/**
 * 房间服务器管理器
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/shizuchengxuyuan/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class RoomsrManager {

    private static final SzLogger log = SzLogger.getLogger();
    private static final RoomsrManager IN_ME = new RoomsrManager();

    public static RoomsrManager getInstance() {
        return IN_ME;
    }

    private RoomsrManager() {
    }

    public static void main(String[] args) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("游戏房间服务器开始启动");
        }

        GlobalUtil.setGameId("100");
        GlobalUtil.setPlatformId("100");
        GlobalUtil.setServerName("roomsr");

        String basepath = System.getProperty("user.dir")
                + java.io.File.separator + ".." + java.io.File.separator + "configs" + java.io.File.separator + "gatesr"
                + java.io.File.separator;
        {
            String appconfig = new java.io.File(basepath + "appconfig.ini").getPath();
            InputStream in = new java.io.FileInputStream(appconfig);
            Properties prop = new Properties();
            prop.load(in);

            String sercoder = prop.getProperty("GLOBAL.SERVER.CODER");
            ServerManager.getInstance().setServerCode(Integer.valueOf(sercoder));

            in.close();
        }

        ArrayList<String> loadJavaHandlers = ScriptManager.getInstance().loadJava();
        if (loadJavaHandlers != null && !loadJavaHandlers.isEmpty()) {
            log.error("服务器启动失败！！！！！");
            System.exit(0);
        }

        {
            String appconfig = new java.io.File(basepath + "appconfig.ini").getPath();
            InputStream in = new java.io.FileInputStream(appconfig);
            Properties prop = new Properties();
            prop.load(in);

            String halltcpip = prop.getProperty("CONNECT.HALL.TCP.IP");
            String halltcpport = prop.getProperty("CONNECT.HALL.TCP.PORT");

            ServerManager.getInstance().connectHallServer(halltcpip, Integer.valueOf(halltcpport));

            in.close();
        }

        log.error("游戏房间服务器启动完成");
    }

}
