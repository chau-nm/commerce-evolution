package dev.chaunm.commerceevolution.notification.presentation.notification.listnotifications;

import dev.chaunm.commerceevolution.notification.application.usecase.listnotifications.ListNotificationsMapper;
import dev.chaunm.commerceevolution.notification.application.usecase.listnotifications.ListNotificationsUseCase;
import dev.chaunm.commerceevolution.notification.application.usecase.listnotifications.NotificationSummaryItem;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;
import dev.chaunm.commerceevolution.shared.presentation.pagination.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class ListNotificationsController {

    private final ListNotificationsUseCase listNotificationsUseCase;
    private final ListNotificationsMapper mapper;

    @GetMapping
    public ResponseEntity<PaginationResponse<NotificationSummaryResponse>> listNotifications(@ModelAttribute ListNotificationsRequest request) {
        PaginationResult<NotificationSummaryItem> result = listNotificationsUseCase.listNotifications(mapper.toCommand(request));
        return ResponseEntity.ok(result.map(mapper::toResponse).toResponse());
    }
}
