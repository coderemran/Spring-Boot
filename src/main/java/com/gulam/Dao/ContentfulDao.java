package com.gulam.Dao;

import com.gulam.Entity.Contentful;
import com.gulam.utility.ProcessJsonResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ContentfulDao extends com.gulam.Dao.Contentful {
    //spaceID
    private String spaceID = "3su0kqe2ind7";
    //contentfulURL
    private String contentfulURL = "https://cdn.contentful.com/";
    //accessToken
    private String accessToken = "dad2d59a42cfc7b41dbe6d492025d876cb1829940cd3e3d092a0831e3f4157e0";
    //environement
    private String environment = "master";
    //content type
    //default course
    private String contentType = "promotions";


    @Autowired
    private static Map<String, Contentful> contentEntries;

    static {
        contentEntries = new HashMap<String, Contentful>(){};
    }

    private ContentfulDao(){
        this.setLatestEntries();
    }

    public Collection<Contentful> getAllEntries(){
        this.setLatestEntries();
        return contentEntries.values();
    }

    public Contentful getEntryById(String id){
        this.setLatestEntries();
        return contentEntries.get(id);
    }

    public Contentful getEntryBySlug(String slug){
        return contentEntries.get(slug);
    }

    public Collection<Contentful> getEntryByType(String contentType){
        this.contentType = contentType;
        this.setLatestEntries();
        return contentEntries.values();
    }

    /********************************************/
    /* SetAllentries                            */
    /* @input: null                             */
    /* @return: null                            */
    /********************************************/

    public void setLatestEntries(){
        //assemble url
        String contentfulURL = this.contentfulURL + "spaces/" + this.spaceID + "/environments/"+ environment +"/entries";
        //append access token
        contentfulURL = contentfulURL + "?access_token=" + this.accessToken;
        //get response from contentful
        JSONObject jsonObject = ProcessJsonResponse.getJsonResponse(contentfulURL);
        //all items
        JSONArray itemsArray = jsonObject.getJSONArray("items");

        int courseCounter = 1;

        contentEntries.clear();

        //filter and remove garbage data

        for(int i = 0; i < itemsArray.length(); i++){
            JSONObject contentSys       = new JSONObject(itemsArray.getJSONObject(i).toString());
            JSONObject sysObj           = new JSONObject(contentSys.getJSONObject("sys").toString());
            JSONObject sysFields        = new JSONObject(contentSys.getJSONObject("fields").toString());
            JSONObject contentTypeObj   = new JSONObject(sysObj.getJSONObject("contentType").getJSONObject("sys").toString());

            String contentId            = (String) sysObj.get("id");
            String contentType          = (String) contentTypeObj.get("type");
            String contentTypeID        = (String) contentTypeObj.get("id").toString();

            if(contentTypeID.equals(this.contentType)){
                String title            = sysFields.has("title") ? (String) sysFields.get("title") : null;
                String link             = sysFields.has("link") ? (String) sysFields.get("link") : null;
                String slug             = sysFields.has("slug") ? (String) sysFields.get("slug") : null;
                String shortDescription = sysFields.has("shortDescription") ? (String) sysFields.get("shortDescription") : null;
                String description      = sysFields.has("description") ? (String) sysFields.get("description") : null;
                String imageSrc = null;
                if(sysFields.has("image")) {
                    String imageId = (String) sysFields.getJSONObject("image").getJSONObject("sys").get("id");
                    imageSrc = this.getImageByID(imageId);
                }
                //add to content entry array
                contentEntries.put(contentId, new Contentful(courseCounter, contentId, title, slug, shortDescription, description, imageSrc, link));
                courseCounter++;
            }
        }
    }
}
