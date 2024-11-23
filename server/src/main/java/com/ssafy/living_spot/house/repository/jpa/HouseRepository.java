package com.ssafy.living_spot.house.repository.jpa;

import com.ssafy.living_spot.house.domain.House;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    Optional<House> findByAptSeq(String aptSeq);
}
