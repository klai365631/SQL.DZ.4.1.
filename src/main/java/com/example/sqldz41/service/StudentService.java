package com.example.sqldz41.service;

import com.example.sqldz41.component.RecordMapper;
import com.example.sqldz41.entity.Avatar;
import com.example.sqldz41.entity.Faculty;
import com.example.sqldz41.entity.Student;
import com.example.sqldz41.exeption.AvatarNotFoundException;
import com.example.sqldz41.exeption.StudentNotFoundException;
import com.example.sqldz41.record.FacultyRecord;
import com.example.sqldz41.record.StudentRecord;
import com.example.sqldz41.repository.AvatarRepository;
import com.example.sqldz41.repository.FacultyRepository;
import com.example.sqldz41.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final AvatarRepository avatarRepository;

    private final RecordMapper recordMapper;

    public StudentService(StudentRepository studentRepository,
                          FacultyRepository facultyRepository,
                          RecordMapper recordMapper,
                          AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.recordMapper = recordMapper;
        this.avatarRepository = avatarRepository;
    }

    public StudentRecord create(StudentRecord studentRecord) {
        Student student = recordMapper.toEntity(studentRecord);
        student.setFaculty(
                Optional.ofNullable(student.getFaculty())
                        .map(Faculty::getId)
                        .flatMap(facultyRepository::findById)
                        .orElse(null)
        );
        return recordMapper.toRecord(studentRepository.save(student));
    }

    public StudentRecord read(long id) {
        return recordMapper.toRecord(studentRepository.findById(id).orElseThrow(()->new StudentNotFoundException(id)));
    }

    public StudentRecord update(long id,
                                StudentRecord studentRecord) {
        Student oldStudent = studentRepository.findById(id).orElseThrow(()->new StudentNotFoundException(id));
        oldStudent.setName(studentRecord.getName());
        oldStudent.setAge(studentRecord.getAge());
        oldStudent.setFaculty(
                Optional.ofNullable(studentRecord.getFaculty())
                        .map(FacultyRecord::getId)
                        .flatMap(facultyRepository::findById)
                        .orElse(null)
        );
        return recordMapper.toRecord(studentRepository.save(oldStudent));
    }

    public StudentRecord delete(long id) {
        Student student = studentRepository.findById(id).orElseThrow(()->new StudentNotFoundException(id));
        studentRepository.delete(student);
        return recordMapper.toRecord(student);
    }

    public Collection<StudentRecord> findByAge(int age) {
        return studentRepository.findAllByAge(age).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<StudentRecord> findByAgeBetween(int minAge, int maxAge) {
        return studentRepository.findByAgeBetween(minAge,maxAge).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public FacultyRecord getFacultyByStudent(long id) {
        return read(id).getFaculty();
    }

    public StudentRecord patchStudentAvatar(long id, long avatarId) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        Optional<Avatar> optionalAvatar = avatarRepository.findById(avatarId);
        if (!optionalStudent.isEmpty()) {
            throw new StudentNotFoundException(avatarId);
        }
        if (!optionalAvatar.isEmpty()) {
            throw new AvatarNotFoundException(id);
        }
        Student student = optionalStudent.get();
        student.setAvatar(optionalAvatar.get());
        return recordMapper.toRecord(studentRepository.save(studentRepository.save(student)));
    }

    public int totalCountOfStudents() {
        return studentRepository.totalCountOfStudents();
    }


    public double averageAgeOfStudents() {
        return studentRepository.averageAgeOfStudents();
    }

    public List<StudentRecord> lastStudents(int count) {
        return studentRepository.lastStudents(count).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }


}
