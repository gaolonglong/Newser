package com.gaolonglong.app.newser.presenter;

import android.app.Activity;

/**
 * Created by gaohailong on 2016/11/24.
 */

public interface NewsPresenter {
    void loadNews(Activity activity, String type, int indexPage);
}
