package com.gulam.Service;

import com.gulam.Dao.ContentfulPagesDao;
import com.gulam.Entity.ContentfulPages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ContentfulPagesService {
    @Autowired
    private ContentfulPagesDao contentfulPagesDao;

    public Collection<ContentfulPages> getAllPages(){
        return this.contentfulPagesDao.getAllPages();
    }

    public ContentfulPages getPageByName(String name){
        return this.contentfulPagesDao.getPageByName(name);
    }
}
