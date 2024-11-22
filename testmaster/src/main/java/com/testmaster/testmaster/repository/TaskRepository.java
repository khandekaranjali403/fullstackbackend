package com.testmaster.testmaster.repository;

import com.testmaster.testmaster.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Long>
{

}
