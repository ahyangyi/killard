package com.killard.web.multipart;

import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MemoryMultipartFile implements MultipartFile {

    private FileItemStream item;

    private long size = -1;

    byte[] bytes;

    public MemoryMultipartFile(FileItemStream item) throws IOException {
        this.item = item;
        getBytes();
    }

    public String getName() {
        return item.getName();
    }

    public String getOriginalFilename() {
        return item.getFieldName();
    }

    public String getContentType() {
        return item.getContentType();
    }

    public boolean isEmpty() {
        return false;
    }

    public long getSize() {
        if (size > 0) {
            try {
                return getBytes().length;
            } catch (IOException e) {
                throw new MultipartException("Something went wrong here", e);
            }
        }
        return size;
    }

    public byte[] getBytes() throws IOException {
        if (bytes == null) {
            bytes = IOUtils.toByteArray(item.openStream());
        }
        return bytes;
    }

    public InputStream getInputStream() throws IOException {
        throw new UnsupportedOperationException("This operation is not supported.");
    }

    public void transferTo(File dest) throws IOException, IllegalStateException {
        throw new UnsupportedOperationException("This operation is not supported.");
    }
}