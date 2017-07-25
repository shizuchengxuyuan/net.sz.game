package net.sz.game.gatesr.server.timer;

import java.util.ArrayList;
import net.sz.game.gatesr.server.iscript.IStopServerScript;
import net.sz.framework.scripts.manager.ScriptManager;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.szthread.TimerTaskModel;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class StopServerTimer extends TimerTaskModel {

    private static final SzLogger log = SzLogger.getLogger();
    private static final long serialVersionUID = -7167878823285088402L;

    public StopServerTimer(int intervalTime) {
        super(intervalTime);
    }

    @Override
    public void run() {
        ArrayList<IStopServerScript> evts = ScriptManager.getInstance().getBaseScriptEntry().getEvts(IStopServerScript.class);
        if (!evts.isEmpty()) {
            for (IStopServerScript evt : evts) {
                evt.checkStopServer();
            }
        }
    }

}
