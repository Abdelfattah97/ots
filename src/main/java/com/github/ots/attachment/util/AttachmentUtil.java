package com.github.ots.attachment.util;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public class AttachmentUtil {

    public static String getExtension(MultipartFile file) {

        String fileName = Optional.ofNullable(file)
                .map(MultipartFile::getResource)
                .map(Resource::getFilename)
                .orElse(null);
        if (fileName != null) {
            int extensionIndex = fileName.lastIndexOf(".");
            return extensionIndex == -1 ? "" : file.getResource().getFilename().substring(extensionIndex+1);
        }
        return "";
    }

}
