package com.scu.ams.basic.service.impl;

import com.scu.ams.basic.service.DatabaseBackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DatabaseBackupServiceImpl {
    @Autowired
    private DatabaseBackupService databaseBackupService;

    @Scheduled(cron = "0 0 0 1 * *") // 每个月的第一天执行，时间为凌晨 00:00:00
    public void backupDatabase() {
        databaseBackupService.performBackup();
    }
}
