package com.cashbook.cashbook.flow.bean;

import java.util.ArrayList;

/**
 * 用户足迹列表的bean
 *
 * @author gaojie
 */
public class MyFootPrintBean {

    /**
     * 当前收藏项目是否被选中
     */
    public boolean isSelect;

    /**
     * 店铺列表
     */
    public ArrayList<MyFootPrintBean> footPrints;

    public int totalCount;
    public int	totalPage;
    public int pageSize;
    public int currentPage;//	当前页
    public String productId;
    public String skuId;
    public String produceName;//	商品名称	string
    public String stockNum;//	库存数量
    public String imgUrl;//	图片
    public String visitDate;//	访问时间
    public String onShelf;//	1上架0下架
    public String priceType;//	价格类型
    public String salePrice;//售价
    public boolean dataTag;//日期的标记
    public String stockState;// stockState	库存状态Y有库存N无库存
    public String schemeUrl;// 跳转商品详情页scheme


}
