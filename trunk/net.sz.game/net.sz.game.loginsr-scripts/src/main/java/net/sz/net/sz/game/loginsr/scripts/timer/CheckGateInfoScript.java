package net.sz.net.sz.game.loginsr.scripts.timer;

import net.sz.framework.scripts.timer.iscript.ISecondsEventTimerScript;
import net.sz.framework.szlog.SzLogger;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class CheckGateInfoScript implements ISecondsEventTimerScript {

    private static final SzLogger log = SzLogger.getLogger();

    @Override
    public void run(int sec) {
        if (sec % 5 == 0) {
            /*每五秒执行一次检测*/
            
        }
    }

}
