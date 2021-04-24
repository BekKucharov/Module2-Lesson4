package uz.pdp.springlesson4restsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.springlesson4restsecurity.entity.Income;

public interface IncomeRepository extends JpaRepository<Income, Integer> {
}
