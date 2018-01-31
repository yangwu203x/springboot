package cn.enter.util.states;

/**
 * @Author leo_Yang【音特】
 * @Date 2017/11/23 0023 18:57
 */
public class TrolleyState {

    public static final int NORELATED                                    =   0;//单纯的加入了购物车
    public static final int RELATEDSHOW                                  =   1;//加入了购物车并生成了订单详情，显示在购物车中
    public static final int RELATEDHIDE                                  =   2;//立即购买生成的购物车
    public static final int RELATEDORDERS                                =   3;//生成了订单
}
