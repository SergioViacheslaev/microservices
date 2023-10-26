package com.study.microservices.employeeservice.async.controller;

import com.study.microservices.employeeservice.exception.InvalidCsvException;
import com.study.microservices.employeeservice.async.service.AsyncEmployeeUploadService;
import com.study.microservices.employeeservice.async.service.TicketManager;
import com.study.microservices.employeeservice.async.service.TempFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.study.microservices.employeeservice.util.constants.CsvConstants.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/async/employee")
@Tag(name = "Async Employee CSV file upload controller")
public class AsyncEmployeeUploadController {

    private final TicketManager ticketManager;
    private final AsyncEmployeeUploadService asyncEmployeeUploadService;
    private final TempFileService tempFileService;

    @Operation(description = "Initiates an asynchronous process to upload a Employee CSV file")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success. Response body contains a ticket to get an SSE connection",
                    content = {@Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                            schema = @Schema(implementation = UUID.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Request is invalid",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))
                    })
    })
    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> uploadSelfHotelsFileV2(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        final var file = getFile(multipartFile);
        final var ticket = ticketManager.createTicket();
        asyncEmployeeUploadService.asyncUploadEmployeeFile(ticket, file);
        return ResponseEntity.ok(ticket);
    }

    @Operation(description = "Gets the server-sent event (SSE) emitter associated with the ticket")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success",
            content = {@Content(mediaType = MediaType.TEXT_EVENT_STREAM_VALUE)})
    })
    @GetMapping(value = "/ticket/{ticket}")
    public SseEmitter getSseConnection(@PathVariable String ticket) {
        return ticketManager.getMappedSseEmitterForTicket(ticket);
    }

    private File getFile(MultipartFile multipartFile) throws IOException {
        final List<String> allowedContentTypes = List.of(CSV_CONTENT_TYPE, MS_EXCEL_CONTENT_TYPE);
        if (null == multipartFile.getContentType() || !allowedContentTypes.contains(multipartFile.getContentType())) {
            log.error("Invalid file format. Content type is: {}", multipartFile.getContentType());
            throw new InvalidCsvException(INVALID_CSV_FORMAT_MSG);
        }
        return tempFileService.transferMultipartFileToTmpFile(multipartFile);
    }

}
