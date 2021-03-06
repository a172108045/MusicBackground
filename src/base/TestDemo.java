package base;


import base.Base;
import util.BSON;
import util.BSONObject;
import util.Bmob;
import util.Where;

import java.util.Date;

public class TestDemo extends Base {

    public static void main(String[] args) {
        //BSONObject 简单使用
//        CreateClassBSONObject();
//		Search();//查询
//		update();//修改
//		delete();//删除
//		insert();//新增
//		callFunction();//调用云代码
//		findPayOrder();//查询支付订单
//		count();//计数
//		upload();//文件上传
    }


    private static void Search() {
        //where方法很多，可参考官网RestAPI文档
        BSONObject where1 = new BSONObject(Where.less(10));
        BSONObject where = new BSONObject();
        where.put("score", where1);
        //find方法很多，可自行尝试
        String result = Bmob.find("Your TableName", where.toString(), 0, 50, "order");
        Bmob.findBQL("BQL");
        Bmob.findBQL("BQL", "value");
        //可使用JSON 或者 BSON 转换成Object
//		BSONObject bson = new BSONObject(result);
    }

    private static void update() {
        BSONObject bson = new BSONObject();
        bson.put("score", 100);
        //score 修改为100
        Bmob.update("Your TableName", "Your objectId", bson.toString());
    }

    private static void delete() {
        Bmob.delete("Your TableName", "Your objectId");
    }

    private static void insert() {
        BSONObject bson = new BSONObject();
        bson.put("student", "zhangsan");
        Bmob.insert("Your TableName", bson.toString());

    }

    private static void callFunction() {
        BSONObject bson = new BSONObject();
        bson.put("param1", "a");
        bson.put("param2", 0);
        bson.put("param3", true);

        Bmob.callFunction("Your functionName", bson.toString());
    }

    private static void findPayOrder() {
        Bmob.findPayOrder("Your PayId");
    }

    private static void count() {
        BSONObject where = new BSONObject();
        where.put("score", 100);
        Bmob.count("Your TableName", where.toString());
    }

    private static void upload() {
        String res = Bmob.uploadFile("/tmp/myPicture.jpg");
        System.out.println(res);
    }

    private static void CreateClassBSONObject() {
        BSONObject class1, teacher, students;
        BSONObject zhangsan, lisi, wangwu;

        class1 = new BSONObject();
        class1.put("name", "Class 1");
        class1.put("build", new Date());

        teacher = new BSONObject();
        teacher.put("name", "Miss Wang");
        teacher.put("sex", "female");
        teacher.put("age", 30);
        teacher.put("offer", true);

        students = new BSONObject();
        students.put("number", 45).put("boy", 23).put("girl", 22);

        zhangsan = new BSONObject().put("name", "ZhangSan");
        zhangsan.put("age", 12).put("sex", "male");

        lisi = new BSONObject();
        lisi.put("name", "LiSi");
        lisi.put("age", 12);
        lisi.put("sex", "female");

        wangwu = new BSONObject();
        wangwu.put("name", "WangWu");
        wangwu.put("age", 13);
        wangwu.put("sex", "male");

        students.put("student", new BSONObject[]{zhangsan, lisi, wangwu});
        class1.put("teacher", teacher);
        class1.put("students", students);

        BSONObject bson_class = new BSONObject(class1.toString());
        BSON.Log("BSON:" + bson_class + "\n");
        BSON.Log("Build date:" + bson_class.getDate("build"));
        BSON.Log("Is teacher offer? " + bson_class.getBSONObject("teacher").getBoolean("offer"));
        BSON.Log("Teacher's age:" + bson_class.getBSONObject("teacher").getInt("age"));
        BSON.Log("First student's name:" + bson_class.getBSONObject("students").getBSONArray("student")[0].getString("name"));

    }

}