package study.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import study.example.entity.UserInfo;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Integer> {

}
