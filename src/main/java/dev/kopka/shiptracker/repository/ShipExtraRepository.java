package dev.kopka.shiptracker.repository;

import dev.kopka.shiptracker.domain.model.ShipExtra;
import dev.kopka.shiptracker.domain.model.ShipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipExtraRepository extends JpaRepository<ShipExtra, Long> {
    ShipExtra findByShipType(ShipType type);
}
