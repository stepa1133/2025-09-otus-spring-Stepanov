package ru.otus.hw.security;

import org.springframework.security.acls.domain.AuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.Permission;

public class CustomMaskPermissionGrantingStrategy extends DefaultPermissionGrantingStrategy {

    public CustomMaskPermissionGrantingStrategy(AuditLogger auditLogger) {
        super(auditLogger);
        System.out.println("CustomMaskPermissionGrantingStrategy initialized!");
    }

    @Override
    protected boolean isGranted(AccessControlEntry ace, Permission p) {
        int aceMask = ace.getPermission().getMask();
        int requestedMask = p.getMask();
        return (aceMask & requestedMask) == requestedMask;
    }
}
