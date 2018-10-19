package com.eshel.mine.aidl;

public class Mine{
    int x;
    int y;
    int mineNumber;//周围雷的数量 -1代表本身是雷
    public String toJson(){
        return x+"="+y+"="+mineNumber;
    }

    public static String toJson(int x, int y, int mineNumber){
        return x+"="+y+"="+mineNumber;
    }

    public void fromJson(String json){
        String[] split = json.split("=");
        x = Integer.parseInt(split[0]);
        y = Integer.parseInt(split[1]);
        mineNumber = Integer.parseInt(split[2]);
    }
}
