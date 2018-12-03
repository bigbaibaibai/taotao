package xyz.thishome.rest.pojo;

public class CartItem {
    private Long id;
    private Integer num;
    private Long price;
    private String image;
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getImages() {
        if (image != null) {
            String[] split = image.split(",");
            return split;
        }
        return null;
    }

    public void setImages(String[] images) {

    }
}
