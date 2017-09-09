package check;

import base.Base;
import bean.ColumnList;
import com.google.gson.Gson;
import util.BSONObject;
import util.Bmob;

//自动巡检：更新每个专栏含有的曲谱数
public class UpdateSpecialColumn extends Base {
    public static void main(String[] args) {
        initBmob();
        String result=Bmob.find("SpecialColumn",50);
        Gson gson=new Gson();
        ColumnList columnList=gson.fromJson(result, ColumnList.class);
        for (int i=0;i<columnList.getResults().size();i++){
            ColumnList.ResultsBean column=columnList.getResults().get(i);
            System.out.println(column.getObjectId());
            int number=getSpecialColumnNumber(column.getObjectId());
            System.out.println(number);
            updateSpecialColumn(column.getObjectId(),number);
        }
    }

    private static void updateSpecialColumn(String objectId, int number) {
        BSONObject object=new BSONObject();
        object.put("songNum",number);
        Bmob.update("SpecialColumn",objectId,object.toString());
    }

    private static int getSpecialColumnNumber(String objectId) {
        BSONObject where=new BSONObject();
        where.put("column",objectId);
        return Bmob.count("SpecialColumnSong",where.toString());
    }
}
