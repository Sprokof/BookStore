package online.book.store.builder;

import online.book.store.entity.Book;

import java.io.File;

public abstract class AbstractBookBuilder {

   public AbstractBookBuilder builder(){
        return this;
    }

   public AbstractBookBuilder path(File bookImage){
       return null;
   }

   public AbstractBookBuilder isbn(String isbn){
       return null;
   }

   public AbstractBookBuilder title(String title){
       return null;
   }

   public AbstractBookBuilder price(Double price){
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

   public AbstractBookBuilder availableCopies(int availableCopies){
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


   public boolean containsNull(){
       return false;
   }

   public Book build(){
       return null;
   }
}
