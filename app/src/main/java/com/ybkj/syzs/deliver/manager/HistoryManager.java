package com.ybkj.syzs.deliver.manager;

import com.ybkj.syzs.deliver.bean.response.OrderSearchHistory;
import com.ybkj.syzs.deliver.utils.preferences.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description 一些搜索历史的存储
 * Author Ren Xingzhi
 * Created on 2019/4/13.
 * Email 15384030400@163.com
 */
public class HistoryManager {

    private static String KEY_USER_HISTORY = "user_history";

    public static void addOrderSearchHistory(String keyWord) {

        OrderSearchHistory userSearchHistory = (OrderSearchHistory) PreferencesUtils.getObject(KEY_USER_HISTORY, null);
        if (userSearchHistory == null) {
            userSearchHistory = new OrderSearchHistory();
            List<String> data = new ArrayList<>();
            data.add(keyWord);
            userSearchHistory.setKewords(data);
        } else {
            List<String> data = userSearchHistory.getKewords();
            for (String name : data) {
                if (keyWord.equals(name)) {
                    data.remove(name);
                    break;
                }
            }
            data.add(0, keyWord);
            userSearchHistory.setKewords(data);
        }
        PreferencesUtils.saveObject(KEY_USER_HISTORY, userSearchHistory);
    }

    public static OrderSearchHistory getOrderSearchHistory() {
        return (OrderSearchHistory) PreferencesUtils.getObject(KEY_USER_HISTORY, null);
    }

    public static void clearOrderSearchHistory() {
        PreferencesUtils.saveObject(KEY_USER_HISTORY, null);
    }
}
