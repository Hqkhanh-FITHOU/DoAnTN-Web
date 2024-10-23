package com.graduate.hou.dto.request;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private Long reponseId;

    private Long reviewId;

    private Long userId;

    private String reponseContent;

    private LocalDateTime createdAt;
}
