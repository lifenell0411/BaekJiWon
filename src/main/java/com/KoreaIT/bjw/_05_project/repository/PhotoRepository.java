package com.KoreaIT.bjw._05_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.KoreaIT.bjw._05_project.vo.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    // Photo 엔티티와 관련된 DB 조작 메소드가 자동으로 생성됨
}