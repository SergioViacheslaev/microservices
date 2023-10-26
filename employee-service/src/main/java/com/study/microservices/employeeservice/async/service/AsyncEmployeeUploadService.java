package com.study.microservices.employeeservice.async.service;

import com.study.microservices.employeeservice.exception.PartialUploadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.profiler.Profiler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.File;
import java.io.IOException;

import static com.study.microservices.employeeservice.util.constants.AsyncConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncEmployeeUploadService {

    private final TicketManager ticketManager;
    private final TempFileService tempFileService;

    @Async("asyncEmployeeUploadExecutor")
    public void asyncUploadEmployeeFile(String ticket, File file) throws IOException {
        upload(ticket, file);
    }

    private void upload(String ticket, File file) throws IOException {
        SseEmitter.SseEventBuilder sseEventBuilder;

        final var profiler = new Profiler("CSV Upload Timing:");
        profiler.start("CSV Upload Started");
        try {
            //todo: simulate CSV file processing
            log.info("Started file uploading");
            Thread.sleep(10_000L);
            log.info("Finished file uploading");

            sseEventBuilder = SseEmitter.event().data(ticket).name(FILE_UPLOAD_COMPLETED);
        } catch (PartialUploadException e) {
            sseEventBuilder = SseEmitter.event().data(e.getMessage()).name(FILE_UPLOAD_PARTIAL_INFO);
        } catch (Exception e) {
            sseEventBuilder = SseEmitter.event().data(e.getMessage()).name(FILE_UPLOAD_FAILED);
        } finally {
            tempFileService.deleteFileIfExists(file);
            log.debug(profiler.stop().toString());
        }

        final var emitter = ticketManager.getMappedSseEmitterForTicket(ticket);
        emitter.send(sseEventBuilder);
        emitter.complete();
    }
}
