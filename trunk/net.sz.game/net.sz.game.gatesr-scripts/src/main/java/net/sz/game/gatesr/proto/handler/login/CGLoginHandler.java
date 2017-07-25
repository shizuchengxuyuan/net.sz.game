package net.sz.game.gatesr.proto.handler.login;

import net.sz.framework.nio.tcp.NettyCoder;
import net.sz.framework.nio.tcp.NettyTcpHandler;
import net.sz.framework.scripts.IInitBaseScript;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.utils.MD5Util;
import net.sz.game.gatesr.server.ServerManager;
import net.sz.game.message.proto.LoginMessage;


/**
 *
 *<br>
 * author 失足程序员<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public final class CGLoginHandler extends NettyTcpHandler implements IInitBaseScript {

    private static final SzLogger log = SzLogger.getLogger();
    private static final long serialVersionUID = 1L;
    private static final String LOGINPWDSIGN = "af0ca5ee6203e02ec076aa8b84385d08";

    public CGLoginHandler() {

    }

    @Override
    public void _init() {
        //把消息自动注册到消息中心
        NettyCoder.register(
                net.sz.game.message.proto.LoginMessage.CGLoginMessage.newBuilder(),/*消息体*/
                ServerManager.getInstance().getClientMsgThread().getTid(),/*线程id*/
                this, /*消息执行的handler*/
                null /*消息队列*/
        );
    }

    @Override
    public void run() {
        // TODO 处理LoginMessage.CGLogin消息
        LoginMessage.CGLoginMessage reqMessage = (LoginMessage.CGLoginMessage) getMessage();

        /*登录成功后发送消息到*/
        String token = reqMessage.getToken();
        long tokenTime = reqMessage.getTokenTime();
        long uid = reqMessage.getUid();
        String uname = reqMessage.getUname();
        String signString = getMD5(uid, uname, tokenTime);

        LoginMessage.GCLoginMessage.Builder gcloginBuilder = LoginMessage.GCLoginMessage.newBuilder();

        if (signString.equalsIgnoreCase(token)) {
            gcloginBuilder.setCode(0);

            log.error("用户：" + uname + "登录成功");
        } else {
            gcloginBuilder.setCode(1);
            gcloginBuilder.setMsg("登录验证失败");
            log.error("用户：" + uname + " 登录验证失败");
        }

        ServerManager.getInstance().getClientNettyCoder0().send(getSession(), gcloginBuilder);

    }

    public String getMD5(long uid, String username, long md5time) {
        return MD5Util.md5Encode('=', String.valueOf(uid), username, String.valueOf(md5time), LOGINPWDSIGN);
    }
}
