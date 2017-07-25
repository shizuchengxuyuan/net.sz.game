package net.sz.chess.model.struct.data;

import java.io.Serializable;
import java.util.HashMap;
import net.sz.framework.struct.ObjectBase;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class Player extends ObjectBase implements Serializable, Cloneable {

    private static final long serialVersionUID = -3157500630718615158L;

    /*记录客户端同学的key*/
    private transient String sessionKey;

    private HashMap<String, String> hotkeyMap = new HashMap<>();

}
