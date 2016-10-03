/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ti.scsgo.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author a0284021
 */
public class FileUploadServiceTest {

    /**
     * Test of saveStream method, of class FileUploadService.
     */
    @Test
    public void testSaveStream() throws Exception {
        final MockMultipartFile mockMultipartFile = new MockMultipartFile("TEST", "TEST FILE", "UTF", new FileInputStream("Test File"));
        MultipartFile file = mockMultipartFile;
        FileUploadService fs = new FileUploadService();
        fs.saveStream(file);
        assertTrue(Files.exists(Paths.get(FileUploadService.TMP_DIR)));
        assertTrue(Files.exists(Paths.get(FileUploadService.TMP_DIR, "TEST FILE")));

    }

    @Test
    public void testClearTmp() throws IOException {
        FileUploadService fs = new FileUploadService();
        fs.clearTmp();
        assertFalse(Files.exists(Paths.get(FileUploadService.TMP_DIR)));
    }

}
