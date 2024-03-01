package io.yanocode.portify.Repo;

import io.yanocode.portify.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
}
