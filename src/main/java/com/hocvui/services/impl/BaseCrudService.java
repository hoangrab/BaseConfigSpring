package com.hocvui.services.impl;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BaseCrudService<H,O,A,N> {
    @Transactional(readOnly = true)
    List<H> findAll();
}
