package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.AttachmentMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Attachment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AttachmentListService {

    private final AttachmentMapper attachmentMapper;

    public AttachmentListService(AttachmentMapper attachmentMapper) {
        this.attachmentMapper = attachmentMapper;
    }

    public boolean isAttachmentNameAvailable(String fileName, Integer userId) {
        return attachmentMapper.getAttachmentInfoByFileName(fileName, userId) == null;
    }
    public Integer saveUploadedFile(MultipartFile multipartFile, Integer userId) throws IOException {
        Attachment attachment = new Attachment(null, multipartFile.getOriginalFilename(), multipartFile.getContentType(),
                multipartFile.getSize(),userId, multipartFile.getBytes());
        return attachmentMapper.saveAttachment(attachment);
    }

    public List<Attachment>  getAttachmentsByUser(Integer userId) {
        return attachmentMapper.getAttachmentsByUserId(userId);
    }
    public Attachment getUserRequestedImage(Integer fileId) {
        return attachmentMapper.getAttachmentByFileId(fileId);
    }
    public int deleteUploadedAttachment(Integer fileId) {
       return attachmentMapper.deleteAttachmentByFileId(fileId);
    }




}
