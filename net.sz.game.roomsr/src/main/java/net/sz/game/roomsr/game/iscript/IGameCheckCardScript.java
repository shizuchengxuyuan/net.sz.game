package net.sz.game.roomsr.game.iscript;

import net.sz.chess.model.struct.game.Room;
import net.sz.framework.scripts.IBaseScript;

/**
 * 检查牌
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public interface IGameCheckCardScript extends IBaseScript {

    void checkCard(Room room);
}
