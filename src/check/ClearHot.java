package check;

import base.Base;
import bean.PostList;
import com.google.gson.Gson;
import util.BSONObject;
import util.Bmob;
import util.Where;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//自动巡检：求谱热点-1，
public class ClearHot extends Base {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Calendar calendar = Calendar.getInstance();

    public static void main(String[] args) {
        initBmob();
        PostList postList = getPostList();
        for (int i = 0; i < postList.getResults().size(); i++) {
            PostList.ResultsBean post = postList.getResults().get(i);
            if (post.getObjectId().equals("01f6d95744")) {
                continue;
            }
            check(post);
        }
    }

    //获得帖子列表
    private static PostList getPostList() {
        BSONObject where1 = new BSONObject(Where.greateEqual(1));
        BSONObject where = new BSONObject();
        where.put("index", where1);
        //find方法很多，可自行尝试
        String result = Bmob.find("AskSongPost", where.toString(), 0, 50, "order");
        //  System.out.println(result);
        Gson gson = new Gson();
        return gson.fromJson(result, PostList.class);
    }

    //检测
    private static void check(PostList.ResultsBean post) {
        String updatedAt = post.getUpdatedAt();
        try {
            Date last = format.parse(updatedAt);
            //  System.out.println(last.toString());
            calendar.setTime(last);
            int lastDay = calendar.get(Calendar.DAY_OF_YEAR);
            Date now = new Date();
            calendar.setTime(now);
            int nowDay = calendar.get(Calendar.DAY_OF_YEAR);
            if (nowDay - lastDay >= 7) {
                clear(post, updatedAt);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //-1
    private static void clear(PostList.ResultsBean post, String updatedAt) {
        System.out.println(post.getTitle() + "  " + post.getIndex() + " " + updatedAt);
        int lastIndex = post.getIndex();
        lastIndex--;
        BSONObject index = new BSONObject();
        index.put("index", lastIndex);
        Bmob.update("AskSongPost", post.getObjectId(), index.toString());
    }


}
