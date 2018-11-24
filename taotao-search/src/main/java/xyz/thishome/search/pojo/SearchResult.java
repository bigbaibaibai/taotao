package xyz.thishome.search.pojo;

import java.util.List;

/**
 * 封装了搜索需要返回总信息的pojo
 */
public class SearchResult {

    //商品信息列表
    private List<SearchItem> itemList;
    //总页数
    private Long recordCount;
    //总页码数
    private Long pageCount;
    //当前第几页
    private Long curPage;

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

    public Long getCurPage() {
        return curPage;
    }

    public void setCurPage(Long curPage) {
        this.curPage = curPage;
    }
}
