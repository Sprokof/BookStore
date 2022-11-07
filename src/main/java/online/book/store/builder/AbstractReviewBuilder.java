package online.book.store.builder;

import online.book.store.entity.BookReview;

public abstract class AbstractReviewBuilder {

    public AbstractReviewBuilder builder() {
        return this;
    }

    public AbstractReviewBuilder review(String review){
        return this;
    }

    public AbstractReviewBuilder score(String score){
        return this;
    }

    public BookReview build(){
        return null;
    }

    public boolean containsNull () {
        return false;
    }
}
