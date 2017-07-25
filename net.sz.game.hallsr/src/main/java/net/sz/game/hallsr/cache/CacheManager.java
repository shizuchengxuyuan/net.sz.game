package net.sz.game.hallsr.cache;

import java.util.concurrent.ConcurrentHashMap;
import net.sz.framework.szlog.SzLogger;

/**
 * 缓存管理器
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class CacheManager {

    private static final SzLogger log = SzLogger.getLogger();
    private static final CacheManager IN_ME = new CacheManager();

    public static CacheManager getInstance() {
        return IN_ME;
    }

    /*记录玩家当前在哪个服务器通讯*/
    private ConcurrentHashMap<Long, Integer> playerIdToRoomIdMap = new ConcurrentHashMap<>();

    public CacheManager() {
    }

}
