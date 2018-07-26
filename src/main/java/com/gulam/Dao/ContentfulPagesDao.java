package com.gulam.Dao;

import com.gulam.Entity.ContentfulPages;
import com.gulam.utility.ProcessJsonResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ContentfulPagesDao extends Contentful {
    //spaceID
    private String spaceID = "3su0kqe2ind7";
    //contentfulURL
    private String contentfulURL = "https://cdn.contentful.com/";
    //accessToken
    private String accessToken = "dad2d59a42cfc7b41dbe6d492025d876cb1829940cd3e3d092a0831e3f4157e0";
    //environment
    private String environment = "master";
    //content type
    //default course
    private String contentType = "pages";

    @Autowired
    private static Map<String, ContentfulPages> contentfulPages;

    static {
        contentfulPages = new HashMap<String, ContentfulPages>(){};
    }


    public ContentfulPagesDao(){}

    public Collection<ContentfulPages> getAllPages(){
        this.setPages();
        return contentfulPages.values();
    }

    public ContentfulPages getPageByName(String name){
        this.setPages();
        return contentfulPages.get(name);
    }

    public void setPages(){
        //assemble url
        String contentfulURL = this.contentfulURL + "spaces/" + this.spaceID + "/environments/"+ environment +"/entries";
        //append access token
        contentfulURL = contentfulURL + "?access_token=" + this.accessToken + "&content_type=pages";

        //get response from contentful
        JSONObject jsonObject = ProcessJsonResponse.getJsonResponse(contentfulURL);
        //all items
        JSONArray pagesArray = jsonObject.getJSONArray("items");


        for(int i = 0; i < pagesArray.length(); i++){
            JSONObject contentSys       = new JSONObject(pagesArray.getJSONObject(i).toString());
            JSONObject sysObj           = new JSONObject(contentSys.getJSONObject("sys").toString());
            JSONObject sysFields        = new JSONObject(contentSys.getJSONObject("fields").toString());


            String pageId               = (String) sysObj.get("id");
            String pageTitle            = sysFields.has("pagetitle") ? (String) sysFields.get("pagetitle") : null;
            String pageUrl              = sysFields.has("pageUrl") ? (String) sysFields.get("pageUrl") : null;

            //Map top navigation contents
            Map<Integer, Map> topNav    = new HashMap<>();
            if(sysFields.has("topNav")) {
                for (int n = 0; n < sysFields.getJSONArray("topNav").length(); n++) {
                    Map<String, String> topNavItems = new HashMap<>();
                    String navId = sysFields.getJSONArray("topNav").getJSONObject(n).getJSONObject("sys").get("id").toString();
                    JSONObject navContents = this.getEntryDetailsByID(navId);
                    topNavItems.put("id", navId);
                    topNavItems.put("linkType", sysFields.getJSONArray("topNav").getJSONObject(n).getJSONObject("sys").get("linkType").toString());
                    topNavItems.put("type", sysFields.getJSONArray("topNav").getJSONObject(n).getJSONObject("sys").get("type").toString());
                    topNavItems.put("title",navContents.getJSONObject("fields").get("title").toString());
                    topNavItems.put("slug",navContents.getJSONObject("fields").get("slug").toString());
                    if(navContents.getJSONObject("fields").has("link")) {
                        topNavItems.put("link", navContents.getJSONObject("fields").get("link").toString());
                    }
                    if(navContents.getJSONObject("fields").has("order")) {
                        topNavItems.put("order",navContents.getJSONObject("fields").get("order").toString());
                    }
                    topNav.put(n, topNavItems);
                }
            }

            //Map page content
            Map<Integer, Map> pageContents = new HashMap<>();
            if(sysFields.has("contents")){
                for (int c = 0; c < sysFields.getJSONArray("contents").length(); c++) {
                    Map<String, String> contentItems = new HashMap<>();
                    String contentId    = sysFields.getJSONArray("contents").getJSONObject(c).getJSONObject("sys").get("id").toString();
                    JSONObject detailsContent = this.getEntryDetailsByID(contentId);

                    //content type ID
                    String contentTypeID = detailsContent.getJSONObject("sys").getJSONObject("contentType").getJSONObject("sys").get("id").toString();


                    if(contentTypeID.equals("promotions")) {
                        contentItems.put("id", contentId);
                        contentItems.put("title", detailsContent.getJSONObject("fields").get("title").toString());
                        if (detailsContent.getJSONObject("fields").has("description")) {
                            contentItems.put("description", detailsContent.getJSONObject("fields").get("description").toString());
                        }
                        if (detailsContent.getJSONObject("fields").has("shortDescription")) {
                            contentItems.put("shortDescription", detailsContent.getJSONObject("fields").get("shortDescription").toString());
                        }
                        if (sysFields.getJSONArray("contents").getJSONObject(c).getJSONObject("sys").has("linkType")) {
                            contentItems.put("linkType", sysFields.getJSONArray("contents").getJSONObject(c).getJSONObject("sys").get("linkType").toString());
                        }
                        if (sysFields.getJSONArray("contents").getJSONObject(c).getJSONObject("sys").has("type")) {
                            contentItems.put("type", sysFields.getJSONArray("contents").getJSONObject(c).getJSONObject("sys").get("type").toString());
                        }
                    } else {
                        contentItems = this.mapContents(contentTypeID, detailsContent);
                        contentItems.put("id", contentId);
                    }

                    if(detailsContent.getJSONObject("fields").has("image")) {
                        String imageId = detailsContent.getJSONObject("fields").getJSONObject("image").getJSONObject("sys").get("id").toString();
                        String imageURL = this.getAssetsByAssetID(imageId).getJSONObject("fields").getJSONObject("file").get("url").toString();
                        contentItems.put("imageURL", imageURL);
                    }

                    pageContents.put(c, contentItems);
                }
            }


            //add to page map
            contentfulPages.put(pageUrl, new ContentfulPages(pageId, pageUrl, pageTitle, topNav, pageTitle, pageContents));
        }

    }


    //map contents
    public Map mapContents(String contentTypeID, JSONObject detailsContent){
        Map<String, String> detailsMappedContent = new HashMap<>();
        if(contentTypeID.equals("mbhBadge")){
            //fields
            JSONObject badgeFields = detailsContent.getJSONObject("fields");
            detailsMappedContent.put("type", contentTypeID);
            detailsMappedContent.put("title", badgeFields.get("title").toString());
            //map links
            Map<Integer, JSONObject> linkItems = new HashMap<>();
            for(int i=0; i < badgeFields.getJSONArray("mbhLinks").length(); i++){
                JSONObject linkItemDetails = this.getEntryDetailsByID(badgeFields.getJSONArray("mbhLinks").getJSONObject(i).getJSONObject("sys").get("id").toString());
                JSONObject linkItemFields = linkItemDetails.getJSONObject("fields");
                linkItems.put(i, linkItemFields);
            }

            detailsMappedContent.put("badgesLinks", linkItems.toString());
            if(badgeFields.has("subDescription")) {
                detailsMappedContent.put("subDescription", badgeFields.get("subDescription").toString());
            }
        }

        return detailsMappedContent;
    }
}
