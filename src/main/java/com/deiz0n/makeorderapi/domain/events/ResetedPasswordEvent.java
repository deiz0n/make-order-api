package com.deiz0n.makeorderapi.domain.events;

import com.deiz0n.makeorderapi.domain.utils.requests.ResetPasswordRequest;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ResetedPasswordEvent extends ApplicationEvent {

    private ResetPasswordRequest resetPassword;

    public ResetedPasswordEvent(Object id, ResetPasswordRequest resetPassword) {
        super(id);
        this.resetPassword = resetPassword;
    }
}
