package online.book.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NoticeDto {
    private String noticeMessage;
    private String noticeDate;
    private int count;
    private int id;
    private String status;
    private String exists;


    public NoticeDto(int id, String noticeMessage, String date, String status, int count) {
        this.id = id;
        this.noticeMessage = noticeMessage;
        this.noticeDate = date;
        this.status = status;
        this.count = count;
    }

    public NoticeDto(int count) {
        this.count = count;
    }


    public NoticeDto(boolean exists) {
        this.exists = String.valueOf(exists);
    }
}
