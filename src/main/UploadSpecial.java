package main;

import base.Base;
import bean.BmobObject;
import com.google.gson.Gson;
import util.*;

import java.util.List;
import java.util.Map;


//上传专栏曲谱
//步骤：在special下加入图片：曲谱_1,曲谱_2
public class UploadSpecial extends Base {
    private static final String mSpecialBasePath = "C:\\Users\\81256\\Desktop\\special\\";
    private static final String content = "来自谱友上传";
    private static final String columnId= Constant.ALBUM_rumenqu_Id;
    public static void main(String[] args) {
        initBmob();
        Map<String,List<String>> map= FileUtil.getShareMap(mSpecialBasePath);
        for (Map.Entry<String,List<String>> entry:map.entrySet()){
            String trueName=entry.getKey();
            List<String> list=entry.getValue();
            String specialColumnSongObjectId = insertSpecialColumnSong(trueName, content);
            for (int i = 0; i < list.size(); i++) {
                System.out.println(trueName + "  " + list.get(i));
                String url=FileUtil.uploadPic(mSpecialBasePath,list.get(i));
                insertSpecialPic(specialColumnSongObjectId, url);
            }
        }
    }

    //插入图片
    private static void insertSpecialPic(String specialColumnSongObjectId, String url) {
        //指定某个SpecialSong
        BSONObject specialSong = new BSONObject();
        specialSong.put("__type", "Pointer");
        specialSong.put("className", "SpecialColumnSong");
        specialSong.put("objectId", specialColumnSongObjectId);

        //设置为SpecialPic
        BSONObject specialPic = new BSONObject();
        specialPic.put("url", url);
        specialPic.put("song", specialSong);
        String result = Bmob.insert("SpecialColumnPic", specialPic.toString());
        System.out.println("insert Special Pic : " + result);
    }

    //插入歌
    private static String insertSpecialColumnSong(String trueName, String content) {
        //设置专辑栏
        BSONObject column=new BSONObject();
        column.put("__type","Pointer");
        column.put("className","SpecialColumn");
        column.put("objectId", columnId);

        //设置specialSong
        BSONObject specialSong = new BSONObject();
        specialSong.put("name",trueName);
        specialSong.put("content", content);
        specialSong.put("column",column);

        //插入
        String result = Bmob.insert("SpecialColumnSong", specialSong.toString());
        System.out.println("insert Special Song : " + result);
        Gson gson = new Gson();
        return gson.fromJson(result, BmobObject.class).getObjectId();
    }
}
