package xyz.thishome.common.pojo;

/**
 * 异步tree封装结果集
 */
public class EasyUITreeNode {
    /**
     * id 节点id
     */
    private Long id;
    /**
     * text 显示文本
     */
    private String text;
    /**
     * 状态，如果为父节点，则值为"closed"，如果为叶子节点，则值为"open"
     */
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
