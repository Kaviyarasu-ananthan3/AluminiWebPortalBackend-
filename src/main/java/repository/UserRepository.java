package com.alumni.alumniconnectportal.repository;

import com.alumni.alumniconnectportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Login
    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    User findByName(String name);

    // Dashboard Count
    Long countByRole(String role);

    // Get all Alumni
    List<User> findByRole(String role);

    // Total Unique Companies
    @Query("SELECT COUNT(DISTINCT u.company) FROM User u WHERE u.role='alumni' AND u.company IS NOT NULL AND u.company <> ''")
    Long countDistinctCompanies();

    // Total Unique Locations
    @Query("SELECT COUNT(DISTINCT u.location) FROM User u WHERE u.role='alumni' AND u.location IS NOT NULL AND u.location <> ''")
    Long countDistinctLocations();

    @Query("SELECT DISTINCT u.department FROM User u WHERE u.role='alumni' AND u.department IS NOT NULL AND u.department<>'' ORDER BY u.department")
    List<String> getDepartments();

    @Query("SELECT DISTINCT u.batch FROM User u WHERE u.role='alumni' AND u.batch IS NOT NULL AND u.batch<>'' ORDER BY u.batch")
    List<String> getBatches();

    @Query("SELECT DISTINCT u.company FROM User u WHERE u.role='alumni' AND u.company IS NOT NULL AND u.company<>'' ORDER BY u.company")
    List<String> getCompanies();

    @Query("SELECT DISTINCT u.location FROM User u WHERE u.role='alumni' AND u.location IS NOT NULL AND u.location<>'' ORDER BY u.location")
    List<String> getLocations();

    @Query("""
SELECT u FROM User u
WHERE u.role='alumni'
AND (:department='' OR u.department=:department)
AND (:batch='' OR u.batch=:batch)
AND (:company='' OR u.company=:company)
AND (:location='' OR u.location=:location)
""")
    List<User> filterAlumni(
            @Param("department") String department,
            @Param("batch") String batch,
            @Param("company") String company,
            @Param("location") String location
    );

    @Query("""
SELECT u FROM User u
WHERE u.role='alumni'
AND (
LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR LOWER(u.company) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR LOWER(u.designation) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR LOWER(u.department) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR LOWER(u.batch) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR LOWER(u.location) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR LOWER(u.skills) LIKE LOWER(CONCAT('%', :keyword, '%'))
)
""")
    List<User> searchAlumni(@Param("keyword") String keyword);
}