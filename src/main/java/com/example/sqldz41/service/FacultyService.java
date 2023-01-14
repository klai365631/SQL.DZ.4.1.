package com.example.sqldz41.service;
import com.example.sqldz41.component.RecordMapper;
import com.example.sqldz41.entity.Faculty;
import com.example.sqldz41.exeption.FacultyNotFoundException;
import com.example.sqldz41.record.FacultyRecord;
import com.example.sqldz41.record.StudentRecord;
import com.example.sqldz41.repository.FacultyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;
@Service
public class FacultyService {

    private static final Logger LOG = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;
    private final RecordMapper recordMapper;

    public FacultyService(FacultyRepository facultyRepository, RecordMapper recordMapper) {
        this.facultyRepository = facultyRepository;
        this.recordMapper = recordMapper;
    }

    public FacultyRecord create(FacultyRecord facultyRecord) {
        LOG.debug("Method create was invoked");
        return recordMapper.toRecord(facultyRepository.save(recordMapper.toEntity(facultyRecord)));
    }

    public FacultyRecord read(long id) {
        LOG.debug("Method read was invoked");
        return recordMapper.toRecord(facultyRepository.findById(id).orElseThrow(()->{
        LOG.error("Faculty with id={} not found", id);
        return new FacultyNotFoundException(id);
        }));
    }

    public FacultyRecord update(long id,
                                FacultyRecord facultyRecord) {
        LOG.debug("Method create was invoked");
        Faculty oldFaculty = facultyRepository.findById(id).orElseThrow(() -> {
        LOG.error("Faculty with id={} not found", id);
        return new FacultyNotFoundException(id);
    });
        oldFaculty.setName(facultyRecord.getName());
        oldFaculty.setColor(facultyRecord.getColor());
        return recordMapper.toRecord(facultyRepository.save(oldFaculty));
    }

    public FacultyRecord delete(long id) {
        LOG.debug("Method delete was invoked");
        Faculty faculty = facultyRepository.findById(id).orElseThrow(()->{
            LOG.error("Faculty with id={} not found", id);
            return new FacultyNotFoundException(id);
        });
        facultyRepository.delete(faculty);
        return recordMapper.toRecord(faculty);
    }

    public Collection<FacultyRecord> findByColor(String color) {
        LOG.debug("Method findByColor was invoked");
        return facultyRepository.findAllByColor(color).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<FacultyRecord> findByColorOnName(String colorOnName) {
        LOG.debug("Method findByColorOnName was invoked");
        return facultyRepository.findAllByColorIgnoreCaseOrNameIgnoreCase(colorOnName,colorOnName).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<StudentRecord> getStudentsByFaculty(long id) {
        LOG.debug("Method getStudentsByFaculty was invoked");
        return facultyRepository.findById(id)
                .map(Faculty::getStudents)
                .map(students ->
                        students.stream()
                                .map(recordMapper::toRecord)
                                .collect(Collectors.toList())
                )
                .orElseThrow(()->{
                    LOG.error("Faculty with id={} not found", id);
                    return new FacultyNotFoundException(id);
                });

    }

    public String findTheLongestFacultyName() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElseThrow(FacultyNotFoundException::new);
    }
}
