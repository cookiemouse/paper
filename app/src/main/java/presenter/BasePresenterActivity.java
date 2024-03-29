/*
 * Copyright 2015 LuMeng
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package presenter;

import android.app.Activity;
import android.os.Bundle;

import com.zhy.autolayout.AutoLayoutActivity;

import vu.Vu;

/**
 * @author lumeng on 15/12/25.
 */
public abstract class BasePresenterActivity<V extends Vu> extends Activity {
    protected V vu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            vu = getVuClass().newInstance();
            vu.init(getLayoutInflater(), null);
            setContentView(vu.getView());
            onBindVu();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        beforePause();
        super.onPause();
    }

    protected void beforePause() {
        // TODO: 15/12/25 do something
    }

    @Override
    protected void onResume() {
        super.onResume();
        afterResume();
    }

    protected void afterResume() {
        // TODO: 15/12/25 do something
    }

    @Override
    protected void onDestroy() {
        onDestroyVu();
        vu = null;
        super.onDestroy();
    }

    protected void onDestroyVu() {
        // TODO: 15/12/25 do something
    }

    protected void onBindVu() {}

    protected abstract Class<V> getVuClass();
}
