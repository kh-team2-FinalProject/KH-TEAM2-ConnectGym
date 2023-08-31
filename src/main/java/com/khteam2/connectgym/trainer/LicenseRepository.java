package com.khteam2.connectgym.trainer;

import com.khteam2.connectgym.lesson.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseRepository extends JpaRepository<License, Long>  {
}
