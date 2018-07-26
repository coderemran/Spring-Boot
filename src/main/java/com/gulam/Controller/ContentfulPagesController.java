package com.gulam.Controller;

import com.gulam.Entity.ContentfulPages;
import com.gulam.Service.ContentfulPagesService;
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
@RequestMapping("/pages")
public class ContentfulPagesController {
    @Autowired
    private ContentfulPagesService ContentfulPagesService;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<ContentfulPages> getAllPages(){
        return ContentfulPagesService.getAllPages();
    }

    @RequestMapping(value="/{name}", method = RequestMethod.GET)
    public ContentfulPages getContentType(@PathVariable("name") String name){
        return ContentfulPagesService.getPageByName(name);
    }
}
