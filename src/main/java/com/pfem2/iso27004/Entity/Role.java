package com.pfem2.iso27004.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.pfem2.iso27004.Entity.Permission.ADMIN_CREATE;
import static com.pfem2.iso27004.Entity.Permission.ADMIN_DELETE;
import static com.pfem2.iso27004.Entity.Permission.ADMIN_READ;
import static com.pfem2.iso27004.Entity.Permission.ADMIN_UPDATE;
import static com.pfem2.iso27004.Entity.Permission.MANAGER_CREATE;
import static com.pfem2.iso27004.Entity.Permission.MANAGER_DELETE;
import static com.pfem2.iso27004.Entity.Permission.MANAGER_READ;
import static com.pfem2.iso27004.Entity.Permission.MANAGER_UPDATE;

@RequiredArgsConstructor
public enum Role {

        USER,
        ADMIN;

}