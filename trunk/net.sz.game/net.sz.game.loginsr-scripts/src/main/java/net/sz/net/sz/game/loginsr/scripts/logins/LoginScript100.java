package net.sz.net.sz.game.loginsr.scripts.logins;

import net.sz.framework.nio.http.NioHttpRequest;
import net.sz.framework.szlog.SzLogger;
import net.sz.game.loginsr.login.LoginManager;
import net.sz.game.loginsr.login.iscript.ILoginPlatformScript;

/**
 * 100渠道登录
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class LoginScript100 implements ILoginPlatformScript {

    private static final SzLogger log = SzLogger.getLogger();

    @Override
    public boolean checkStringScriptId1(String scriptId) {
        return "100".equalsIgnoreCase(scriptId);
    }

    //http://127.0.0.1:5501/login?platform=100&channel=100&username=ROBOTsz111&password=1
    //http://192.168.0.235:5501/login?platform=100&channel=100&username=ROBOT111&password=1&version=1&mac64=jdjdjjd&os=ios&fr=0202125
    //http://192.168.0.219:5501/login?platform=100&channel=100&username=ROBOT111&password=1&version=1&mac64=jdjdjjd&os=ios&fr=0202125
    @Override
    public boolean login(String platform, String channelId, NioHttpRequest request) {
        if (checkStringScriptId1(platform)) {
            String username = request.getParams().get("username");
            String password = request.getParams().get("password");
            LoginManager.getInstance().getiLoginScript()._login(username, password, platform, channelId, request);
            return true;
        }
        return false;
    }
}
