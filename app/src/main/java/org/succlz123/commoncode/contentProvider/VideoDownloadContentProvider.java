package org.succlz123.commoncode.contentProvider;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import androidx.annotation.Nullable;

/**
 * Created by succlz123 on 16/4/2.
 */
public class VideoDownloadContentProvider extends ContentProvider {
    public static final String AUTHORITY = "tv.danmaku.bili.providers.VideoDownloadSettingProvider";
    public static final String PATH_TOTAL_COUNT = "setting";

    public static final Uri COUNT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH_TOTAL_COUNT);

    private static final String NAME = "video_download_setting";

    private UriMatcher mUriMatcher;
    private static final String[] mSettingColumnNames = {"downloadPath", "enableGPRSDownload","autoDownload"};
    private int[] mCounts = new int[3];

    @Override
    public boolean onCreate() {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(AUTHORITY, PATH_TOTAL_COUNT, 1);
        FileInputStream input = null;

        try {
            input = getContext().openFileInput(NAME);
            FileChannel channel = input.getChannel();
            final ByteBuffer buffer = ByteBuffer.allocate(input.available());
            channel.read(buffer);
            buffer.rewind();
            buffer.asIntBuffer().get(mCounts);
            channel.close();
        } catch (Exception e) {

        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        MatrixCursor cursor = new MatrixCursor(mSettingColumnNames, 1);
        cursor.addRow(new Object[]{"wocoadfa", 1, 1});

        switch (mUriMatcher.match(uri)) {
            case 1:
                break;
        }


        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {


        return 0;
    }
}
