package util;


public class Where {

    private static String EMPTY_STRING = new String();


    private static boolean isNumber(Object obj){
        return obj instanceof Integer ||
                obj instanceof Float ||
                obj instanceof Double;
    }


    public static String or(Object where1, Object where2){
        return "{\"$or\":["+where1.toString()+","+where2.toString()+"]}";
    }


    public static String and(Object where1, Object where2){
        return "{\"$and\":["+where1.toString()+","+where2.toString()+"]}";
    }


    public static String less(Object value){
        if(isNumber(value)){
            return "{\"$lt\":"+value+"}";
        }
        return "{\"$lt\":\""+value.toString()+"\"}";
    }



    public static String lessEqual(Object value){
        if(isNumber(value)){
            return "{\"$lte\":"+value+"}";
        }
        return "{\"$lte\":\""+value.toString()+"\"}";
    }


    public static String greate(Object value){
        if(isNumber(value)){
            return "{\"$gt\":"+value+"}";
        }
        return "{\"$gt\":\""+value.toString()+"\"}";
    }


    public static String greateEqual(Object value){
        if(isNumber(value)){
            return "{\"$gte\":"+value+"}";
        }
        return "{\"$gte\":\""+value.toString()+"\"}";
    }


    public static String notEqual(Object value){
        if(isNumber(value)){
            return "{\"$ne\":"+value+"}";
        }
        return "{\"$ne\":\""+value.toString()+"\"}";
    }


    public static String in(int[] value){
        String result = EMPTY_STRING;
        for(int i=0; i<value.length; i++){
            result = i == value.length-1 ? String.valueOf(result + value[i]) : result + value[i]+",";
        }
        return "{\"$in\":["+result+"]}";
    }

    public static String in(String[] value){
        String result = EMPTY_STRING;
        for(int i=0; i<value.length; i++){
            result = i == value.length-1 ? result +  "\"" + value[i] +"\"" : result + "\"" + value[i]+"\",";
        }
        return "{\"$in\":["+result+"]}";
    }

    public static String in(String value){
        return "{\"$in\":["+value+"]}";
    }



    public static String notIn(int[] value){
        String result = EMPTY_STRING;
        for(int i=0; i<value.length; i++){
            result = i == value.length-1 ? String.valueOf(result + value[i]) : result + value[i]+",";
        }
        return "{\"$nin\":["+result+"]}";
    }

    public static String notIn(String[] value){
        String result = EMPTY_STRING;
        for(int i=0; i<value.length; i++){
            result = i == value.length-1 ? result +  "\"" + value[i] +"\"" : result + "\"" + value[i]+"\",";
        }
        return "{\"$nin\":["+result+"]}";
    }

    public static String notIn(String value){
        return "{\"$nin\":["+value+"]}";
    }

    public static String exists(boolean value){
        return "{\"$exists\":"+value+"}";
    }


    public static String all(String value){
        return "{\"$all\":["+value+"]}";
    }



    public static String included(boolean greatEqual, int greatValue, boolean lessEqual, int lessValue){
        return included(greatEqual, String.valueOf(greatValue), lessEqual, String.valueOf(lessValue));
    }

    public static String included(boolean greatEqual, String greatValue, boolean lessEqual, String lessValue){
        String op1;
        String op2;
        op1 = greatEqual ? "\"$gte\"" : "\"$gt\"";
        op2 = lessEqual ? "\"$lte\"" : "\"$lt\"";
        return "{"+op1+":"+greatValue+","+op2+":"+lessValue+"}";
    }



    public static String regex(String regexValue){
        String op = "\"$regex\"";
        return "{"+op+":\""+regexValue+"\"}";
    }



    public static String like(String value) {
        return regex(".*" + value + ".*");
    }

}