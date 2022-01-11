package com.ivantimarket.ivanti.controllers;

import com.ivantimarket.ivanti.model.Download;
import com.ivantimarket.ivanti.service.DownloadService;
import com.ivantimarket.ivanti.service.SequenceGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api")
public class DownloadController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final SequenceGeneratorService sequenceGeneratorService;

    private final DownloadService downloadService;

    public DownloadController(SequenceGeneratorService sequenceGeneratorService, DownloadService downloadService) {
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.downloadService = downloadService;
    }

    @PostMapping("/download/create")
    public ResponseEntity<Download> createDownload(@RequestBody Download download) {
        LOG.info("Saving download");
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/download/create").toUriString());
        download.setId(sequenceGeneratorService.generateSequence(Download.SEQUENCE_NAME));
        return ResponseEntity.created(uri).body(downloadService.saveDownload(download));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Download> getDownloadById(@PathVariable("id")int id) {
        LOG.info("Getting download with ID : {}", id);
        return ResponseEntity.ok().body(downloadService.getById(id));
    }

    @PostMapping("/download/delete/{id}")
    public ResponseEntity<Boolean> deleteDownloadById(@PathVariable("id")int id) {
        LOG.info("Deleting download with ID : {}", id);
        return ResponseEntity.ok().body(downloadService.deleteDownload(id));
    }

    @GetMapping("/downloads/{userId}")
    public ResponseEntity<List<Download>> getDownloadsByUserId(@PathVariable("userId")int userId) {
        LOG.info("Getting all downloads from user with ID : {}", userId);
        return ResponseEntity.ok().body(downloadService.getByUserId(userId));
    }

    @GetMapping("/downloads/{packageId}")
    public ResponseEntity<List<Download>> getDownloadsByPackageId(@PathVariable("packageId")int packageId) {
        LOG.info("Getting all downloads from package with ID : {}", packageId);
        return ResponseEntity.ok().body(downloadService.getByPackageId(packageId));
    }

}
