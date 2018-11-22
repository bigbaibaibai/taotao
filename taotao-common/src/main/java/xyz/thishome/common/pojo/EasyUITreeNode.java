package xyz.thishome.common.pojo;

import java.util.List;

public class EasyUITreeNode {
    private Long id;
    private String text;
    private String state;

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getId() {

        return id;
    }

    public String getText() {
        return text;
    }

    public String getState() {
        return state;
    }
}
