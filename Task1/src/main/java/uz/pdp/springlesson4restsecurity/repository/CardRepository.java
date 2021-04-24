package uz.pdp.springlesson4restsecurity.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.springlesson4restsecurity.entity.Card;

public interface CardRepository extends JpaRepository<Card, Integer> {
}
