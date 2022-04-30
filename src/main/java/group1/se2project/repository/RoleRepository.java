package group1.se2project.repository;

import group1.se2project.model.Role;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Override
    Role getById(Integer integer);

}
