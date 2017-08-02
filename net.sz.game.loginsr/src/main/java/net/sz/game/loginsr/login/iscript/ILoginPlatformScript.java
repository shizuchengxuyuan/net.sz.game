package net.sz.game.loginsr.login.iscript;

import net.sz.framework.nio.http.NioHttpRequest;
import net.sz.framework.scripts.IBaseScript;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/shizuchengxuyuan/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public interface ILoginPlatformScript extends IBaseScript {

    /**
     * 处理登录 平台登录
     *
     * @param platform 平台ID
     * @param channelId 渠道ID
     * @param request 请求
     * @return
     */
    boolean login(String platform, String channelId, NioHttpRequest request);
}
