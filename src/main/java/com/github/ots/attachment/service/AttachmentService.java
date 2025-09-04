package com.github.ots.attachment.service;

import com.github.ots.attachment.dto.SavableAttachmentDto;
import com.github.ots.attachment.mapper.AttachmentMapper;
import com.github.ots.attachment.model.Attachment;
import com.github.ots.attachment.repository.AttachmentRepository;
import com.github.ots.attachment.validator.AttachmentValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final AttachmentMapper attachmentMapper;

    public Attachment saveAttachment(SavableAttachmentDto attachmentDto) {
        AttachmentValidator.validate(attachmentDto);
        var attachment = attachmentMapper.toEntity(attachmentDto);
        return attachmentRepository.save(attachment);
    }

    public Boolean saveMultiPartFile(MultipartFile multipartFile) throws IOException {

        SavableAttachmentDto attachmentDto = new SavableAttachmentDto();
        attachmentDto.setData(multipartFile.getBytes());
        attachmentDto.setMimeType(multipartFile.getContentType());
        attachmentDto.setFileName(multipartFile.getOriginalFilename());

        return saveAttachment(attachmentDto) != null;

    }


}
