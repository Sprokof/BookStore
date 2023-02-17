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
    private int id;

    private String status;


    public NoticeDto(int id, String noticeMessage, String noticeDate, String status) {
        this.id = id;
        this.noticeMessage = noticeMessage;
        this.noticeDate = noticeDate;
        this.status = status;
    }

    public NoticeDto(int count) {
        this.count = count;
    }


}
