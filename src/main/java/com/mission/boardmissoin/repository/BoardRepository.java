package com.mission.boardmissoin.repository;

import com.mission.boardmissoin.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {

}
