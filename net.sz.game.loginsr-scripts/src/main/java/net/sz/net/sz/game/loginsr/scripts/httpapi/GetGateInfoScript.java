package net.sz.net.sz.game.loginsr.scripts.httpapi;

import net.sz.framework.nio.http.NioHttpRequest;
import net.sz.framework.szlog.SzLogger;
import net.sz.game.loginsr.server.ServerManager;
import net.sz.game.loginsr.server.httpserver.httpapi.IHttpAPIScript;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/shizuchengxuyuan/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class GetGateInfoScript implements IHttpAPIScript {

    private static final SzLogger log = SzLogger.getLogger();

    @Override
    public boolean checkStringScriptId1(String url) {
        return "getgate".equalsIgnoreCase(url);
    }

    //http://127.0.0.1:5501/getgate/
    @Override
    public void run(String url, NioHttpRequest request) {

        Long uid = request.getParams().get("uid", Long.class);
        String uname = request.getParams().get("uname");
        Long utime = request.getParams().get("utime", Long.class);
        String token = request.getParams().get("token");

        String mD5 = token;
//        mD5 = LoginManager.getInstance().getiLoginScript().getMD5(uid, uname, utime);

//        if (mD5.equalsIgnoreCase(token)) {
        request.addContent("{" + ServerManager.getInstance().getiServerManagerScript().getGateInfo() + "}");
//        } else {
//            request.addContent(LoginManager.getInstance().getiLoginScript().getErrorCode(500, "验证失败"));
//        }

    }

}
