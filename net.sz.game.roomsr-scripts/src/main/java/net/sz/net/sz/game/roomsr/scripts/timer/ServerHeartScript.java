package net.sz.net.sz.game.roomsr.scripts.timer;

//package net.sz.chess.roomsr.scripts.timer;
//
//import net.sz.chess.message.proto.RegisterHallSrMessage;
//import net.sz.chess.roomsr.app.RoomsrManager;
//import net.sz.framework.scripts.timer.iscript.ISecondsEventTimerScript;
//import net.sz.framework.szlog.SzLogger;
//import net.sz.framework.utils.TimeUtil;
//
///**
// *
// * <br>
// * author 失足程序员<br>
// * blog http://www.cnblogs.com/ty408/<br>
// * mail 492794628@qq.com<br>
// * phone 13882122019<br>
// */
//public class ServerHeartScript implements ISecondsEventTimerScript {
//
//    private static final SzLogger log = SzLogger.getLogger();
//
//    @Override
//    public void run(int sec) {
//
//        if (sec % 10 == 0) {
//
//            RegisterHallSrMessage.GHServerHeartMessage.Builder serverheart = RegisterHallSrMessage.GHServerHeartMessage.newBuilder();
//
//            serverheart.setTime(TimeUtil.currentTimeMillis());
//
//            RoomsrManager.getInstance().getHallServerSessionClient().addMessage(serverheart);
//
//        }
//    }
//
//}
