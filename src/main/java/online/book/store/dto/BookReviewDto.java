package online.book.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.builder.AbstractReviewBuilder;
import online.book.store.entity.BookReview;

import java.lang.reflect.Field;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookReviewDto extends AbstractReviewBuilder {

    private String review;
    private String score;
    private String isbn;
    private String sessionid;
    private String author;

    @Override
    public AbstractReviewBuilder builder() {
        return this;
    }

    @Override
    public AbstractReviewBuilder review(String review) {
        this.review = review;
        return this;
    }

    @Override
    public AbstractReviewBuilder score(String score) {
        this.score = score;
        return this;
    }

    @Override
    public boolean containsNull() {
        Field[] fields = this.getClass().getFields();
        int index = 0;
        while(index != fields.length){
            if(fields[index] == null) return true;
            index ++ ;
        }
    return false;
    }

    @Override
    public BookReview build() {
        if(!containsNull()){
            return new BookReview(this.review, Integer.parseInt(this.score));
        }
    return null;
    }

    public BookReview doBookReviewBuilder() {
        return builder().review(this.review).
                score(this.score).build();
    }
}
