package online.book.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDto {
    private String noticeMessage;
    private String noticeDate;
    private int count;


    public NoticeDto(String noticeMessage, String noticeDate) {
        this.noticeMessage = noticeMessage;
        this.noticeDate = noticeDate;
    }

    public NoticeDto(int count) {
        this.count = count;
    }


}
