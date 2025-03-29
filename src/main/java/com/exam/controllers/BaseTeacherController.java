package com.exam.controllers;

import com.exam.models.GiaoVien;

public abstract class BaseTeacherController {
    protected GiaoVien giaoVien;

    public void initData(GiaoVien giaoVien) {
        this.giaoVien = giaoVien;
        initialize();
    }

    /**
     * Initialize the controller after data has been set.
     * This method should be overridden by subclasses to perform
     * any necessary initialization.
     */
    protected abstract void initialize();
}