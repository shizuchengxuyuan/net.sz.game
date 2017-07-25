package net.sz.game.loginsr.login;

import net.sz.game.loginsr.login.iscript.ILoginScript;
import net.sz.framework.szlog.SzLogger;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class LoginManager {

    private static final SzLogger log = SzLogger.getLogger();

    private static final LoginManager IN_ME = new LoginManager();

    public static LoginManager getInstance() {
        return IN_ME;
    }

    private ILoginScript iLoginScript = null;

    private LoginManager() {
        
    }

    public ILoginScript getiLoginScript() {
        return iLoginScript;
    }

    public void setiLoginScript(ILoginScript iLoginScript) {
        this.iLoginScript = iLoginScript;
    }

}
