/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jjs.com.simple.ui.activity.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.github.obsessive.library.base.BaseSwipeBackCompatActivity;


import butterknife.ButterKnife;
import jjs.com.simple.R;
import jjs.com.simple.SimpleApplication;
import jjs.com.simple.view.base.BaseView;

/**
 * Author:  Tau.Chen
 * Email:   1076559197@qq.com | tauchen1990@gmail.com
 * Date:    15/7/21
 * Description:
 */
public abstract class BaseSwipeBackActivity extends BaseSwipeBackCompatActivity implements BaseView {

    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isApplyKitKatTranslucency()) {
            setSystemBarTintDrawable(getResources().getDrawable(R.drawable.sr_primary));
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mToolbar = ButterKnife.findById(this, R.id.common_toolbar);
        if (null != mToolbar) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    protected SimpleApplication getBaseApplication() {
        return (SimpleApplication) getApplication();
    }


    @Override
    public void showError(String msg) {
        toggleShowError(true, msg, null);
    }

    @Override
    public void showException(String msg) {
        toggleShowError(true, msg, null);
    }

    @Override
    public void showNetError() {
        toggleNetworkError(true, null);
    }

    @Override
    public void showLoading(String msg) {
        toggleShowLoading(true, null);
    }

    @Override
    public void hideLoading() {
        toggleShowLoading(false, null);
    }

    protected abstract boolean isApplyKitKatTranslucency();
}
