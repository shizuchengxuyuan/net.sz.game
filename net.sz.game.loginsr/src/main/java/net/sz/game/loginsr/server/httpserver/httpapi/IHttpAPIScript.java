package net.sz.game.loginsr.server.httpserver.httpapi;

import net.sz.framework.nio.http.NioHttpRequest;
import net.sz.framework.scripts.IBaseScript;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/shizuchengxuyuan/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public interface IHttpAPIScript extends IBaseScript {

    /**
     *
     * @param url
     * @param request
     */
    void run(String url, NioHttpRequest request);
}
