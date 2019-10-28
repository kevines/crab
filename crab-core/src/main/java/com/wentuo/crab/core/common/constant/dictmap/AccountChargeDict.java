/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wentuo.crab.core.common.constant.dictmap;


import com.wentuo.crab.core.common.constant.dictmap.base.AbstractDictMap;

/**
 * 账号充值的映射
 *
 * @author fengshuonan
 * @date 2017-05-06 15:01
 */
public class AccountChargeDict extends AbstractDictMap {

    @Override
    public void init() {
        put("id", "id");
        put("chargeNo", "充值编号");
        put("phone", "手机号码");
        put("checkUserId", "审核人用户ID");
        put("amount", "充值金额");
        put("description", "备注");
    }

    @Override
    protected void initBeWrapped() {
//        putFieldWrapperMethodName("deptId", "getDeptName");
//        putFieldWrapperMethodName("pid", "getDeptName");
    }
}
