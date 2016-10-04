package com.ti.scsgo.service;

import com.ti.scsgo.domain.EngineRun;
import com.ti.scsgo.utils.EngineRunner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author a0284021
 */
@Service
public class EngineRunService {

    protected static final String TMP_DIR = "tmp";

    Logger LOG = LoggerFactory.getLogger(EngineRunService.class);

    public List<EngineRun> runEngine() throws IOException {

        // get file list
        Path p = Paths.get(TMP_DIR);
        final List<File> files = Files.walk(p)
                .map(f -> f.toFile())
                .peek((f) -> LOG.trace("looking at {}", f))
                .filter(f -> f.isFile())
                .collect(Collectors.toList());

        LOG.debug("Found list of files. {}", files);

        final List<EngineRun> collect = files.stream()
                // run engine for each file
                .map(file -> {
                    Optional<EngineRun> run = Optional.empty();
                    try {
                        run = Optional.ofNullable(EngineRunner.run(file));
                    } catch (FileNotFoundException ex) {
                        LOG.error("Error processing file ", ex);
                    }
                    return run;
                })
                .filter(x -> x.isPresent())
                .map(run -> run.get())
                .collect(Collectors.toList());

        return collect;
    }

}
