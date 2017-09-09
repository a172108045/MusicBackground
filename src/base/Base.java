package base;

import util.Bmob;

public class Base {
    //使用RestAPI前必须先初始化，KEY可在Bmob应用信息里查询。
    public static void initBmob() {
        Bmob.initBmob("236163bbf4c3be6f2cc44de4405da6eb",
                "7a91bc58cf83cd74619ca30203491b39");
    }
}
