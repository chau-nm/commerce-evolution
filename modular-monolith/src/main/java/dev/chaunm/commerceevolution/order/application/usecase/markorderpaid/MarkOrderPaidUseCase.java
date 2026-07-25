package dev.chaunm.commerceevolution.order.application.usecase.markorderpaid;

public interface MarkOrderPaidUseCase {
    MarkOrderPaidResult markPaid(MarkOrderPaidCommand command);
}
