package com.dragon.module_base.utils.room;

import com.dragon.module_base.utils.FileTool;
import com.getkeepsafe.relinker.ReLinker;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabaseHook;
import net.sqlcipher.database.SQLiteException;
import net.sqlcipher.database.SQLiteOpenHelper;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;


public class MySupportHelper implements SupportSQLiteOpenHelper {
    private SQLiteOpenHelper standardHelper;
    private byte[] passphrase;
    private final boolean clearPassphrase;

    MySupportHelper(final Configuration configuration,
                    byte[] passphrase, final SQLiteDatabaseHook hook,
                    boolean clearPassphrase) {
        //加载so库
//        ReLinker.log(new ReLinker.Logger() {
//            @Override
//            public void log(String message) {
//                Log.e("加载so测试", "log: "+message );
//            }
//        }).loadLibrary(configuration.context, "sqlcipher", new ReLinker.LoadListener() {
//            @Override
//            public void success() {
//                Log.e("加载so测试", "log: 成功了" );
//            }
//
//            @Override
//            public void failure(Throwable t) {
//                Log.e("加载so测试", "log: 失败了"+t );
//                //加载本地编译好的so库
//                ReLinker.loadLibrary(configuration.context,"mysqlcipher");
//            }
//        });

        try {
            //封好的
            SQLiteDatabase.loadLibs(configuration.context);
        }catch (Throwable e){
//            if (e instanceof UnsatisfiedLinkError){
//            }
            //用ReLinker来加载 TODO 如果还找不到so或so加载继续出错是否可直接加载另一个已经编译好的so
            ReLinker.force().loadLibrary(configuration.context,"sqlcipher");
        }

        this.passphrase = passphrase;
        this.clearPassphrase = clearPassphrase;

        standardHelper =
                new SQLiteOpenHelper(configuration.context, configuration.name,
                        null, configuration.callback.version, hook) {
                    @Override
                    public void onCreate(SQLiteDatabase db) {
                        configuration.callback.onCreate(db);
                    }

                    @Override
                    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                                          int newVersion) {
                        configuration.callback.onUpgrade(db, oldVersion,
                                newVersion);
                    }

                    @Override
                    public void onDowngrade(SQLiteDatabase db, int oldVersion,
                                            int newVersion) {
                        configuration.callback.onDowngrade(db, oldVersion,
                                newVersion);
                    }

                    @Override
                    public void onOpen(SQLiteDatabase db) {
                        configuration.callback.onOpen(db);
                    }

                    @Override
                    public void onConfigure(SQLiteDatabase db) {
                        configuration.callback.onConfigure(db);
                    }
                };

    }

    @Override
    public String getDatabaseName() {
        return standardHelper.getDatabaseName();
    }

    @Override
    public void setWriteAheadLoggingEnabled(boolean enabled) {
        standardHelper.setWriteAheadLoggingEnabled(enabled);
    }

    @Override
    public SupportSQLiteDatabase getWritableDatabase() {
        SQLiteDatabase result;
        try {
            result = standardHelper.getWritableDatabase(passphrase);
        } catch (SQLiteException ex) {
            //TODO 不能正确打开数据库文件时候
            if (passphrase != null) {
                boolean isCleared = true;
                for (byte b : passphrase) {
                    isCleared = isCleared && (b == (byte) 0);
                }
                if (isCleared) {
                    //TODO 密码问题
//                    throw new IllegalStateException("The passphrase appears to be cleared. This happens by " +
//                            "default the first time you use the factory to open a database, so we can remove the " +
//                            "cleartext passphrase from memory. If you close the database yourself, please use a " +
//                            "fresh SupportFactory to reopen it. If something else (e.g., Room) closed the " +
//                            "database, and you cannot control that, use SupportFactory boolean constructor option " +
//                            "to opt out of the automatic password clearing step. See the project README for more information.", ex);
                }
            }
            //TODO 数据库文件 问题
//            EventBus.getDefault().post(new CommonEvent.DbHasError());
            try {
                //删除有问题的数据库文件
                FileTool.deleteFileFromDir("/data/data/com.snibe.x8tablet/databases");
                result = standardHelper.getWritableDatabase(passphrase);
            } catch (SQLiteException es) {
                //创建数据库文件失败
                throw es;
            }
            return result;
//            throw ex;
        }
        if (clearPassphrase && passphrase != null) {
            for (int i = 0; i < passphrase.length; i++) {
                passphrase[i] = (byte) 0;
            }
        }
        return result;
    }

    @Override
    public SupportSQLiteDatabase getReadableDatabase() {
        return getWritableDatabase();
    }

    @Override
    public void close() {
        standardHelper.close();
    }
}
