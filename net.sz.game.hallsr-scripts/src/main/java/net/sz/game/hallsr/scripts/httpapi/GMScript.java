package net.sz.game.hallsr.scripts.httpapi;

import java.util.ArrayList;
import net.sz.framework.nio.http.NioHttpRequest;
import net.sz.framework.scripts.IInitBaseScript;
import net.sz.framework.scripts.manager.ScriptManager;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.util.ObjectAttribute;
import net.sz.framework.utils.JsonUtil;
import net.sz.framework.utils.TimeUtil;
import net.sz.game.hallsr.gm.GmManager;
import net.sz.game.hallsr.gm.iscript.IGmScript;
import net.sz.game.hallsr.server.httpserver.httpapi.IHttpAPIScript;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/shizuchengxuyuan/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class GMScript implements IHttpAPIScript, IGmScript, IInitBaseScript {

    private static final SzLogger log = SzLogger.getLogger();
    private static final String GMTOKEN = "1a45ab3830b340de80da75e124d246f2";

    @Override
    public void _init() {
        GmManager.getInstance().setiGmScript(this);
    }

    @Override
    public boolean checkStringScriptId1(String scriptId) {
        return "gmcmd".equalsIgnoreCase(scriptId);
    }

    @Override
    public void run(String url, NioHttpRequest requestMessage) {
        if (checkStringScriptId1(url)) {
            String cmdString = "&" + requestMessage.getParams().get("cmd");
            String exec = exec(cmdString, requestMessage.getIp(), requestMessage.getParams());
            requestMessage.addContent(exec);
            requestMessage.respons();
        }
    }

    //http://127.0.0.1:7501/gmcmd?token=1a45ab3830b340de80da75e124d246f2&cmd=reloadtable
    //http://127.0.0.1:7501/gmcmd?token=1a45ab3830b340de80da75e124d246f2&cmd=reloadscript
    @Override
    public String exec(String cmd, String ip, ObjectAttribute<String> params) {
        int gmlevel = 0;
        if (ip.startsWith("127.0.0.1")
                || ip.startsWith("192.168.")) {
            gmlevel = 10;
        }
        long currentTimeMillis = TimeUtil.currentTimeMillis();
        String execString = "cmd:" + cmd + "，ip:" + ip + "，result:";
        if (gmlevel <= 0) {
            String gmtoken = params.get("token");
            if (GMTOKEN.equalsIgnoreCase(gmtoken)) {
                gmlevel = 10;
            }
        }
        if (gmlevel <= 0) {
            return execString + " 权限不足";
        }
        switch (cmd.toLowerCase()) {//转化成所有小写
            case "&reloadscript": {
                ArrayList<String> loadJava;
                loadJava = ScriptManager.getInstance().loadJava();
                String toJSONString = JsonUtil.toJSONString(loadJava);
                execString += toJSONString;
            }
            break;
            case "&reloadtable": {
//                DataManager.getInstance().loadDb();
                execString += " ok";
            }
            break;
            case "&stopserver": {

//                ServerManager.getInstance().setStopServerTime(TimeUtil.currentTimeMillis());

            }
            break;
            default:
                execString += "未知GM命令 ";
                break;
        }
        execString += "，执行耗时：" + (TimeUtil.currentTimeMillis() - currentTimeMillis);
        return execString;
    }

}
