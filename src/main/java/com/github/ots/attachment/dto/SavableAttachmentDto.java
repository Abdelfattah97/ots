package com.github.ots.attachment.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SavableAttachmentDto {

    private String fileName;
    private String mimeType;
    private byte[] data;

    public int getSize(){
        return data.length;
    }

}
