package net.sz.game.loginsr.app;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import net.sz.game.loginsr.data.DataManager;
import net.sz.game.loginsr.server.httpserver.HttpServerManager;
import net.sz.framework.scripts.manager.ScriptManager;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.utils.GlobalUtil;
import net.sz.game.loginsr.server.ServerManager;

/**
 * 监听端口5001开始
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class LoginsrManager {

    private static final SzLogger log = SzLogger.getLogger();
    private static final LoginsrManager IN_ME = new LoginsrManager();

    public static LoginsrManager getInstance() {
        return IN_ME;
    }

    private LoginsrManager() {

    }

    public static void main(String[] args) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("游戏登录服务器开始启动");
        }

        GlobalUtil.setGameId("100");
        GlobalUtil.setPlatformId("100");
        GlobalUtil.setServerId(101);
        GlobalUtil.setServerName("loginsr");

        String basepath = System.getProperty("user.dir")
                + java.io.File.separator + ".." + java.io.File.separator + "configs" + java.io.File.separator + "loginsr"
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

        /*开始加载消息处理器*/
        ArrayList<String> loadJavaHandlers = ScriptManager.getInstance().loadJavaHandlers();
        if (loadJavaHandlers != null && !loadJavaHandlers.isEmpty()) {
            log.error("游戏登录服务器启动失败！！！！！");
            System.exit(0);
        }

        DataManager.getInstance().initDbConfig();

        /*开始加载消息处理器*/
        loadJavaHandlers = ScriptManager.getInstance().loadJavaScripts();

        if (loadJavaHandlers != null && !loadJavaHandlers.isEmpty()) {
            log.error("游戏登录服务器启动失败！！！！！");
            System.exit(0);
        }

        ScriptManager.getInstance().startScriptTimer();
        {
            String appconfig = new java.io.File(basepath + "appconfig.ini").getPath();
            InputStream in = new java.io.FileInputStream(appconfig);
            Properties prop = new Properties();
            prop.load(in);

            String httpport = prop.getProperty("LISTN.CONNECT.LOGIN.HTTP.PORT");
            //TcpServerManager.getInstance().startServer(5001);
            HttpServerManager.getInstance().startServer(Integer.valueOf(httpport));

            in.close();
        }
        GlobalUtil.SERVERSTARTEND = true;

        log.error("游戏登录服务器启动完成");

    }
}
