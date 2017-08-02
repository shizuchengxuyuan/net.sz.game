package net.sz.game.loginsr.login.iscript;

import net.sz.framework.nio.http.NioHttpRequest;
import net.sz.framework.scripts.IInitBaseScript;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/shizuchengxuyuan/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public interface ILoginScript extends IInitBaseScript {

    /**
     * 返回错误码
     *
     * @param code
     * @param msg
     * @return
     */
    String getErrorCode(int code, String msg);

    /**
     * 获取统一的登录md5验证码
     *
     * @param uid
     * @param username
     * @param md5time
     * @return
     */
    String getMD5(long uid, String username, long md5time);

    /**
     * 最终登录
     *
     * @param username
     * @param userpwd
     * @param platform
     * @param channelId
     * @param request
     */
    void _login(String username, String userpwd, String platform, String channelId, NioHttpRequest request);
}
