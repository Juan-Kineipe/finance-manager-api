package com.kineipe.financemanager.domain.dto;

import com.kineipe.financemanager.domain.Permission;

import java.util.List;

public record RegisterDTO(String username, String firstName, String lastName, String password, List<Permission> permissions) {}
