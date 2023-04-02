package ru.netology.diplomafinal.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.netology.diplomafinal.models.storrage.FileBearer;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileBearer, Long> {

    Optional<FileBearer> findByFileNameEquals(String fileName);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update FileBearer file  set file.fileName = :filename  where file.id = :id")
    void updateFile(@Param("filename") String filename, @Param("id") Long id);

}
