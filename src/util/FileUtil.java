package util;

import bean.UploadFileBean;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtil {
    //得到shareMap,幻化成风---幻化成风_1.jpg  幻化成风_2.jpg
    public static Map<String, List<String>> getShareMap(String dirName) {
        File dir = new File(dirName);
        Map<String, List<String>> map = new HashMap<>();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (dir.isDirectory()) {
            //保存所有的name，trueName，然后对name进行排序
            for (int i = 0; i < dir.listFiles().length; i++) {
                File file = dir.listFiles()[i];
                String fullName = file.getName();//幻化成风_1.jpg
                String trueName = fullName.split("_")[0];//幻化成风
                if (map.get(trueName) == null) {
                    List<String> temp = new ArrayList<>();
                    temp.add(fullName);
                    map.put(trueName, temp);
                } else {
                    List<String> temp = map.get(trueName);
                    temp.add(fullName);
                    map.put(trueName, temp);
                }
            }
        }
        return map;
    }

    //上传文件,返回url
    public static String uploadPic(String dir,String picName) {
        String path = dir + picName;
        String result = Bmob.uploadFile(path);
        System.out.println("uploadFile: " + result);
        Gson gson = new Gson();
        UploadFileBean uploadFileBean = gson.fromJson(result, UploadFileBean.class);
        return uploadFileBean.getUrl();
    }
}
