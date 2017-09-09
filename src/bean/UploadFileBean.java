package bean;

public class UploadFileBean {

    /**
     * cdn : upyun
     * filename : C:\Users\81256\Desktop\幻化成风.jpg
     * url : http://bmob-cdn-6553.b0.upaiyun.com/2017/09/08/418f74d34079a90c80a3bd6d69f63896.jpg
     */

    private String cdn;
    private String filename;
    private String url;

    public String getCdn() {
        return cdn;
    }

    public void setCdn(String cdn) {
        this.cdn = cdn;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
