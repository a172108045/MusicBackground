package util;



        import java.io.BufferedReader;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import java.io.OutputStreamWriter;
        import java.io.PrintWriter;
        import java.io.UnsupportedEncodingException;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.net.URLEncoder;

        import javax.net.ssl.HttpsURLConnection;
        import javax.net.ssl.SSLContext;

public class Bmob {

    private static boolean IS_INIT = false;
    private static int TIME_OUT = 10000;

    private static String STRING_EMPTY = "";
    private static String APP_ID = STRING_EMPTY;
    private static String REST_API_KEY = STRING_EMPTY;
    private static String MASTER_KEY = STRING_EMPTY;

    private static final String BMOB_APP_ID_TAG = "X-Bmob-Application-Id";
    private static final String BMOB_REST_KEY_TAG = "X-Bmob-REST-API-Key";
    private static final String BMOB_MASTER_KEY_TAG = "X-Bmob-Master-Key";
    private static final String BMOB_EMAIL_TAG = "X-Bmob-Email";
    private static final String BMOB_PASSWORD_TAG = "X-Bmob-Password";
    private static final String CONTENT_TYPE_TAG = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";

    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_PUT = "PUT";
    private static final String METHOD_DELETE = "DELETE";

    private static final String UTF8 = "UTF-8";
    private static final String CHAR_RISK = ":";

    public static final String MSG_NOT_FOUND = "Not Found";
    public static final String MSG_FILE_NOT_FOUND = "file Not Found";
    public static final String MSG_ERROR = "Error";
    public static final String MSG_UNREGISTERED = "Unregistered";

    /**
     * 鏄惁鍒濆鍖朆mob
     *
     * @return 鍒濆鍖栫粨鏋�
     */
    public static boolean isInit() {
        return IS_INIT;
    }


    public static boolean initBmob(String appId, String restKey) {
        return initBmob(appId, restKey, 10000);
    }


    public static boolean initBmob(String appId, String restKey, int timeout) {
        APP_ID = appId;
        REST_API_KEY = restKey;
        if (!APP_ID.equals(STRING_EMPTY) && !REST_API_KEY.equals(STRING_EMPTY)) {
            IS_INIT = true;
        }
        if (timeout > 1000 && timeout < 20000) {
            TIME_OUT = timeout;
        }
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, null, null);
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            IS_INIT = false;
        }
        return isInit();
    }


    public static void initMaster(String masterKey) {
        MASTER_KEY = masterKey;
    }


    public static String findAll(String tableName) {
        return find(tableName, STRING_EMPTY);
    }


    public static String findAll(String tableName, String where) {
        return find(tableName, where, STRING_EMPTY);
    }


    public static String findOne(String tableName, String objectId) {
        String result = STRING_EMPTY;
        if (isInit()) {
            HttpURLConnection conn = null;
            String mURL = "https://api.bmob.cn/1/classes/" + tableName + "/"
                    + objectId;
            try {
                conn = connectionCommonSetting(conn, new URL(mURL), METHOD_GET);
                conn.connect();
                result = getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = MSG_NOT_FOUND + CHAR_RISK + "(findOne)"
                        + e.getMessage();
            } catch (Exception e) {
                result = MSG_ERROR + CHAR_RISK + "(findOne)" + e.getMessage();
            }
        } else {
            result = MSG_UNREGISTERED;
        }
        return result;
    }


    public static String find(String tableName, int limit) {
        return find(tableName, "{}", 0, limit, STRING_EMPTY);
    }


    public static String find(String tableName, String where, int limit) {
        return find(tableName, where, 0, limit, STRING_EMPTY);
    }


    public static String findColumns(String tableName, String keys,
                                     String where, int limit) {
        return findColumns(tableName, keys, where, 0, limit, STRING_EMPTY);
    }


    public static String find(String tableName, int skip, int limit) {
        return find(tableName, "{}", skip, limit, STRING_EMPTY);
    }

    public static String find(String tableName, String where, int skip,
                              int limit) {
        return find(tableName, where, skip, limit, STRING_EMPTY);
    }


    public static String findColumns(String tableName, String keys,
                                     String where, int skip, int limit) {
        return findColumns(tableName, keys, where, skip, limit, STRING_EMPTY);
    }


    public static String find(String tableName, String order) {
        return find(tableName, "{}", 0, 1000, order);
    }


    public static String find(String tableName, String where, String order) {
        return find(tableName, where, 0, 1000, order);
    }


    public static String findColumns(String tableName, String keys,
                                     String where, String order) {
        return findColumns(tableName, keys, where, 0, 1000, order);
    }


    public static String find(String tableName, int limit, String order) {
        return find(tableName, "{}", 0, limit, order);
    }


    public static String find(String tableName, String where, int limit,
                              String order) {
        return find(tableName, where, 0, limit, order);
    }


    public static String findColumns(String tableName, String keys,
                                     String where, int limit, String order) {
        return findColumns(tableName, keys, where, 0, limit, order);
    }


    public static String find(String tableName, String where, int skip,
                              int limit, String order) {
        return findColumns(tableName, STRING_EMPTY, where, skip, limit, order);
    }


    public static String findColumns(String tableName, String keys,
                                     String where, int skip, int limit, String order) {
        String result = STRING_EMPTY;
        if (isInit()) {
            HttpURLConnection conn = null;
            skip = skip < 0 ? 0 : skip;
            limit = limit < 0 ? 0 : limit;
            limit = limit > 1000 ? 1000 : limit;
            where = where.equals(STRING_EMPTY) ? "{}" : where;
            String mURL = "https://api.bmob.cn/1/classes/" + tableName
                    + "?where=" + urlEncoder(where) + "&limit=" + limit
                    + "&skip=" + skip + "&order=" + order + "&keys=" + keys;

            try {
                conn = connectionCommonSetting(conn, new URL(mURL), METHOD_GET);
                conn.connect();
                result = getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = MSG_NOT_FOUND + CHAR_RISK + "(findColumns)"
                        + e.getMessage();
            } catch (Exception e) {
                result = MSG_ERROR + CHAR_RISK + "(findColumns)"
                        + e.getMessage();
            }
        } else {
            result = MSG_UNREGISTERED;
        }
        return result;
    }


    public static String findBQL(String BQL) {
        return findBQL(BQL, STRING_EMPTY);
    }

    public static String findBQL(String BQL, String value) {
        String result = STRING_EMPTY;
        if (isInit()) {
            HttpURLConnection conn = null;
            BQL = urlEncoder(BQL) + "&values=[" + urlEncoder(value) + "]";
            String mURL = "https://api.bmob.cn/1/cloudQuery?bql=" + BQL;

            try {
                conn = connectionCommonSetting(conn, new URL(mURL), METHOD_GET);
                conn.connect();
                result = getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = MSG_NOT_FOUND + CHAR_RISK + "(findBQL)"
                        + e.getMessage();
            } catch (Exception e) {
                result = MSG_ERROR + CHAR_RISK + "(findBQL)" + e.getMessage();
            }
        } else {
            result = MSG_UNREGISTERED;
        }
        return result;
    }


    public static String getServerTime() {
        String result = STRING_EMPTY;
        if (isInit()) {
            HttpURLConnection conn = null;
            String mURL = "https://api.bmob.cn/1/timestamp/";
            try {
                conn = connectionCommonSetting(conn, new URL(mURL), METHOD_GET);
                conn.connect();
                result = getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = MSG_NOT_FOUND + CHAR_RISK + "(getServerTime)"
                        + e.getMessage();
            } catch (Exception e) {
                result = MSG_ERROR + CHAR_RISK + "(getServerTime)"
                        + e.getMessage();
            }
        } else {
            result = MSG_UNREGISTERED;
        }
        return result;
    }


    public static int count(String tableName) {
        return count(tableName, "{}");
    }


    public static int count(String tableName, String where) {
        String result = STRING_EMPTY;
        if (isInit()) {
            HttpURLConnection conn = null;
            String mURL = "https://api.bmob.cn/1/classes/" + tableName
                    + "?where=" + urlEncoder(where) + "&count=1&limit=0";
            try {
                conn = connectionCommonSetting(conn, new URL(mURL), METHOD_GET);
                conn.connect();
                result = getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = MSG_NOT_FOUND + CHAR_RISK + "(count)" + e.getMessage();
                System.err.println("Warn: " + result);
            } catch (Exception e) {
                result = MSG_ERROR + CHAR_RISK + "(count)" + e.getMessage();
                System.err.println("Warn: " + result);
            }
        } else {
            result = MSG_UNREGISTERED;
            System.err.println("Warn: " + result);
        }
        int count = 0;
        if (result.contains(MSG_NOT_FOUND) || result.contains(MSG_ERROR)
                || result.equals(MSG_UNREGISTERED)) {
            return count;
        } else {
            if (result.contains("count")) {
                count = Integer.valueOf(result.replaceAll("[^0-9]",
                        STRING_EMPTY));
            }
        }
        return count;
    }

    public static String update(String tableName, String objectId,
                                String paramContent) {
        String result = STRING_EMPTY;
        if (isInit()) {
            HttpURLConnection conn = null;
            String mURL = "https://api.bmob.cn/1/classes/" + tableName + "/"
                    + objectId;
            try {
                conn = connectionCommonSetting(conn, new URL(mURL), METHOD_PUT);
                conn.setDoOutput(true);
                conn.connect();
                printWriter(conn, paramContent);
                result = getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = MSG_NOT_FOUND + CHAR_RISK + "(update)"
                        + e.getMessage();
            } catch (Exception e) {
                result = MSG_ERROR + CHAR_RISK + "(update)" + e.getMessage();
            }
        } else {
            result = MSG_UNREGISTERED;
        }
        return result;
    }


    public static String insert(String tableName, String paramContent) {
        String result = STRING_EMPTY;
        if (isInit()) {
            HttpURLConnection conn = null;
            String mURL = "https://api.bmob.cn/1/classes/" + tableName;
            try {
                conn = connectionCommonSetting(conn, new URL(mURL), METHOD_POST);
                conn.setDoOutput(true);
                conn.connect();
                printWriter(conn, paramContent);
                result = getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = MSG_NOT_FOUND + CHAR_RISK + "(insert)"
                        + e.getMessage();
            } catch (Exception e) {
                result = MSG_ERROR + CHAR_RISK + "(insert)" + e.getMessage();
            }
        } else {
            result = MSG_UNREGISTERED;
        }
        return result;
    }


    public static String delete(String tableName, String objectId) {
        String result = STRING_EMPTY;
        if (isInit()) {
            HttpURLConnection conn = null;
            String mURL = "https://api.bmob.cn/1/classes/" + tableName + "/"
                    + objectId;
            try {
                conn = connectionCommonSetting(conn, new URL(mURL),
                        METHOD_DELETE);
                conn.connect();
                result = getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = MSG_NOT_FOUND + CHAR_RISK + "(delete)"
                        + e.getMessage();
            } catch (Exception e) {
                result = MSG_ERROR + CHAR_RISK + "(delete)" + e.getMessage();
            }
        } else {
            result = MSG_UNREGISTERED;
        }
        return result;
    }


    public static String findPayOrder(String payId) {
        String result = STRING_EMPTY;
        if (isInit()) {
            HttpURLConnection conn = null;
            String mURL = "https://api.bmob.cn/1/pay/" + payId;
            try {
                conn = connectionCommonSetting(conn, new URL(mURL), METHOD_GET);
                conn.connect();
                result = getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = MSG_NOT_FOUND + CHAR_RISK + "(findPayOrder)"
                        + e.getMessage();
            } catch (Exception e) {
                result = MSG_ERROR + CHAR_RISK + "(findPayOrder)"
                        + e.getMessage();
            }
        } else {
            result = MSG_UNREGISTERED;
        }
        return result;
    }


    public static String pushMsg(String data) {
        String result = STRING_EMPTY;
        if (isInit()) {
            HttpURLConnection conn = null;
            String mURL = "https://api.bmob.cn/1/push";
            try {
                conn = connectionCommonSetting(conn, new URL(mURL), METHOD_POST);
                conn.setDoOutput(true);
                conn.connect();
                printWriter(conn, data);
                result = getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = MSG_NOT_FOUND + CHAR_RISK + "(pushMsg)"
                        + e.getMessage();
            } catch (Exception e) {
                result = MSG_ERROR + CHAR_RISK + "(pushMsg)" + e.getMessage();
            }
        } else {
            result = MSG_UNREGISTERED;
        }
        return result;
    }


    public static String callFunction(String funcName, String paramContent) {
        String result = STRING_EMPTY;
        if (isInit()) {
            HttpURLConnection conn = null;
            String mURL = "https://api.bmob.cn/1/functions/" + funcName;
            try {
                conn = connectionCommonSetting(conn, new URL(mURL), METHOD_POST);
                conn.setDoOutput(true);
                conn.connect();
                printWriter(conn, paramContent);
                result = getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = MSG_NOT_FOUND + CHAR_RISK + "(callFunction)"
                        + e.getMessage();
            } catch (Exception e) {
                result = MSG_ERROR + CHAR_RISK + "(callFunction)"
                        + e.getMessage();
            }
        } else {
            result = MSG_UNREGISTERED;
        }
        return result;
    }


    public static String uploadFile(String file) {
        String result = STRING_EMPTY;
        if (isInit()) {
            HttpURLConnection conn = null;
            // 鑾峰彇鏂囦欢鍚�
            String fileName = file.trim();
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
            String mURL = "https://api.bmob.cn/2/files/" + fileName;
            try {
                FileInputStream fis = new FileInputStream(file);
                conn = connectionCommonSetting(conn, new URL(mURL), METHOD_POST);
                conn.setDoOutput(true);
                conn.connect();
                // 涓€娆¤澶氫釜瀛楄妭
                byte[] tempbytes = new byte[1];
                OutputStream os = conn.getOutputStream();
                while ((fis.read(tempbytes)) != -1) {
                    os.write(tempbytes);
                }
                os.flush();
                os.close();
                fis.close();
                result = getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = MSG_FILE_NOT_FOUND + CHAR_RISK + e.getMessage();
            } catch (Exception e) {
                result = MSG_ERROR + CHAR_RISK + e.getMessage();
            }
        } else {
            result = MSG_UNREGISTERED;
        }
        return result;
    }


    public static String login(String user, String passwd) {
        String result = STRING_EMPTY;
        HttpURLConnection conn = null;
        String mURL = "https://api.bmob.cn/1/apps";
        try {
            conn = connectionLoginSetting(conn, new URL(mURL), METHOD_GET,
                    user, passwd);
            conn.connect();
            result = getResultFromConnection(conn);
            conn.disconnect();
        } catch (FileNotFoundException e) {
            result = MSG_NOT_FOUND + CHAR_RISK + "(Login)" + e.getMessage();
        } catch (Exception e) {
            result = MSG_ERROR + CHAR_RISK + "(Login)" + e.getMessage();
        }
        return result;
    }


    public static String findAllTables() {
        String result = STRING_EMPTY;
        if (isInit() && !MASTER_KEY.equals(STRING_EMPTY)) {
            HttpURLConnection conn = null;
            String mURL = "https://api.bmob.cn/1/schemas";
            try {
                conn = connectionCommonSetting(conn, new URL(mURL), METHOD_GET);
                conn.connect();
                result = getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = MSG_NOT_FOUND + CHAR_RISK + "(findAllTables)"
                        + e.getMessage();
            } catch (Exception e) {
                result = MSG_ERROR + CHAR_RISK + "(findAllTables)"
                        + e.getMessage();
            }
        } else {
            result = MSG_UNREGISTERED;
        }
        return result;
    }



    public static String webPay(String payInfo) {
        String result = STRING_EMPTY;
        if (isInit()) {
            HttpURLConnection conn = null;
            String mURL = "https://api.bmob.cn/1/webpay";
            try {
                conn = connectionCommonSetting(conn, new URL(mURL), METHOD_POST);
                conn.setDoOutput(true);
                conn.connect();
                printWriter(conn, payInfo);
                result = getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = MSG_NOT_FOUND + CHAR_RISK + "(webPay)"
                        + e.getMessage();
            } catch (Exception e) {
                result = MSG_ERROR + CHAR_RISK + "(webPay)" + e.getMessage();
            }
        } else {
            result = MSG_UNREGISTERED;
        }
        return result;
    }

    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }


	public static String whereOr(String where1, String where2) {
		return "{\"$or\":[" + where1 + "," + where2 + "]}";
	}


	public static String whereAnd(String where1, String where2) {
		return "{\"$and\":[" + where1 + "," + where2 + "]}";
	}


	public static String whereLess(int value) {
		return "{\"$lt\":" + value + "}";
	}


	public static String whereLess(String value) {
		return "{\"$lt\":" + value + "}";
	}


	public static String whereLessEqual(int value) {
		return "{\"$lte\":" + value + "}";
	}


	public static String whereLessEqual(String value) {
		return "{\"$lte\":" + value + "}";
	}


	public static String whereGreate(int value) {
		return "{\"$gt\":" + value + "}";
	}


	public static String whereGreate(String value) {
		return "{\"$gt\":" + value + "}";
	}


	public static String whereGreateEqual(int value) {
		return "{\"$gte\":" + value + "}";
	}


	public static String whereGreateEqual(String value) {
		return "{\"$gte\":" + value + "}";
	}


	public static String whereNotEqual(int value) {
		return "{\"$ne\":" + value + "}";
	}


	public static String whereNotEqual(String value) {
		return "{\"$ne\":" + value + "}";
	}


	public static String whereIn(int[] value) {
		String result = STRING_EMPTY;
		for (int i = 0; i < value.length; i++) {
			result = i == value.length - 1 ? String.valueOf(result + value[i])
					: result + value[i] + ",";
		}
		return "{\"$in\":[" + result + "]}";
	}


	public static String whereIn(String[] value) {
		String result = STRING_EMPTY;
		for (int i = 0; i < value.length; i++) {
			result = i == value.length - 1 ? result + "\"" + value[i] + "\""
					: result + "\"" + value[i] + "\",";
		}
		return "{\"$in\":[" + result + "]}";
	}


	public static String whereIn(String value) {
		return "{\"$in\":[" + value + "]}";
	}


	public static String whereNotIn(int[] value) {
		String result = STRING_EMPTY;
		for (int i = 0; i < value.length; i++) {
			result = i == value.length - 1 ? String.valueOf(result + value[i])
					: result + value[i] + ",";
		}
		return "{\"$nin\":[" + result + "]}";
	}


	public static String whereNotIn(String[] value) {
		String result = STRING_EMPTY;
		for (int i = 0; i < value.length; i++) {
			result = i == value.length - 1 ? result + "\"" + value[i] + "\""
					: result + "\"" + value[i] + "\",";
		}
		return "{\"$nin\":[" + result + "]}";
	}


	public static String whereNotIn(String value) {
		return "{\"$nin\":[" + value + "]}";
	}


	public static String whereExists(boolean value) {
		return "{\"$exists\":" + value + "}";
	}


	public static String whereAll(String value) {
		return "{\"$all\":[" + value + "]}";
	}

	public static String whereIncluded(boolean greatEqual, int greatValue,
			boolean lessEqual, int lessValue) {
		return whereIncluded(greatEqual, String.valueOf(greatValue), lessEqual,
				String.valueOf(lessValue));
	}


	public static String whereIncluded(boolean greatEqual, String greatValue,
			boolean lessEqual, String lessValue) {
		String op1;
		String op2;
		op1 = greatEqual ? "\"$gte\"" : "\"$gt\"";
		op2 = lessEqual ? "\"$lte\"" : "\"$lt\"";
		return "{" + op1 + ":" + greatValue + "," + op2 + ":" + lessValue + "}";
	}


	public static String whereRegex(String regexValue) {
		String op = "\"$regex\"";
		return "{" + op + ":\"" + regexValue + "\"}";
	}


	public static String whereLike(String value) {
		return whereRegex(".*" + value + ".*");
	}

    public static int getTimeout() {
        return TIME_OUT;
    }

    public static void setTimeout(int timeout) {
        TIME_OUT = timeout;
    }

    private static void printWriter(HttpURLConnection conn, String paramContent)
            throws UnsupportedEncodingException, IOException {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(
                conn.getOutputStream(), UTF8));
        out.write(paramContent);
        out.flush();
        out.close();
    }

    private static String getResultFromConnection(HttpURLConnection conn)
            throws UnsupportedEncodingException, IOException {
        StringBuffer result = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                conn.getInputStream(), UTF8));
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();
        return result.toString();
    }

    private static HttpURLConnection connectionLoginSetting(
            HttpURLConnection conn, URL url, String method, String user,
            String passwd) throws IOException {
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setReadTimeout(TIME_OUT);

        conn.setUseCaches(false);
        conn.setInstanceFollowRedirects(true);

        conn.setRequestProperty(BMOB_EMAIL_TAG, user);
        conn.setRequestProperty(BMOB_PASSWORD_TAG, passwd);
        conn.setRequestProperty(CONTENT_TYPE_TAG, CONTENT_TYPE_JSON);
        return conn;
    }

    private static HttpURLConnection connectionCommonSetting(
            HttpURLConnection conn, URL url, String method) throws IOException {
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setReadTimeout(TIME_OUT);

        conn.setUseCaches(false);
        conn.setInstanceFollowRedirects(true);

        conn.setRequestProperty(BMOB_APP_ID_TAG, APP_ID);
        conn.setRequestProperty(BMOB_REST_KEY_TAG, REST_API_KEY);
        if (!MASTER_KEY.equals(STRING_EMPTY)) {
            conn.setRequestProperty(BMOB_MASTER_KEY_TAG, MASTER_KEY);
        }

        conn.setRequestProperty(CONTENT_TYPE_TAG, CONTENT_TYPE_JSON);
        return conn;
    }

    private static String urlEncoder(String str) {
        try {
            return URLEncoder.encode(str, UTF8);
        } catch (UnsupportedEncodingException e1) {
            return str;
        }
    }


    public static String findUsers( String keys,
                                     String where, int skip, int limit, String order) {
        String result = STRING_EMPTY;
        if (isInit()) {
            HttpURLConnection conn = null;
            skip = skip < 0 ? 0 : skip;
            limit = limit < 0 ? 0 : limit;
            limit = limit > 1000 ? 1000 : limit;
            where = where.equals(STRING_EMPTY) ? "{}" : where;
            String mURL = "https://api.bmob.cn/1/users/"
                    + "?where=" + urlEncoder(where) + "&limit=" + limit
                    + "&skip=" + skip + "&order=" + order + "&keys=" + keys;

            try {
                conn = connectionCommonSetting(conn, new URL(mURL), METHOD_GET);
                conn.connect();
                result = getResultFromConnection(conn);
                conn.disconnect();
            } catch (FileNotFoundException e) {
                result = MSG_NOT_FOUND + CHAR_RISK + "(findColumns)"
                        + e.getMessage();
            } catch (Exception e) {
                result = MSG_ERROR + CHAR_RISK + "(findColumns)"
                        + e.getMessage();
            }
        } else {
            result = MSG_UNREGISTERED;
        }
        return result;
    }

}
