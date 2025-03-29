package com.exam.controllers;

import com.exam.models.SinhVien;

public abstract class BaseStudentController {
    protected SinhVien sinhVien;

    public void initData(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
        initialize();
    }

    /**
     * Initialize the controller after data has been set.
     * This method should be overridden by subclasses to perform
     * any necessary initialization.
     */
    protected abstract void initialize();
}