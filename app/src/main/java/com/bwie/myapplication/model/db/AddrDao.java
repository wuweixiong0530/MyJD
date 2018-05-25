package com.bwie.myapplication.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AddrDao {
    private Context context;
    private String DB_NAME = "aball360.db";

    public AddrDao(Context context) {
        this.context = context;
    }

    public SQLiteDatabase initAddrDao() {
        File fileDir = new File(Environment.getExternalStorageDirectory(), "databases");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        String dbPath = fileDir + DB_NAME;
        if (!new File(dbPath).exists()) {
            try {
                FileOutputStream out = new FileOutputStream(dbPath);
                InputStream in = context.getAssets().open("aball360.db");
                byte[] buffer = new byte[1024];
                int readBytes = 0;
                while ((readBytes = in.read(buffer)) != -1)
                    out.write(buffer, 0, readBytes);
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return SQLiteDatabase.openOrCreateDatabase(dbPath, null);
    }
}
