package net.sz.game.roomsr.game.iscript;

import net.sz.chess.model.struct.game.Room;
import net.sz.framework.scripts.IBaseScript;

/**
 * 获取手牌
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/shizuchengxuyuan/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public interface IGameGetCardScript extends IBaseScript {

    void getCard(Room room);
}
