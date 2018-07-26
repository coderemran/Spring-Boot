package com.gulam.Controller;

import com.gulam.Entity.Contentful;
import com.gulam.Service.ContentfulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@Configuration
@ComponentScan
@EnableAutoConfiguration
@RequestMapping("/entries")
public class ContentfulController {

    @Autowired
    private ContentfulService ContentfulService;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Contentful> getAllEntries(){
        return ContentfulService.getAllEntries();
    }

    @RequestMapping(value="/{contentType}", method = RequestMethod.GET)
    public Collection<Contentful> getContentType(@PathVariable("contentType") String contentType){
        return ContentfulService.getEntryByType(contentType);
    }

    @RequestMapping(value="/{contentType}/{id}", method = RequestMethod.GET)
    public Contentful getEntryById(@PathVariable("contentType") String contentType, @PathVariable("id") String id){
        return ContentfulService.getEntryById(id);
    }

}
