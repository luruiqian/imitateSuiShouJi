package com.cashbook.cashbook.database;

import java.util.List;

/**
 * Created by luruiqian on 2018/6/4.
 */

public class CashbookInfo {
    public int beizhu;
    public String money;
    public List<AccountItem> accountItemList;
    public String name;
    public String type;


    public static class AccountItem {
        public String itemName;
        public String itemDesc;
    }
}
