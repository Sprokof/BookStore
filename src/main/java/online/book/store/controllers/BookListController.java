package online.book.store.controllers;

import online.book.store.engines.SortConfig;
import online.book.store.engines.SortEngine;
import online.book.store.engines.SortTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookListController {

    SortEngine sortEngine = SortEngine.instanceSortEngine();

    @ModelAttribute("sortConfig")
    public SortConfig getSortConfig(){
        return sortEngine.getSortConfig();
    }

    @ModelAttribute("sortTypes")
    public SortTypes[] getSortType(){
        return SortConfig.sortTypes();
    }

    @GetMapping("/home/sort")
    public String sortBookList(@RequestParam("type") String type, Model model){
            SortConfig config = new SortConfig();
            config.setSelectedType(SortTypes.getTypeByName(type));
            sortEngine.setSortConfig(config);
            model.addAttribute("books", sortEngine.getSortBooks());
        return "sortedSearchedBooks";
    }
}
