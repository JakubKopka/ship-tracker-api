package dev.kopka.shiptracker.repository;

import dev.kopka.shiptracker.domain.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
    Point findByName(String name);
}
