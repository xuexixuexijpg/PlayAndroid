package com.dragon.common_database.helper;

import net.sqlcipher.database.SQLiteDatabaseHook;

import androidx.sqlite.db.SupportSQLiteOpenHelper;

/**
 * [SupportFactory]
 */
public class MySupportFactory implements SupportSQLiteOpenHelper.Factory {
    private final byte[] passphrase;
    private final SQLiteDatabaseHook hook;
    private final boolean clearPassphrase;

    public MySupportFactory(byte[] passphrase) {
        this(passphrase, (SQLiteDatabaseHook)null);
    }

    public MySupportFactory(byte[] passphrase, SQLiteDatabaseHook hook) {
        this(passphrase, hook, true);
    }

    public MySupportFactory(byte[] passphrase, SQLiteDatabaseHook hook,
                          boolean clearPassphrase) {
        this.passphrase = passphrase;
        this.hook = hook;
        this.clearPassphrase = clearPassphrase;
    }

    @Override
    public SupportSQLiteOpenHelper create(SupportSQLiteOpenHelper.Configuration configuration) {
        return new MySupportHelper(configuration, passphrase, hook, clearPassphrase);
    }
}
