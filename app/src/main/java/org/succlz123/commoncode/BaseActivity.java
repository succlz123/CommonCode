package org.succlz123.commoncode;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by succlz123 on 16/1/6.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        danli = new BaseActivity.danli();


        danli = new BaseActivity.danli();

        BaseActivity activity = danli.get();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private static danli danli;

    private static class danli extends Singleton<BaseActivity> {
        @Override
        protected BaseActivity create() {
            return new BaseActivity();
        }
    }

}
