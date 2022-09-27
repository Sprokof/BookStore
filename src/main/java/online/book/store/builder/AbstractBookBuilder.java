package online.book.store.builder;

import online.book.store.entity.Book;
import online.book.store.entity.Category;

import java.io.File;
import java.util.List;

public abstract class AbstractBookBuilder {

   public AbstractBookBuilder builder(){
        return this;
    }

   public AbstractBookBuilder bookImage(String imageName){
       return null;
   }

   public AbstractBookBuilder isbn(String isbn){
       return null;
   }

   public AbstractBookBuilder title(String title){
       return null;
   }

   public AbstractBookBuilder price(String price){
       return null;
   }

   public AbstractBookBuilder yearPub(String yearPub){
       return null;
   }

   public AbstractBookBuilder publisher(String publisher){
       return null;
   }

   public AbstractBookBuilder subject(String subject){
       return null;
   }

   public AbstractBookBuilder availableCopies(String availableCopies){
       return null;
   }

   public AbstractBookBuilder format(String authors){
       return null;
   }

   public AbstractBookBuilder authors(String authors){
       return null;
   }

   public AbstractBookBuilder description(String description){
       return null;
   }

   public AbstractBookBuilder categories(String categories){ return null; }


   public boolean containsNull(){
       return false;
   }

   public Book build(){
       return null;
   }
}
