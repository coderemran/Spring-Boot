package com.gulam.Service;

import com.gulam.Dao.ContentfulDao;
import com.gulam.Entity.Contentful;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ContentfulService {
    @Autowired
    private ContentfulDao contentfulDao;

    public Collection<Contentful> getAllEntries(){
        return this.contentfulDao.getAllEntries();
    }

    public Contentful getEntryById(String id){
        return this.contentfulDao.getEntryById(id);
    }

    public Contentful getEntryBySlug(String slug){
        return this.contentfulDao.getEntryBySlug(slug);
    }

    public Collection<Contentful> getEntryByType(String contentType){
        return this.contentfulDao.getEntryByType(contentType);
    }
}
