package net.sz.net.sz.game.loginsr.scripts.logins;

import net.sz.framework.nio.http.NioHttpRequest;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.utils.GlobalUtil;
import net.sz.framework.utils.JsonUtil;
import net.sz.framework.utils.MD5Util;
import net.sz.framework.utils.StringUtil;
import net.sz.framework.utils.TimeUtil;
import net.sz.game.loginsr.data.DataManager;
import net.sz.game.loginsr.login.LoginManager;
import net.sz.game.loginsr.login.iscript.ILoginScript;
import net.sz.game.loginsr.server.ServerManager;
import net.sz.game.pmodel.po.loginsr.UserInfo;
import net.sz.game.pmodel.struct.ServerInfo;
import net.sz.game.pmodel.struct.SocketListenInfo;

/**
 * 登录本地系统 操作数据库
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class LoginScript implements ILoginScript {

    private static final SzLogger log = SzLogger.getLogger();

    private static final String LOGINPWDSIGN = "af0ca5ee6203e02ec076aa8b84385d08";

    @Override
    public void _init() {
        //反向注册
        LoginManager.getInstance().setiLoginScript(this);
    }

    @Override
    public String getErrorCode(int code, String msg) {
        String ret = "{" + "\"code\":" + code + ", \"msg\":\"" + msg + "\"}";
        return ret;
    }

    @Override
    public String getMD5(long uid, String username, long md5time) {
        return MD5Util.md5Encode('=', String.valueOf(uid), username, String.valueOf(md5time), LOGINPWDSIGN);
    }

    @Override
    public void _login(String username, String userpwd, String platform, String channelId, NioHttpRequest request) {
        long currentTimeMillis = TimeUtil.currentTimeMillis();
        if (!"100".equalsIgnoreCase(platform)) {
            username = platform + "_" + username;
        }
        if (log.isDebugEnabled()) {
            log.debug("登录耗时 " + username + " 1 ：" + (TimeUtil.currentTimeMillis() - currentTimeMillis));
        }
        boolean flag = true;
        String usernameLowerCase = username.toLowerCase();

        if (!StringUtil.checkFilter(username, StringUtil.PATTERN_ABC_0) || !StringUtil.checkFilter(userpwd, StringUtil.PATTERN_ABC_PWD)) {
            if (log.isInfoEnabled()) {
                log.info("用户：" + username + " 账号或者密码非法字符!!!");
            }
            request.addContent(getErrorCode(10, "账号或者密码非法字符!"));
            flag = false;
        }

        if (!("100".equalsIgnoreCase(platform)
                || request.getIp().startsWith("192.168.")
                || request.getIp().startsWith("127.0.0.1"))) {
            if (usernameLowerCase.startsWith("robot")) {
                if (log.isInfoEnabled()) {
                    log.info("用户：" + username + " 并非特殊平台，不允许此账号!!!");
                }
                request.addContent(getErrorCode(10, "并非特殊平台，不允许此账号!"));
                flag = false;
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("登录耗时 " + username + " 2 ：" + (TimeUtil.currentTimeMillis() - currentTimeMillis));
        }
        if (flag) {
            try {
                /*优先获取缓存状态*/
                UserInfo userinfo = DataManager.getInstance().getDataCachePool().getValue(usernameLowerCase, UserInfo.class);
                if (userinfo == null) {
                    /*数据库操作之前，加锁，锁定账户小写对象池副本，就一定能针对单账户锁定*/
                    synchronized (usernameLowerCase.intern()) {
                        if (log.isInfoEnabled()) {
                            log.info("用户：" + username + " 不存在缓存用户!!!");
                        }
                        /*再次获取缓存状态，存在并发，那么获得锁权限以后有几率以及得到数据了*/
                        userinfo = DataManager.getInstance().getDataCachePool().getValue(usernameLowerCase, UserInfo.class);
                        if (userinfo != null) {
                            if (log.isInfoEnabled()) {
                                log.info("平台：" + platform + ", ip：" + request.getIp() + ", 用户：" + username + " 缓存用户!!!");
                            }
                        } else {
                            if (log.isDebugEnabled()) {
                                log.debug("登录耗时 " + username + " 3 ：" + (TimeUtil.currentTimeMillis() - currentTimeMillis));
                            }
                            userinfo = DataManager.getInstance().getDataDao().getObjectByWhere(UserInfo.class, "where `userNameLowerCase` = ?", usernameLowerCase);
                            if (log.isDebugEnabled()) {
                                log.debug("登录耗时 " + username + " 4 ：" + (TimeUtil.currentTimeMillis() - currentTimeMillis));
                            }
                            if (userinfo == null) {
                                if ("robottroy".equalsIgnoreCase(usernameLowerCase)) {
                                    request.addContent(getErrorCode(31, "注册用户失败，，特殊账号不能注册!"));
                                    if (log.isInfoEnabled()) {
                                        log.info("平台：" + platform + ", ip：" + request.getIp() + ", 用户：" + username + " 注册用户失败，，特殊账号不能注册!!!");
                                    }
                                    return;
                                }

                                if (log.isInfoEnabled()) {
                                    log.info("用户：" + username + " 数据库不存在!!!创建用户");
                                }

                                userinfo = new UserInfo();
                                userinfo.setId(GlobalUtil.getId());
                                userinfo.setUserName(username);
                                userinfo.setUserNameLowerCase(usernameLowerCase);
                                userinfo.setUserPwd(userpwd);
                                userinfo.setCreateTime(TimeUtil.currentTimeMillis());
                                userinfo.setLastLoginTime(TimeUtil.currentTimeMillis());
                                userinfo.setStatus(1);
                                userinfo.setUserMail("");
                                userinfo.setUserPhone("");
                                userinfo.setPlatformId(platform);
                                userinfo.setChannelId(channelId);
                                DataManager.getInstance().getDataCUDThread().insert_Sync(userinfo);
                            }
                            //保存用户信息数据
                            DataManager.getInstance().getDataCachePool().add(usernameLowerCase, userinfo, 10 * 60 * 60 * 1000, true);/*10个小时的滑动缓存*/
                            //存储用户名索引
                            DataManager.getInstance().getUserInfoMapIndex().put(userinfo.getId(), usernameLowerCase);
                            if (log.isDebugEnabled()) {
                                log.debug("登录耗时 " + username + " 5 ：" + (TimeUtil.currentTimeMillis() - currentTimeMillis));
                            }
                        }
                    }
                } else if (log.isInfoEnabled()) {
                    log.info("平台：" + platform + ", ip：" + request.getIp() + ", 用户：" + username + " 缓存用户!!!");
                }

                if (!userinfo.getUserName().equals(username) || !userinfo.getUserPwd().equals(userpwd)) {
                    request.addContent(getErrorCode(3, "用户名或密码错误！"));
                    if (log.isInfoEnabled()) {
                        log.info("平台：" + platform + ", ip：" + request.getIp() + ", 用户：" + username + " 用户名或密码错误!!!");
                    }
                } else {
                    //token生成之后3分钟
                    long md5time = TimeUtil.currentTimeMillis();
                    //String token = MD5Util.md5Encode('=', userinfo.getId() + "", username, request.getIp(), md5time + "", MyAttributeKey.TOKENKEY);
                    String token = getMD5(userinfo.getId(), username, md5time);
                    //更新token
                    userinfo.setToken(token);
                    //更新token生成时间
                    userinfo.setTokenTime(md5time);

                    userinfo.setLastLoginTime(TimeUtil.currentTimeMillis());
                    if (log.isDebugEnabled()) {
                        log.debug("登录耗时 " + username + " 6 ：" + (TimeUtil.currentTimeMillis() - currentTimeMillis));
                    }
                    String serverInfo = "";//ServerManager.getInstance().serverInfoScript.getServerInfo(platform, channelId, request, userinfo);
                    if (log.isDebugEnabled()) {
                        log.debug("登录耗时 " + username + " 7 ：" + (TimeUtil.currentTimeMillis() - currentTimeMillis));
                    }
                    Ret ret = new Ret(0, 0);
                    ServerInfo nextGateServer = getNextGateServer();
                    if (nextGateServer != null) {
                        ret.setGateserverid(nextGateServer.getServerId());
                        ret.setGatecoder(nextGateServer.getServerCode());
                        SocketListenInfo sli = nextGateServer.getListenInfos().get(0);
                        if (sli != null) {
                            ret.setGateip(sli.getTcpIp());
                            ret.setGateport(sli.getTcpPort());
                        }
                    }
                    ret.setToken(token);
                    ret.setTime(md5time);
                    ret.setUserName(username);
                    ret.setUid(userinfo.getId());
                    String toJSONString = ret.showString(serverInfo);
                    if (log.isDebugEnabled()) {
                        log.debug("登录耗时 " + username + " 8 ：" + (TimeUtil.currentTimeMillis() - currentTimeMillis));
                    }
                    if (log.isDebugEnabled()) {
                        log.debug("平台：" + platform + ", ip：" + request.getIp() + ", 用户：" + username + " 用户登录完成!!!同步服务器信息：" + toJSONString);
                    }
                    request.addContent(toJSONString);
                    if (log.isDebugEnabled()) {
                        log.debug("登录耗时 " + username + " 8 ：" + (TimeUtil.currentTimeMillis() - currentTimeMillis));
                    }
                    if (log.isInfoEnabled()) {
                        log.debug("平台：" + platform + ", ip：" + request.getIp() + ", 用户：" + username + " 用户登录完成!!!");
                    }
                }
            } catch (Exception e) {
                log.error("平台：" + platform + ", ip：" + request.getIp() + ", 用户：" + username + " 登录发生错误信息", e);
                request.addContent(getErrorCode(500, "服务器内部错误"));
            }
        }
    }

    public static void main(String[] args) {
        String jsonString = "{code:0, token:\"af0ca5ee6203e02ec076aa8b84385d08\", userName:\"ROBOTsz111\", msg:\"\", time:1469087482055, uid:197, infos:[{zoneId:100, serverGroup:\"测试大区\", serverId:\"1003\", serverName:\"服务器（刘富顺）\", tcpIp:\"182.150.21.45\", tcpPort:8084, httpIP:\"182.150.21.45\", httpPort:9094, idenIcon:\"\", startTime:\"1\", otherString:\"\", serverState:\"维护\", nextOpenTime:\"\"},{zoneId:200, serverGroup:\"测试专区\", serverId:\"1\", serverName:\"终焉之时\", tcpIp:\"182.150.21.45\", tcpPort:8083, httpIP:\"182.150.21.45\", httpPort:9093, idenIcon:\"new\", startTime:\"1\", otherString:\" \", serverState:\"维护\", nextOpenTime:\" \"},{zoneId:100, serverGroup:\"测试大区\", serverId:\"1001\", serverName:\"服务器（陈飞）\", tcpIp:\"182.150.21.45\", tcpPort:8084, httpIP:\"182.150.21.45\", httpPort:9094, idenIcon:\"\", startTime:\"1\", otherString:\"\", serverState:\"维护\", nextOpenTime:\"\"},{zoneId:100, serverGroup:\"测试大区\", serverId:\"1002\", serverName:\"服务器（吴复全）\", tcpIp:\"182.150.21.45\", tcpPort:8084, httpIP:\"182.150.21.45\", httpPort:9094, idenIcon:\"\", startTime:\"1\", otherString:\"\", serverState:\"维护\", nextOpenTime:\"\"},{zoneId:100, serverGroup:\"测试大区\", serverId:\"2\", serverName:\"客户端\", tcpIp:\"182.150.21.45\", tcpPort:7075, httpIP:\"182.150.21.45\", httpPort:9094, idenIcon:\"xingxing\", startTime:\"1\", otherString:\"\", serverState:\"维护\", nextOpenTime:\"\"}]}";
        jsonString = new LoginScript().getErrorCode(10, "830510");
        Ret parseObject = JsonUtil.parseObject(jsonString, Ret.class);
        log.error(parseObject.toString());
    }

    ServerInfo getNextGateServer() {
        ServerInfo[] toArray = ServerManager.getInstance().getGateInfoMap().values().toArray(new ServerInfo[0]);
        ServerInfo si = null;
        for (ServerInfo tmp : toArray) {
            if (si == null || si.getLastUpdatecount() < tmp.getLastUpdatecount()) {
                if (TimeUtil.currentTimeMillis() - tmp.getLastUpdatetimer() < 5000) {
                    /*服务器同步时间不能超过 5 秒*/
                    si = tmp;
                }
            }
        }
        return si;
    }

    static class Ret {

        private int code;
        private String token;
        private String userName;
        private int msg;
        private long time;
        private long uid;
        private int gateserverid;
        private int gatecoder;
        private String gateip;
        private int gateport;

        public Ret(int code, int msg) {
            this.code = code;
            this.msg = msg;
        }

        public Ret() {
        }

        public String showString(String serverinfos) {
            return "{"
                    + "\"code\":" + code
                    + ", \"token\":\"" + token + "\", \"userName\":\"" + userName + "\", \"msg\":\"" + msg + "\", \"time\":" + time
                    + ", \"uid\":" + uid +", \"gateserverid\":" + gateserverid +", \"gatecoder\":" + gatecoder+ ", \"gateip\":\"" + gateip + "\", \"gateport\":" + gateport + "}";
        }

        @Override
        public String toString() {
            return "Ret{" + "code=" + code + ", token=" + token + ", userName=" + userName + ", msg=" + msg + ", time=" + time + ", uid=" + uid + ", tcpip=" + gatecoder + ", tcpport=" + gateport + '}';
        }

        /**
         * @return the code
         */
        public int getCode() {
            return code;
        }

        /**
         * @param code the code to set
         */
        public void setCode(int code) {
            this.code = code;
        }

        /**
         * @return the token
         */
        public String getToken() {
            return token;
        }

        /**
         * @param token the token to set
         */
        public void setToken(String token) {
            this.token = token;
        }

        /**
         * @return the userName
         */
        public String getUserName() {
            return userName;
        }

        /**
         * @param userName the userName to set
         */
        public void setUserName(String userName) {
            this.userName = userName;
        }

        /**
         * @return the msg
         */
        public int getMsg() {
            return msg;
        }

        /**
         * @param msg the msg to set
         */
        public void setMsg(int msg) {
            this.msg = msg;
        }

        /**
         * @return the time
         */
        public long getTime() {
            return time;
        }

        /**
         * @param time the time to set
         */
        public void setTime(long time) {
            this.time = time;
        }

        /**
         * @return the uid
         */
        public long getUid() {
            return uid;
        }

        /**
         * @param uid the uid to set
         */
        public void setUid(long uid) {
            this.uid = uid;
        }

        public int getGateserverid() {
            return gateserverid;
        }

        public void setGateserverid(int gateserverid) {
            this.gateserverid = gateserverid;
        }

        public int getGatecoder() {
            return gatecoder;
        }

        public void setGatecoder(int gatecoder) {
            this.gatecoder = gatecoder;
        }

        public String getGateip() {
            return gateip;
        }

        public void setGateip(String gateip) {
            this.gateip = gateip;
        }

        public int getGateport() {
            return gateport;
        }

        public void setGateport(int gateport) {
            this.gateport = gateport;
        }

    }

}
