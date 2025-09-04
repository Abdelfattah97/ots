package com.github.ots.attachment.mapper;

import com.github.ots.attachment.dto.SavableAttachmentDto;
import com.github.ots.attachment.model.Attachment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "fileType", source = "mimeType")
    Attachment toEntity(SavableAttachmentDto attachmentDto);

}
