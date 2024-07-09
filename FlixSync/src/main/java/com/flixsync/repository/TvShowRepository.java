package com.flixsync.repository;

import com.flixsync.model.entity.TvShowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TvShowRepository extends JpaRepository<TvShowEntity, Integer> {

}
