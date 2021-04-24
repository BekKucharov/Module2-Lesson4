package uz.pdp.springlesson4restsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.springlesson4restsecurity.entity.Outcome;

public interface OutcomeRepository extends JpaRepository<Outcome, Integer> {
}
