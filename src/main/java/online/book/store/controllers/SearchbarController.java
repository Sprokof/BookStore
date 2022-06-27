package online.book.store.controllers;


import online.book.store.engines.SearchQuery;
import online.book.store.engines.SiteEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SearchbarController {

    @Autowired
    private @Qualifier("sortEngine")
    SiteEngine sortEngine;



    @ModelAttribute("query")
    public SearchQuery getSearchQuery(){
        return new SearchQuery();
    }


    @PostMapping("/home/book/search")
    public String search(@ModelAttribute("query") SearchQuery query, Model model){

    }
}
