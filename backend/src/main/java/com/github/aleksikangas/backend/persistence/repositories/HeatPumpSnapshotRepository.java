/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.persistence.repositories;

import com.github.aleksikangas.backend.domain.snapshot.HeatPumpSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeatPumpSnapshotRepository extends JpaRepository<HeatPumpSnapshot, Long> {

}
