package net.sz.game.roomsr.game.iscript;

import net.sz.chess.model.struct.game.Room;
import net.sz.framework.scripts.IBaseScript;

/**
 * 开始游戏
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/shizuchengxuyuan/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public interface IGameStartScript extends IBaseScript {

    /**
     * 开始游戏
     *
     * @param room
     */
    void startGame(Room room);
}
