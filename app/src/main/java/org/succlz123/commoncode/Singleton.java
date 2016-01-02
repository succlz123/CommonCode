package org.succlz123.commoncode;

/**
 * Created by succlz123 on 16/1/7.
 */
public abstract class Singleton<T> {
    private T mT;

    protected abstract T create();

    public final T get(){
        synchronized (this){
            if (mT==null){
                mT=create();
            }
            return mT;
        }
    }
}
