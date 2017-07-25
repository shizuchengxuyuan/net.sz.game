package net.sz.game.hallsr;

import net.sz.framework.nio.ProtoBufHandlerUtil;
import net.sz.framework.szlog.SzLogger;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class CreateHandler {

    private static final SzLogger log = SzLogger.getLogger();

    public static void main(String[] args) {
        ProtoBufHandlerUtil.createScriptHandler("/../net.sz.chess.message/", "GH|GG|RH");
    }
}
