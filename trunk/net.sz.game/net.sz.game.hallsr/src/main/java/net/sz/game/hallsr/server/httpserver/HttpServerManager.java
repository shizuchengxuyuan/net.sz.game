package net.sz.game.hallsr.server.httpserver;

import java.util.ArrayList;
import net.sz.game.hallsr.server.httpserver.httpapi.IHttpAPIScript;
import net.sz.framework.nio.NettyPool;
import net.sz.framework.nio.http.NettyHttpServer;
import net.sz.framework.nio.http.NioHttpRequest;
import net.sz.framework.nio.http.handler.IHttpHandler;
import net.sz.framework.scripts.manager.ScriptManager;
import net.sz.framework.szlog.SzLogger;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class HttpServerManager {

    private static final SzLogger log = SzLogger.getLogger();

    private static final HttpServerManager IN_ME = new HttpServerManager();

    public static HttpServerManager getInstance() {
        return IN_ME;
    }

    NettyHttpServer BindHttpServer;

    private HttpServerManager() {
    }

    /**
     * 获取http服务
     *
     * @return
     */
    public NettyHttpServer getBindHttpServer() {
        return BindHttpServer;
    }

    public void startServer(int port) {

        BindHttpServer = NettyPool.getInstance().addBindHttpServer("0.0.0.0", port);
        BindHttpServer.start(2);

        BindHttpServer.addHttpBind(new IHttpHandler() {

            @Override
            public void run(String url, NioHttpRequest request) {
                ArrayList<IHttpAPIScript> evts = ScriptManager.getInstance().getBaseScriptEntry().getEvts(IHttpAPIScript.class);
                for (int i = 0; i < evts.size(); i++) {
                    IHttpAPIScript get = evts.get(i);
                    /*判断监听*/
                    if (get.checkStringScriptId1(url)) {
                        /*处理监听*/
                        get.run(url, request);
                        break;
                    }
                }

            }

        }, 10, "*");

    }

}
