package net.sz.chess.model.struct.game;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/shizuchengxuyuan/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public enum GameType {
    SCMJ(10001, 10001, 1, "四川麻将"),
    DQG(10001, 10001, 1, "断勾卡");
    /*游戏类型*/
    int gtype;
    /*执行脚本*/
    int gscript;
    /*执行程序版本号*/
    int coder;
    /*游戏描述*/
    String msgString;

    private GameType(int gtype, int gscript, int coder, String msgString) {
        this.gtype = gtype;
        this.gscript = gscript;
        this.coder = coder;
        this.msgString = msgString;
    }

    public int getGtype() {
        return gtype;
    }

    public void setGtype(int gtype) {
        this.gtype = gtype;
    }

    public String getMsgString() {
        return msgString;
    }

    public void setMsgString(String msgString) {
        this.msgString = msgString;
    }

    /**
     *
     * @param gtype
     * @return
     */
    public GameType getGameType(int gtype) {
        GameType[] values = GameType.values();
        return null;
    }

}
