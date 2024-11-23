package com.testmaster.testmaster.controller;


import com.testmaster.testmaster.entity.Project;
import com.testmaster.testmaster.entity.Task;
import com.testmaster.testmaster.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RestController
@RequestMapping("api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Optional<Project> project = projectService.getProjectById(id);
        if (project.isPresent()) {
            return ResponseEntity.ok(project.get());
        }else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public Project createproject(@RequestBody Project project){
        return projectService.saveProject(project);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
        // Retrieve the existing project by ID
        Optional<Project> optionalProject = projectService.getProjectById(id);

        if (optionalProject.isPresent()) {
            Project existingProject = optionalProject.get();

            // Update project fields
            existingProject.setName(projectDetails.getName());
            existingProject.setDescription(projectDetails.getDescription());

            // Clear the current tasks to ensure fresh association
            existingProject.getTasks().clear();

            // Re-add tasks, ensuring that each task is associated with the project
            for (Task task : projectDetails.getTasks()) {
                task.setProject(existingProject); // Associate task with the existing project
                existingProject.getTasks().add(task);
            }

            // Save the updated project
            Project updatedProject = projectService.saveProject(existingProject);

            return ResponseEntity.ok(updatedProject);
        } else {
            // Return 404 Not Found if the project doesn't exist
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id){
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }


}


