package net.sz.game.loginsr.gm;

import net.sz.game.loginsr.gm.iscript.IGmScript;
import net.sz.framework.szlog.SzLogger;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/shizuchengxuyuan/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class GmManager {

    private static final SzLogger log = SzLogger.getLogger();

    private static final GmManager IN_ME = new GmManager();

    public static GmManager getInstance() {
        return IN_ME;
    }

    private IGmScript iGmScript;

    private GmManager() {
    }

    public IGmScript getiGmScript() {
        return iGmScript;
    }

    public void setiGmScript(IGmScript iGmScript) {
        this.iGmScript = iGmScript;
    }

}
