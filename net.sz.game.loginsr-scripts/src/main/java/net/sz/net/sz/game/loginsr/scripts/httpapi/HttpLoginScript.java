package net.sz.net.sz.game.loginsr.scripts.httpapi;

import java.util.ArrayList;
import net.sz.framework.nio.http.NioHttpRequest;
import net.sz.framework.scripts.manager.ScriptManager;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.utils.TimeUtil;
import net.sz.game.loginsr.login.LoginManager;
import net.sz.game.loginsr.login.iscript.ILoginPlatformScript;
import net.sz.game.loginsr.server.httpserver.httpapi.IHttpAPIScript;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/shizuchengxuyuan/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class HttpLoginScript implements IHttpAPIScript {

    private static final SzLogger log = SzLogger.getLogger();

    @Override
    public boolean checkStringScriptId1(String scriptId) {
        return "login".equalsIgnoreCase(scriptId);
    }

    @Override
    public void run(String url, NioHttpRequest request) {
        if (checkStringScriptId1(url)) {
            String platform = request.getParams().get("platform");
            String channel = request.getParams().get("channel");

            ArrayList<ILoginPlatformScript> evts = ScriptManager.getInstance().getBaseScriptEntry().getEvts(ILoginPlatformScript.class);
            for (int i = 0; i < evts.size(); i++) {
                ILoginPlatformScript get = evts.get(i);
                if (get.checkStringScriptId1(platform)) {
                    get.login(platform, channel, request);
                    request.respons();
                    break;
                }
            }

            if (!request.isResponsOver()) {
                request.addContent(LoginManager.getInstance().getiLoginScript().getErrorCode(404, "服务器内部错误"));
                request.respons();
            }

            long end = TimeUtil.currentTimeMillis();
            if (log.isInfoEnabled()) {
                log.info("处理一个登陆耗时：" + (end - request.getCreateTime()));
            }
        }
    }
}
