package net.sz.game.loginsr.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import net.sz.framework.db.Dao;
import net.sz.framework.db.MysqlDaoImpl;
import net.sz.framework.db.thread.CUDThread;
import net.sz.framework.szlog.SzLogger;
import net.sz.framework.utils.PackageUtil;
import net.sz.net.sz.framework.caches.CachePool;

/**
 *
 * <br>
 * author 失足程序员<br>
 * blog http://www.cnblogs.com/ty408/<br>
 * mail 492794628@qq.com<br>
 * phone 13882122019<br>
 */
public class DataManager {

    private static final SzLogger log = SzLogger.getLogger();

    private static final DataManager IN_ME = new DataManager();

    public static DataManager getInstance() {
        return IN_ME;
    }

    private CachePool dataCachePool = new CachePool();
    private MysqlDaoImpl dataDao;
    private CUDThread dataCUDThread;
    /**
     * 用户索引
     */
    private ConcurrentHashMap<Long, String> userInfoMapIndex = new ConcurrentHashMap<>();

    private DataManager() {

    }

    public void initDbConfig() {
        try {

            dataDao = new MysqlDaoImpl("192.168.0.218:3306", "loginsr", "root", "1qaz2wsx");
            dataDao.createDatabase();
            checkTables();
            dataCUDThread = new CUDThread(dataDao, "数据");

        } catch (Exception ex) {

        }
    }

    public void checkTables() {
        try (Connection con = getDataDao().getConnection()) {
            String packageName = "net.sz.chess.pmodel.po.data";
            ArrayList<Class<?>> classNames = PackageUtil.getClazzs(packageName);
//            if (classNames != null) {
//                for (Class<?> className : classNames) {
//                    if (dataDao.checkClazz(className)) {
//                        dataDao.createTable(con, className);
//                    }
//                }
//            }

            packageName = "net.sz.chess.pmodel.po.loginsr";
            classNames = PackageUtil.getClazzs(packageName);
            if (classNames != null) {
                for (Class<?> className : classNames) {
                    if (dataDao.checkClazz(className)) {
                        dataDao.createTable(con, className);
                    }
                }
            }
        } catch (Throwable ex) {
            log.error("数据库自检", ex);
            System.exit(0);
        }
    }

    public CachePool getDataCachePool() {
        return dataCachePool;
    }

    public MysqlDaoImpl getDataDao() {
        return dataDao;
    }

    public CUDThread getDataCUDThread() {
        return dataCUDThread;
    }

    public ConcurrentHashMap<Long, String> getUserInfoMapIndex() {
        return userInfoMapIndex;
    }

}
