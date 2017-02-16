package org.revo.Domain;

/**
 * Created by ashraf on 31/01/17.
 */
public class SearchCriteria {
    private String search;
    private Page page;

    public SearchCriteria() {
    }

    public SearchCriteria(String search, Page page) {
        this.search = search;
        this.page = page;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
