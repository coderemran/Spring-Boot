package com.gulam.Entity;

import java.util.Map;
//@Document(indexName = "contentpage", type="default");@
public class ContentfulPages {
    //@Id
    private String pageUrl;

    private String pageId, pageName, pageTitle;

    private Map topNav, contents;

    public ContentfulPages(String pageId, String pageUrl, String pageName, Map<Integer, Map> topNav, String pageTitle, Map<Integer, Map> contents){
        this.pageId     = pageId;
        this.pageName   = pageName;
        this.pageTitle  = pageTitle;
        this.contents   = contents;
        this.topNav     = topNav;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public void setContents(Map contents) {
        this.contents = contents;
    }

    public void setTopNav(Map topNav){
        this.topNav = topNav;
    }

    public String getPageId() {
        return pageId;
    }

    public String getPageName() {
        return pageName;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public Map getContents() {
        return contents;
    }

    public Map getTopNav() {
        return topNav;
    }
}
