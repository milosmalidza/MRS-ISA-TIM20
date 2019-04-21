package com.webapplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapplication.Model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}
