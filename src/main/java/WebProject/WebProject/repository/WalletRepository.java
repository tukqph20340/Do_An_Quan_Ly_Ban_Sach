package WebProject.WebProject.repository;

import WebProject.WebProject.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet ,Integer> {
    Wallet findByUserId(String a);
}
