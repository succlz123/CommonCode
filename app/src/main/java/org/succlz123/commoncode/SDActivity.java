package org.succlz123.commoncode;

import org.succlz123.commoncode.base.BaseActivity;
import org.succlz123.commoncode.contentProvider.VideoDownloadContentProvider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by succlz123 on 16/4/1.
 */
public class SDActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pr);


        final ContentResolver resolver = getContentResolver();
        final Uri uri = VideoDownloadContentProvider.COUNT_URI;
        //添加一条记录
//        ContentValues values = new ContentValues();
//        values.put("name", "bingxin");
//        values.put("age", 25);
//        resolver.insert(uri, values);
//
        //把id为1的记录的name字段值更改新为zhangsan
//        ContentValues updateValues = new ContentValues();
//        updateValues.put("name", "zhangsan");
//        Uri updateIdUri = ContentUris.withAppendedId(uri, 2);
//        resolver.update(updateIdUri, updateValues, null, null);

        //删除id为2的记录
//        Uri deleteIdUri = ContentUris.withAppendedId(uri, 2);
//        resolver.delete(deleteIdUri, null, null);

//        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox);
//
//        resolver.notifyChange(uri, new ContentObserver() {
//            @Override
//            public boolean deliverSelfNotifications() {
//                return super.deliverSelfNotifications();
//            }
//
//            @Override
//            public void onChange(boolean selfChange) {
//                super.onChange(selfChange);
//            }
//
//            @Override
//            public void onChange(boolean selfChange, Uri uri) {
//                super.onChange(selfChange, uri);
//            }
//        });
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取person表中所有记录
                Cursor cursor = resolver.query(uri, null, null, null, null);
                while (cursor.moveToNext()) {
                    Log.w("ContentTest", "personid=" + cursor.getString(0) + ",name=" + cursor.getString(1));
                }

//                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
//                startActivityForResult(intent, 42);
            }
        });

    }

}
