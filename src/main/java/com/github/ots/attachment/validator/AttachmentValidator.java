package com.github.ots.attachment.validator;

import com.github.ots.attachment.dto.SavableAttachmentDto;
import com.github.ots.common.error.exception.ValidationException;

import java.util.Set;

public class AttachmentValidator{

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5M

    private static final Set<String> allowedFileTypes =
            Set.of(
                    "xlsx",
                    "xls",
                    "pdf",
                    "jpg",
                    "jpeg",
                    "png"
            );

    public static void validate(SavableAttachmentDto attachmentDto) {
        if (attachmentDto == null) {
            throw new ValidationException("Attachment is null");
        }
        if (attachmentDto.getSize() <= 0) {
            throw new ValidationException("File is empty");
        }
        if (attachmentDto.getSize() > MAX_FILE_SIZE) {
            throw new ValidationException("File is too big");
        }
        if (!allowedFileTypes.contains(attachmentDto.getMimeType())) {
            throw new ValidationException("Unsupported file type: " + attachmentDto.getMimeType());
        }

    }

}
