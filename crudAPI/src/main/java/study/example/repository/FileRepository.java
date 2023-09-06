package study.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import study.example.entity.ExcelFile;

@Repository
public interface FileRepository extends JpaRepository<ExcelFile, Long> {
	
}
