package com.cashbook.cashbook.mvp;

/**
 * @author luruiqian
 */

public interface  BasePresenter <V extends BaseView>{
    void attachView(V view);

    void detachView();
}
