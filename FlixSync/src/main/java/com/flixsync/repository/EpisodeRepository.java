package com.flixsync.repository;

import com.flixsync.model.entity.EpisodeEntity;
import com.flixsync.model.entity.EpisodePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpisodeRepository extends JpaRepository<EpisodeEntity, EpisodePK> {

}
