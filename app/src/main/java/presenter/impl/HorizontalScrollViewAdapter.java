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

package presenter.impl;

import com.lumeng.paper.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import presenter.BasePresenterAdapter;
import vu.impl.ReboundItemVu;

/**
 * @author lumeng on 15/12/18.
 *         E-Mail: lumenghz@gmail.com
 */
public class HorizontalScrollViewAdapter extends BasePresenterAdapter<ReboundItemVu> {

    private List<Integer> mDatas = new ArrayList<Integer>(Arrays.asList(
            R.mipmap.one, R.mipmap.two, R.mipmap.three,
            R.mipmap.four, R.mipmap.five, R.mipmap.six));

    public int getCount() {
        return mDatas.size();
    }

    public Object getItem(int position) {
        return mDatas.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    protected Class<ReboundItemVu> getVuClass() {
        return ReboundItemVu.class;
    }

    @Override
    protected void onBindItemVu(int position) {
        vu.setRes(mDatas.get(position));
    }
}
