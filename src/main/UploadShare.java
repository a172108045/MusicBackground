package main;

import base.Base;
import bean.BmobObject;
import com.google.gson.Gson;
import util.BSONObject;
import util.Bmob;
import util.FileUtil;

import java.util.*;

//自动上传分享
// 格式，在share文件中放入xxx_1,xxx_2，
//并且在后台显示的标注id
public class UploadShare extends Base {
    private static final String mShareBasePath = "C:\\Users\\81256\\Desktop\\share\\";
    private static final String content = "最近在练习的歌";


    public static void main(String[] args) {
        initBmob();
        Map<String, List<String>> map = FileUtil.getShareMap(mShareBasePath);
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String trueName = entry.getKey();
            List<String> list = entry.getValue();
            for (int i = 0; i < list.size(); i++) {
                System.out.println(trueName + "  " + list.get(i));
            }
        }
        uploadPics(mShareBasePath,map);
    }


    private static void uploadPics(String dir,Map<String, List<String>> map) {
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String trueName = entry.getKey();
            List<String> list = entry.getValue();
            String shareObjectId = insertShareSong(trueName, content);
            for (int i = 0; i < list.size(); i++) {
                String url = FileUtil.uploadPic(dir,list.get(i));// http://bmob-cdn-6553.b0.upaiyun.com/2017/09/08/418f74d34079a90c80a3bd6d69f63896.jpg
                insertSharePic(shareObjectId, url);
                System.out.println("一张图片插入完成");
            }
        }


    }

    //插入shareSong
    private static String insertShareSong(String name, String content) {
        //设置shareSong
        BSONObject shareSong = new BSONObject();
        shareSong.put("name", name);
        shareSong.put("downloadNum", 0);
        shareSong.put("agreeNum", 0);
        shareSong.put("content", content);

        //插入
        String result = Bmob.insert("ShareSong", shareSong.toString());
        System.out.println("insert Share Song : " + result);
        Gson gson = new Gson();
        return gson.fromJson(result, BmobObject.class).getObjectId();
    }


    //插入SharePic
    private static void insertSharePic(String shareObjectId, String url) {
        //指定某个ShareSong
        BSONObject shareSong = new BSONObject();
        shareSong.put("__type", "Pointer");
        shareSong.put("className", "ShareSong");
        shareSong.put("objectId", shareObjectId);

        //设置为SharePic
        BSONObject sharePic = new BSONObject();
        sharePic.put("url", url);
        sharePic.put("shareSong", shareSong);
        String result = Bmob.insert("SharePic", sharePic.toString());
        System.out.println("insert Share Pic : " + result);
    }



}
