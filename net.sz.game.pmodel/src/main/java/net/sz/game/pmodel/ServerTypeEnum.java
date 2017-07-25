package net.sz.game.pmodel;

/**
 *
 * <br>
 * author 失足程序员<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public enum ServerTypeEnum {
    HallServer(1, "大厅服务器"),
    GateServer(2, "网管服务器"),
    RoomServer(3, "房间服务器"),
    LoginServer(4, "登录服务器"),;
    private int stype;
    private String msg;

    private ServerTypeEnum(int stype, String msg) {
        this.stype = stype;
        this.msg = msg;
    }

    public int getStype() {
        return stype;
    }

    public String getMsg() {
        return msg;
    }

    public static ServerTypeEnum getServerType(int stype) {
        ServerTypeEnum[] values = ServerTypeEnum.values();
        for (ServerTypeEnum value : values) {
            if (stype == value.stype) {
                return value;
            }
        }
        return null;
    }

}
