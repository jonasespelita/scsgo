package com.ti.scsgo.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author a0284021
 */
@Service
public class FileUploadService {

    protected static final String TMP_DIR = "tmp";

    public void clearTmp() throws IOException {
        Path tmp = Paths.get(TMP_DIR);
        // delete all files in tmp
        if (Files.exists(tmp)) {
            Files.walk(tmp)
                    .map(Path::toFile)
                    .forEach(File::delete);
            Files.deleteIfExists(tmp);
        }
    }

    public void saveStream(MultipartFile file) throws FileNotFoundException, IOException {
        final File newFile = new File(TMP_DIR + "/" + file.getOriginalFilename());
        System.out.println("newFile = " + newFile.getAbsolutePath());

        newFile.getParentFile().mkdirs();
        if (!newFile.exists()) {
            newFile.createNewFile();
        }
        try (
                //  TODO: configuable, have way to make files unique
                final OutputStream io = new FileOutputStream(newFile);
                final InputStream inputStream = file.getInputStream()) {

            IOUtils.copy(inputStream, io);
        }
    }

}
