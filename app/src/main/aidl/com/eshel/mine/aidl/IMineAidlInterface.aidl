// IMineAidlInterface.aidl
package com.eshel.mine.aidl;
/**
 * 扫雷对外提供 AIDL 接口
 */
interface IMineAidlInterface {

    String test();
    /**
     * 游戏开始与否
     */
    boolean gameStarted();
    /**
     * 获取一行多少雷
     */
    int getMinesWidth();
    /**
     * 获取一列多少雷
     */
    int getMinesHeight();

    /**
     * 获取一个格子周围有几个未知格子
     * 被标记旗子不计数, 被标记问号计数
     */
    int getUnKnown(int x, int y);

    /**
     * 点击扫雷
     * @return 变化的雷的集合, 格式为: x=y=mineNumber
     * 角标为0的元素对应点击的那个
     */
    List<String> clickMine(int x, int y);
    /**
     * 长按标记
     */
    void longClickMine(int x, int y);
    /**
     * 0 - 8 代表 周围雷的数量
     * -1代表本身是雷
     * 10 代表 未知
     * 11 代表被标记
     * 12 代表 ? 标记
     * -2 代表错误
     */
    int lookMineType(int x, int y);

    /**
     * 0 游戏未开始
     * 1 游戏进行中(已开始)
     * -1 游戏结束 , Game Over You Lose
     * 2  游戏结束, You Win
     */
    int seeGameState();
}
