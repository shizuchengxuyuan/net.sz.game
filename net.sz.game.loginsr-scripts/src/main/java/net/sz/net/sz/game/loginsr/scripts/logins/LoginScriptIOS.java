package net.sz.net.sz.game.loginsr.scripts.logins;

import net.sz.framework.nio.http.NioHttpRequest;
import net.sz.framework.szlog.SzLogger;
import net.sz.game.loginsr.login.iscript.ILoginPlatformScript;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/shizuchengxuyuan/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class LoginScriptIOS implements ILoginPlatformScript {

    private static final SzLogger log = SzLogger.getLogger();

    @Override
    public boolean checkStringScriptId1(String scriptId) {
        return "ios".equalsIgnoreCase(scriptId);
    }

    @Override
    public boolean login(String platform, String channelId, NioHttpRequest request) {
        if (checkStringScriptId1(platform)) {

        }
        return false;
    }

}
