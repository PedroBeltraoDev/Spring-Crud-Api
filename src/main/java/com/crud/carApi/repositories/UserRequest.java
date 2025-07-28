package com.crud.carApi.repositories;

import java.util.UUID;

public record UserRequest(UUID id, String name, String email) {

}
