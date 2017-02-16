package org.revo.Service;

import org.revo.Domain.User;
import org.springframework.scheduling.annotation.Async;

/**
 * Created by ashraf on 14/02/17.
 */
public interface MailService {
    @Async
    void SendActivation(User user);
}
