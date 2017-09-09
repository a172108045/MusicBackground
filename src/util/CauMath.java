package util;

public class CauMath {

    public static int getPage(int count,int limit){
        int page=count/limit;
        if (count%limit>0){
            page++;
        }
        return page;
    }
}
