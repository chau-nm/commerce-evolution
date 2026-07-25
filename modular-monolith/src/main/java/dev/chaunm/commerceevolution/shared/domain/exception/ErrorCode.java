package dev.chaunm.commerceevolution.shared.domain.exception;

/** Implemented by per-bounded-context enums so each context owns its own set of error codes. */
public interface ErrorCode {

    String code();
}
