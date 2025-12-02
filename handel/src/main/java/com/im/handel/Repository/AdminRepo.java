package com.im.handel.Repository;

import com.im.handel.Entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends JpaRepository<AdminEntity, Long> {
    Optional<AdminEntity> findByAdminEmailId(String username);
}
