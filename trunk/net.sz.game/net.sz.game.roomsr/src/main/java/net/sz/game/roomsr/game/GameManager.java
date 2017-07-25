package net.sz.game.roomsr.game;

import net.sz.framework.szlog.SzLogger;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class GameManager {

    private static final SzLogger log = SzLogger.getLogger();

    private static final GameManager IN_ME = new GameManager();

    public static GameManager getInstance() {
        return IN_ME;
    }

    private GameManager() {
    }

}
