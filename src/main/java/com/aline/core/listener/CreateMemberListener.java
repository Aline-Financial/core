package com.aline.core.listener;

import com.aline.core.model.Member;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PrePersist;

@Slf4j(topic = "Create Member Listener")
public class CreateMemberListener {

    @PrePersist
    public void prePersist(Member member) {
        log.info("Pre-Persist...");
    }

}
