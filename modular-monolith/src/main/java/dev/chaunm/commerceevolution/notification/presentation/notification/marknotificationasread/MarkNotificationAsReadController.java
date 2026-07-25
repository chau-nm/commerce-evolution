package dev.chaunm.commerceevolution.notification.presentation.notification.marknotificationasread;

import dev.chaunm.commerceevolution.notification.application.usecase.marknotificationasread.MarkNotificationAsReadMapper;
import dev.chaunm.commerceevolution.notification.application.usecase.marknotificationasread.MarkNotificationAsReadResult;
import dev.chaunm.commerceevolution.notification.application.usecase.marknotificationasread.MarkNotificationAsReadUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications/{notificationId}/read")
@RequiredArgsConstructor
public class MarkNotificationAsReadController {

    private final MarkNotificationAsReadUseCase markNotificationAsReadUseCase;
    private final MarkNotificationAsReadMapper mapper;

    @PatchMapping
    public ResponseEntity<MarkNotificationAsReadResponse> markAsRead(@PathVariable UUID notificationId) {
        MarkNotificationAsReadResult result = markNotificationAsReadUseCase.markAsRead(mapper.toCommand(notificationId));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
