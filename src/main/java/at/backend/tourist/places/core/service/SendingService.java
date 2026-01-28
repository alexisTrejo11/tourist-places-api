package at.backend.tourist.places.core.service;

import at.backend.tourist.places.core.shared.DTOs.EmailSendingDTO;

public interface SendingService {
    void sendEmail(EmailSendingDTO emailSendingDTO);
}
