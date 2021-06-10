package sample;

public class Api {
    private String id;
    private String name;
    private String url;
    private String data;

    public Api(String id, String name, String url, String data) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return this.id + " " + this.name;
    }
}
