package fr.qui.eco_gestion_back_office.auth;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TentativeBlocageRepository extends JpaRepository<TentativeBlocage, Long> {
    Optional<TentativeBlocage> findByIpAndType(String ip, String type);
}
