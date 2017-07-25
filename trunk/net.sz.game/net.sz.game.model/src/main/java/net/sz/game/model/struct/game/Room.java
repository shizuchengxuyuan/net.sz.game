package net.sz.chess.model.struct.game;

import java.io.Serializable;
import net.sz.framework.struct.ObjectBase;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class Room extends ObjectBase implements Serializable, Cloneable {

    private static final long serialVersionUID = 7189521940199915371L;

    private long rid;
    private GameType gameType;

    public Room(long rid, GameType gameType) {
        this.rid = rid;
        this.gameType = gameType;
    }

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

}
