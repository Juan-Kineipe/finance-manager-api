package com.kineipe.financemanager.domain.dto;

import com.kineipe.financemanager.domain.Permission;

import java.util.List;

public record UserDTO(Long id, String username, List<Permission> permissions) {}