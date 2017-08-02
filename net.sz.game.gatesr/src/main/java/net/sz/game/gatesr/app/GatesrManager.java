package net.sz.game.gatesr.app;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import net.sz.framework.scripts.manager.ScriptManager;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.utils.GlobalUtil;
import net.sz.game.gatesr.server.ServerManager;

/**
 * 网关服务器 监听端口7001开始
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/shizuchengxuyuan/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class GatesrManager {

    private static final SzLogger log = SzLogger.getLogger();

    private static final GatesrManager IN_ME = new GatesrManager();

    public static GatesrManager getInstance() {
        return IN_ME;
    }

    private GatesrManager() {
    }

    /**
     * 主函数
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("游戏网管服务器开始启动");
        }

        GlobalUtil.setGameId("100");
        GlobalUtil.setPlatformId("100");
        GlobalUtil.setServerName("gatesr");

        String basepath = System.getProperty("user.dir")
                + java.io.File.separator + ".." + java.io.File.separator + "configs" + java.io.File.separator + "gatesr"
                + java.io.File.separator;
        {
            String appconfig = new java.io.File(basepath + "appconfig.ini").getPath();
            InputStream in = new java.io.FileInputStream(appconfig);
            Properties prop = new Properties();
            prop.load(in);

            String sercoder = prop.getProperty("GLOBAL.SERVER.CODER");
            String connectlogin = prop.getProperty("CONNECT.LOGIN.HTTP");

            ServerManager.getInstance().setLoginConnectString(connectlogin);

            ServerManager.getInstance().setServerCode(Integer.valueOf(sercoder));

            in.close();
        }

        /*开始加载消息处理器*/
        ArrayList<String> loadJavaHandlers = ScriptManager.getInstance().loadJava();
        if (loadJavaHandlers != null && !loadJavaHandlers.isEmpty()) {
            log.error("游戏网管服务器启动失败！！！！！");
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
    }
}
