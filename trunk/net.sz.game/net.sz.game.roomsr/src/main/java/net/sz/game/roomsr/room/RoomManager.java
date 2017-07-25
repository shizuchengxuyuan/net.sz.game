package net.sz.game.roomsr.room;

import net.sz.framework.szlog.SzLogger;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class RoomManager {

    private static final SzLogger log = SzLogger.getLogger();

    private static final RoomManager IN_ME = new RoomManager();

    public static RoomManager getInstance() {
        return IN_ME;
    }

    private RoomManager() {
    }

}
