package net.sz.net.sz.game.roomsr.scripts.game;

import net.sz.chess.model.struct.game.Room;
import net.sz.framework.szlog.SzLogger;
import net.sz.game.roomsr.game.iscript.IGameStartScript;

/**
 * 四川麻将
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/shizuchengxuyuan/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class Game_10001_Script implements IGameStartScript {

    private static final SzLogger log = SzLogger.getLogger();

    @Override
    public boolean checkScriptId1(int scriptId) {
        return 10001 == scriptId;
    }

    @Override
    public void startGame(Room room) {

    }

}
