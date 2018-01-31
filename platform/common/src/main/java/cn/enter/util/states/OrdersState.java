package cn.enter.util.states;

/**
 * @Author leo_Yang【音特】
 * @Date 2017/11/23 0023 18:57
 */
public class OrdersState {

    public static final String ALL                                   =   "0";//全部
    public static final String UNPAID                                =   "1";//未付款
    public static final String PAID                                  =   "2";//已付款
    public static final String CHECKED                               =   "3";//已付款,已确定
    public static final String SEND_OUT                              =   "4";//已发货
    public static final String SUCCESS                               =   "5";//已收货--完成
    public static final String CLOSED                                =   "6";//已关闭
}
