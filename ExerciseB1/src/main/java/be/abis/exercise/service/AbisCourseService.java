package be.abis.exercise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.abis.exercise.model.Course;
import be.abis.exercise.repository.CourseRepository;

@Service
public class AbisCourseService implements CourseService {

	@Autowired
	private CourseService courseService;
	
	@Override
	public List<Course> findAllCourses() {
		return courseService.findAllCourses();
	}

	@Override
	public Course findCourse(int id) {
		return courseService.findCourse(id);
	}

	@Override
	public Course findCourse(String shortTitle) {
		return courseService.findCourse(shortTitle);
	}

}
