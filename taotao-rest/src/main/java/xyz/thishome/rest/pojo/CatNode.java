package xyz.thishome.rest.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CatNode {

    @JsonProperty("n")
    private String name;
    @JsonProperty("u")
    private String url;
    @JsonProperty("i")
    private List<?> items;

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }

    public String getName() {

        return name;
    }

    public String getUrl() {
        return url;
    }

    public List<?> getItems() {
        return items;
    }
}
