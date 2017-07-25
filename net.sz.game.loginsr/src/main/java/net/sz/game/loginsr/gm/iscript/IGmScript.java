package net.sz.game.loginsr.gm.iscript;

import net.sz.framework.scripts.IBaseScript;
import net.sz.framework.util.ObjectAttribute;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public interface IGmScript extends IBaseScript {

    /**
     *
     * @param cmd
     * @param ip
     * @param params
     * @return
     */
    String exec(String cmd, String ip, ObjectAttribute<String> params);
}
