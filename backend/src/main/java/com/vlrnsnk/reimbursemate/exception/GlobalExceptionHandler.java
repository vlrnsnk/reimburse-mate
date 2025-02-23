package com.vlrnsnk.reimbursemate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle UserNotFoundException
     *
     * @param e UserNotFoundException
     * @return CustomErrorResponse
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Handle InvalidRoleException
     *
     * @param e InvalidRoleException
     * @return CustomErrorResponse
     */
    @ExceptionHandler(InvalidUserRoleException.class)
    public ResponseEntity<CustomErrorResponse> handleInvalidUserRoleException(InvalidUserRoleException e) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Handle InvalidStatusException
     *
     * @param e InvalidStatusException
     * @return CustomErrorResponse
     */
    @ExceptionHandler(InvalidReimbursementStatusException.class)
    public ResponseEntity<CustomErrorResponse> handleInvalidReimbursementStatusException(InvalidReimbursementStatusException e) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Handle UserCreationException
     *
     * @param e UserCreationException
     * @return CustomErrorResponse
     */
    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<CustomErrorResponse> handleUserCreationException(UserCreationException e) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Handle ReimbursementNotFoundException
     *
     * @param e ReimbursementNotFoundException
     * @return CustomErrorResponse
     */
    @ExceptionHandler(ReimbursementNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleReimbursementNotFoundException(ReimbursementNotFoundException e) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Handle InvalidReimbursementUpdateException
     *
     * @param e InvalidReimbursementUpdateException
     * @return CustomErrorResponse
     */
    @ExceptionHandler(InvalidReimbursementUpdateException.class)
    public ResponseEntity<CustomErrorResponse> handleInvalidReimbursementUpdateException(InvalidReimbursementUpdateException e) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Handle MissingRequiredFieldsException
     *
     * @param e MissingRequiredFieldsException
     * @return CustomErrorResponse
     */
    @ExceptionHandler(MissingRequiredFieldsException.class)
    public ResponseEntity<CustomErrorResponse> handleMissingRequiredFieldsException(MissingRequiredFieldsException e) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Handle Exception
     *
     * @param e Exception
     * @return CustomErrorResponse
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleException(Exception e) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
